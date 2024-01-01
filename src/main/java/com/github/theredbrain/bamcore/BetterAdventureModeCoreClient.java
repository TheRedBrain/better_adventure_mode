package com.github.theredbrain.bamcore;

import com.github.theredbrain.bamcore.client.gui.screen.ingame.TeleporterBlockScreen;
import com.github.theredbrain.bamcore.client.network.packet.BetterAdventureModeCoreClientPacket;
import com.github.theredbrain.bamcore.client.render.block.entity.HousingBlockBlockEntityRenderer;
import com.github.theredbrain.bamcore.client.render.block.entity.MimicBlockEntityRenderer;
import com.github.theredbrain.bamcore.client.render.block.entity.TeleporterBlockBlockEntityRenderer;
import com.github.theredbrain.bamcore.client.render.renderer.SpawnerBoundEntityRenderer;
import com.github.theredbrain.bamcore.config.ClientConfig;
import com.github.theredbrain.bamcore.config.ClientConfigWrapper;
import com.github.theredbrain.bamcore.registry.*;
import me.shedaniel.autoconfig.AutoConfig;
import me.shedaniel.autoconfig.serializer.JanksonConfigSerializer;
import me.shedaniel.autoconfig.serializer.PartitioningSerializer;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.blockrenderlayer.v1.BlockRenderLayerMap;
import net.fabricmc.fabric.api.client.rendering.v1.ColorProviderRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.EntityRendererRegistry;
import net.minecraft.client.gui.screen.ingame.HandledScreens;
import net.minecraft.client.item.ModelPredicateProviderRegistry;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.block.entity.BlockEntityRendererFactories;
import net.minecraft.item.DyeableItem;
import net.minecraft.item.Items;
import net.minecraft.util.Identifier;
//import net.spell_engine.api.render.CustomModels;

import java.util.List;

public class BetterAdventureModeCoreClient implements ClientModInitializer {

    public static ClientConfig clientConfig;

    @Override
    public void onInitializeClient() {
        // Config
        AutoConfig.register(ClientConfigWrapper.class, PartitioningSerializer.wrap(JanksonConfigSerializer::new));
        clientConfig = ((ClientConfigWrapper)AutoConfig.getConfigHolder(ClientConfigWrapper.class).getConfig()).client;

        // Packets
        BetterAdventureModeCoreClientPacket.init();

        // Registry
        KeyBindingsRegistry.registerKeyBindings();
        registerTransparency();
        registerSpellModels();
        registerBlockEntityRenderer();
        registerEntityRenderer();
        registerScreens();
        registerModelPredicateProviders();
        registerColors();
        EventsRegistry.initializeClientEvents();
    }

    private void registerTransparency() {
        BlockRenderLayerMap.INSTANCE.putBlock(BlockRegistry.INTERACTIVE_BERRY_BUSH_BLOCK, RenderLayer.getCutout());
        BlockRenderLayerMap.INSTANCE.putBlock(BlockRegistry.INTERACTIVE_RED_MUSHROOM_BLOCK, RenderLayer.getCutout());
        BlockRenderLayerMap.INSTANCE.putBlock(BlockRegistry.INTERACTIVE_BROWN_MUSHROOM_BLOCK, RenderLayer.getCutout());
        BlockRenderLayerMap.INSTANCE.putBlock(BlockRegistry.BONFIRE_BLOCK, RenderLayer.getCutout());
        BlockRenderLayerMap.INSTANCE.putBlock(BlockRegistry.USE_RELAY_OAK_DOOR, RenderLayer.getCutout());
        BlockRenderLayerMap.INSTANCE.putBlock(BlockRegistry.USE_RELAY_IRON_DOOR, RenderLayer.getCutout());
        BlockRenderLayerMap.INSTANCE.putBlock(BlockRegistry.USE_RELAY_SPRUCE_DOOR, RenderLayer.getCutout());
        BlockRenderLayerMap.INSTANCE.putBlock(BlockRegistry.USE_RELAY_BIRCH_DOOR, RenderLayer.getCutout());
        BlockRenderLayerMap.INSTANCE.putBlock(BlockRegistry.USE_RELAY_JUNGLE_DOOR, RenderLayer.getCutout());
        BlockRenderLayerMap.INSTANCE.putBlock(BlockRegistry.USE_RELAY_ACACIA_DOOR, RenderLayer.getCutout());
        BlockRenderLayerMap.INSTANCE.putBlock(BlockRegistry.USE_RELAY_CHERRY_DOOR, RenderLayer.getCutout());
        BlockRenderLayerMap.INSTANCE.putBlock(BlockRegistry.USE_RELAY_DARK_OAK_DOOR, RenderLayer.getCutout());
        BlockRenderLayerMap.INSTANCE.putBlock(BlockRegistry.USE_RELAY_MANGROVE_DOOR, RenderLayer.getCutout());
        BlockRenderLayerMap.INSTANCE.putBlock(BlockRegistry.USE_RELAY_BAMBOO_DOOR, RenderLayer.getCutout());
        BlockRenderLayerMap.INSTANCE.putBlock(BlockRegistry.USE_RELAY_CRIMSON_DOOR, RenderLayer.getCutout());
        BlockRenderLayerMap.INSTANCE.putBlock(BlockRegistry.USE_RELAY_WARPED_DOOR, RenderLayer.getCutout());
        BlockRenderLayerMap.INSTANCE.putBlock(BlockRegistry.USE_RELAY_OAK_TRAPDOOR, RenderLayer.getCutout());
        BlockRenderLayerMap.INSTANCE.putBlock(BlockRegistry.USE_RELAY_IRON_TRAPDOOR, RenderLayer.getCutout());
        BlockRenderLayerMap.INSTANCE.putBlock(BlockRegistry.USE_RELAY_SPRUCE_TRAPDOOR, RenderLayer.getCutout());
        BlockRenderLayerMap.INSTANCE.putBlock(BlockRegistry.USE_RELAY_BIRCH_TRAPDOOR, RenderLayer.getCutout());
        BlockRenderLayerMap.INSTANCE.putBlock(BlockRegistry.USE_RELAY_JUNGLE_TRAPDOOR, RenderLayer.getCutout());
        BlockRenderLayerMap.INSTANCE.putBlock(BlockRegistry.USE_RELAY_ACACIA_TRAPDOOR, RenderLayer.getCutout());
        BlockRenderLayerMap.INSTANCE.putBlock(BlockRegistry.USE_RELAY_CHERRY_TRAPDOOR, RenderLayer.getCutout());
        BlockRenderLayerMap.INSTANCE.putBlock(BlockRegistry.USE_RELAY_DARK_OAK_TRAPDOOR, RenderLayer.getCutout());
        BlockRenderLayerMap.INSTANCE.putBlock(BlockRegistry.USE_RELAY_MANGROVE_TRAPDOOR, RenderLayer.getCutout());
        BlockRenderLayerMap.INSTANCE.putBlock(BlockRegistry.USE_RELAY_BAMBOO_TRAPDOOR, RenderLayer.getCutout());
        BlockRenderLayerMap.INSTANCE.putBlock(BlockRegistry.USE_RELAY_CRIMSON_TRAPDOOR, RenderLayer.getCutout());
        BlockRenderLayerMap.INSTANCE.putBlock(BlockRegistry.USE_RELAY_WARPED_TRAPDOOR, RenderLayer.getCutout());
    }

    private void registerSpellModels() {
        // TODO SpellEngine 1.20.2
//        CustomModels.registerModelIds(List.of(
//                BetterAdventureModeCore.identifier("projectile/test_spell_projectile")
//        ));
    }

    private void registerBlockEntityRenderer() {
        BlockEntityRendererFactories.register(EntityRegistry.HOUSING_BLOCK_ENTITY, HousingBlockBlockEntityRenderer::new);
        BlockEntityRendererFactories.register(EntityRegistry.MIMIC_BLOCK_ENTITY, MimicBlockEntityRenderer::new);
        BlockEntityRendererFactories.register(EntityRegistry.TELEPORTER_BLOCK_ENTITY, TeleporterBlockBlockEntityRenderer::new);
    }

    private void registerEntityRenderer() {
        EntityRendererRegistry.register(EntityRegistry.SPAWNER_BOUND_ENTITY, SpawnerBoundEntityRenderer::new);
    }

    private void registerScreens() {
        HandledScreens.register(ScreenHandlerTypesRegistry.TELEPORTER_BLOCK_SCREEN_HANDLER, TeleporterBlockScreen::new);
    }

    private void registerColors() {
        ColorProviderRegistry.ITEM.register((stack, tintIndex) -> tintIndex > 0 ? -1 : ((DyeableItem)((Object)stack.getItem())).getColor(stack), ItemRegistry.LEATHER_HELMET, ItemRegistry.LEATHER_CHESTPLATE, ItemRegistry.LEATHER_LEGGINGS, ItemRegistry.LEATHER_BOOTS, ItemRegistry.LEATHER_GLOVES, ItemRegistry.LEATHER_SHOULDERS);
    }

    private void registerModelPredicateProviders() {
        ModelPredicateProviderRegistry.register(ItemRegistry.TEST_BUCKLER, new Identifier("blocking"), (stack, world, entity, seed) -> entity != null && entity.isUsingItem() && entity.getActiveItem() == stack ? 1.0f : 0.0f);
        ModelPredicateProviderRegistry.register(ItemRegistry.TEST_NORMAL_SHIELD, new Identifier("blocking"), (stack, world, entity, seed) -> entity != null && entity.isUsingItem() && entity.getActiveItem() == stack ? 1.0f : 0.0f);
        ModelPredicateProviderRegistry.register(ItemRegistry.TEST_TOWER_SHIELD, new Identifier("blocking"), (stack, world, entity, seed) -> entity != null && entity.isUsingItem() && entity.getActiveItem() == stack ? 1.0f : 0.0f);
    }
}
