package com.github.theredbrain.betteradventuremode.registry;

import com.github.theredbrain.betteradventuremode.BetterAdventureMode;
import com.github.theredbrain.betteradventuremode.BetterAdventureModeClient;
import com.github.theredbrain.betteradventuremode.api.json_files_backend.PlayerLocation;
import com.github.theredbrain.betteradventuremode.api.json_files_backend.PlayerLocationHelper;
import com.google.common.reflect.TypeToken;
import com.google.gson.Gson;
import com.google.gson.stream.JsonReader;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerLifecycleEvents;
import net.fabricmc.fabric.api.networking.v1.PacketByteBufs;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.resource.ResourceManager;
import net.minecraft.util.Identifier;

import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PlayerLocationsRegistry {

    static Map<Identifier, PlayerLocation> registeredPlayerLocations = new HashMap<>();

    public static void register(Identifier itemId, PlayerLocation playerLocation) {
        registeredPlayerLocations.put(itemId, playerLocation);
    }

    public static PlayerLocation getLocation(Identifier locationId) {
        return registeredPlayerLocations.get(locationId);
    }

    public static void init() {
        ServerLifecycleEvents.SERVER_STARTED.register((minecraftServer) -> {
            loadLocations(minecraftServer.getResourceManager());
            encodeRegistry();
        });
    }

    private static void loadLocations(ResourceManager resourceManager) {
        var gson = new Gson();
        Map<Identifier, PlayerLocation> registeredPlayerLocations = new HashMap();
        // Reading all attribute files
        for (var entry : resourceManager.findResources("player_locations", fileName -> fileName.getPath().endsWith(".json")).entrySet()) {
            var identifier = entry.getKey();
            var resource = entry.getValue();
            try {
                // System.out.println("Checking resource: " + identifier);
                JsonReader reader = new JsonReader(new InputStreamReader(resource.getInputStream()));
                PlayerLocation playerLocation = PlayerLocationHelper.decode(reader);
                var id = identifier
                        .toString().replace("player_locations/", "");
                id = id.substring(0, id.lastIndexOf('.'));
                registeredPlayerLocations.put(new Identifier(id), playerLocation);
            } catch (Exception e) {
                System.err.println("Failed to parse: " + identifier);
                e.printStackTrace();
            }
        }
        PlayerLocationsRegistry.registeredPlayerLocations = registeredPlayerLocations;
    }

    // NETWORK SYNC

    private static PacketByteBuf encodedRegisteredPlayerLocations = PacketByteBufs.create();

    public static void encodeRegistry() {
        PacketByteBuf buffer = PacketByteBufs.create();
        var gson = new Gson();
        var json = gson.toJson(registeredPlayerLocations);
        if (BetterAdventureModeClient.clientConfig.show_debug_log) {
            BetterAdventureMode.LOGGER.info("Player Locations registry loaded: " + json);
        }

        List<String> chunks = new ArrayList<>();
        var chunkSize = 10000;
        for (int i = 0; i < json.length(); i += chunkSize) {
            chunks.add(json.substring(i, Math.min(json.length(), i + chunkSize)));
        }

        buffer.writeInt(chunks.size());
        for (var chunk: chunks) {
            buffer.writeString(chunk);
        }

        if (BetterAdventureModeClient.clientConfig.show_debug_log) {
            BetterAdventureMode.LOGGER.info("Encoded Player Locations registry size (with package overhead): " + buffer.readableBytes()
                    + " bytes (in " + chunks.size() + " string chunks with the size of " + chunkSize + ")");
        }
        encodedRegisteredPlayerLocations = buffer;
    }

    public static void decodeRegistry(PacketByteBuf buffer) {
        var chunkCount = buffer.readInt();
        String json = "";
        for (int i = 0; i < chunkCount; ++i) {
            json = json.concat(buffer.readString());
        }
        if (BetterAdventureModeClient.clientConfig.show_debug_log) {
            BetterAdventureMode.info("Decoded Player Locations registry in " + chunkCount + " string chunks");
            BetterAdventureMode.info("Player Locations registry received: " + json);
        }
        var gson = new Gson();
        Type mapType = new TypeToken<Map<String, PlayerLocation>>() {}.getType();
        Map<String, PlayerLocation> readRegisteredPlayerLocations = gson.fromJson(json, mapType);
        Map<Identifier, PlayerLocation> newRegisteredPlayerLocations = new HashMap();
        readRegisteredPlayerLocations.forEach((key, value) -> {
            newRegisteredPlayerLocations.put(new Identifier(key), value);
        });
        registeredPlayerLocations = newRegisteredPlayerLocations;
    }

    public static PacketByteBuf getEncodedRegistry() {
        return encodedRegisteredPlayerLocations;
    }
}