package com.github.theredbrain.bamcore.registry;

import com.github.theredbrain.bamcore.network.event.PlayerDeathCallback;
import com.github.theredbrain.bamcore.network.event.PlayerJoinCallback;
import dev.emi.trinkets.api.TrinketComponent;
import dev.emi.trinkets.api.TrinketsApi;

import java.util.Optional;

public class EventsRegistry {
    public static void initializeEvents() {
//        // TODO move to bamdimensions
//        PlayerFirstJoinCallback.EVENT.register((player, server) -> {
//            RPGMod.LOGGER.info("dungeon_dimension " + RPGMod.MOD_ID + ":" + player.getUuid().toString() + "_dungeons");
//            RPGMod.LOGGER.info("housing_dimension " + RPGMod.MOD_ID + ":" + player.getUuid().toString() + "_housing");
//            ComponentsRegistry.PLAYER_SPECIFIC_DIMENSION_IDS.get(player).setPair("dungeon_dimension", BetterAdventureModeCore.MOD_ID + ":" + player.getUuid().toString() + "_dungeons", false);
//            ComponentsRegistry.PLAYER_SPECIFIC_DIMENSION_IDS.get(player).setPair("housing_dimension", BetterAdventureModeCore.MOD_ID + ":" + player.getUuid().toString() + "_housing", false);
//            DimensionsManager.addDynamicPlayerDimension(player, server);
//        });
        PlayerJoinCallback.EVENT.register((player, server) -> {
//            // TODO move to bamdimensions
//            // if the player was longer than 5 minutes offline they get teleported to their spawn point
//            if (Math.abs(server.getOverworld().getTime() - ComponentsRegistry.LAST_LOGOUT_TIME.get(player).getValue()) >= 6000) {
//                server.getPlayerManager().respawnPlayer(player, true);
//            }
            if (server.getGameRules().getBoolean(GameRulesRegistry.TELEPORT_TO_SPAWN_ON_LOGIN)) {
                server.getPlayerManager().respawnPlayer(player, true);
            }
            Optional<TrinketComponent> trinkets = TrinketsApi.getTrinketComponent(player);
            if (trinkets.isPresent()) {
                if (trinkets.get().getInventory().get("empty_main_hand") != null) {
                    if (trinkets.get().getInventory().get("empty_main_hand").get("empty_main_hand") != null) {
                        trinkets.get().getInventory().get("empty_main_hand").get("empty_main_hand").setStack(0, ItemRegistry.DEFAULT_EMPTY_HAND_WEAPON.getDefaultStack());
                    }
                }
                if (trinkets.get().getInventory().get("empty_off_hand") != null) {
                    if (trinkets.get().getInventory().get("empty_off_hand").get("empty_off_hand") != null) {
                        trinkets.get().getInventory().get("empty_off_hand").get("empty_off_hand").setStack(0, ItemRegistry.DEFAULT_EMPTY_HAND_WEAPON.getDefaultStack());
                    }
                }
            }
        });
//        PlayerLeaveCallback.EVENT.register((player, server) -> {
//            ComponentsRegistry.LAST_LOGOUT_TIME.get(player).setValue(server.getOverworld().getTime());
//        });

        PlayerDeathCallback.EVENT.register((player, server, source) -> {
            if (server.getGameRules().getBoolean(GameRulesRegistry.RESET_ADVANCEMENTS_ON_DEATH)) {
                player.getAdvancementTracker().reload(server.getAdvancementLoader());
            }
            if (server.getGameRules().getBoolean(GameRulesRegistry.RESET_RECIPES_ON_DEATH)) {
                player.getRecipeBook().lockRecipes(server.getRecipeManager().values(), player);
            }
        });
    }
}
