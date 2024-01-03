package com.github.theredbrain.betteradventuremode.world;

import com.github.theredbrain.betteradventuremode.BetterAdventureMode;
import net.minecraft.block.Blocks;
import net.minecraft.registry.DynamicRegistryManager;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.registry.entry.RegistryEntryList;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.Identifier;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.dimension.DimensionOptions;
import net.minecraft.world.dimension.DimensionTypes;
import net.minecraft.world.gen.chunk.FlatChunkGenerator;
import net.minecraft.world.gen.chunk.FlatChunkGeneratorConfig;
import net.minecraft.world.gen.chunk.FlatChunkGeneratorLayer;
import qouteall.dimlib.DimensionTemplate;
import qouteall.dimlib.api.DimensionAPI;

import java.util.List;
import java.util.Optional;

public class DimensionsManager {

    public static void init() {
        DimensionAPI.suppressExperimentalWarningForNamespace("betteradventuremode");
        DimensionAPI.registerDimensionTemplate(
                "player_locations", PLAYER_LOCATIONS_DIMENSION_TEMPLATE
        );
    }

    public static void addAndSaveDynamicDimension(Identifier dimensionId, MinecraftServer server) {
        // may throw exception here

        DimensionAPI.addDimensionDynamically(server, dimensionId, PLAYER_LOCATIONS_DIMENSION_TEMPLATE.createLevelStem(server));
    }

    public static final DimensionTemplate PLAYER_LOCATIONS_DIMENSION_TEMPLATE = new DimensionTemplate(
            DimensionTypes.OVERWORLD,
            (server, dimTypeHolder) -> {
                DynamicRegistryManager.Immutable registryAccess = server.getRegistryManager();

                Registry<Biome> biomeRegistry = registryAccess.get(RegistryKeys.BIOME);

                RegistryEntry.Reference<Biome> biomeReference = biomeRegistry.entryOf(RegistryKey.of(RegistryKeys.BIOME, BetterAdventureMode.identifier("player_locations_biome")));

                FlatChunkGeneratorConfig flatChunkGeneratorConfig =
                        new FlatChunkGeneratorConfig(
                                Optional.of(RegistryEntryList.of()),
                                biomeReference,
                                List.of()
                        );
                flatChunkGeneratorConfig.getLayers().add(new FlatChunkGeneratorLayer(1, Blocks.AIR));
                flatChunkGeneratorConfig.updateLayerBlocks();

                FlatChunkGenerator chunkGenerator = new FlatChunkGenerator(flatChunkGeneratorConfig);

                return new DimensionOptions(dimTypeHolder, chunkGenerator);
            }
    );
}
