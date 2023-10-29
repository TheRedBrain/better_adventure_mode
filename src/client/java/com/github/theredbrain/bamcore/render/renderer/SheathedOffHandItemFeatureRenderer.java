package com.github.theredbrain.bamcore.render.renderer;

import com.github.theredbrain.bamcore.api.item.BetterAdventureMode_BasicShieldItem;
import com.github.theredbrain.bamcore.api.item.BetterAdventureMode_BasicWeaponItem;
import com.github.theredbrain.bamcore.registry.StatusEffectsRegistry;
import com.github.theredbrain.bamcore.entity.player.DuckPlayerInventoryMixin;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.model.ModelPart;
import net.minecraft.client.network.AbstractClientPlayerEntity;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.feature.FeatureRendererContext;
import net.minecraft.client.render.entity.feature.HeldItemFeatureRenderer;
import net.minecraft.client.render.entity.model.PlayerEntityModel;
import net.minecraft.client.render.item.HeldItemRenderer;
import net.minecraft.client.render.model.json.ModelTransformationMode;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ShieldItem;
import net.minecraft.item.SwordItem;
import net.minecraft.util.math.RotationAxis;

@Environment(EnvType.CLIENT)
public class SheathedOffHandItemFeatureRenderer extends HeldItemFeatureRenderer<AbstractClientPlayerEntity, PlayerEntityModel<AbstractClientPlayerEntity>> {

    private final HeldItemRenderer heldItemRenderer;
    public SheathedOffHandItemFeatureRenderer(FeatureRendererContext<AbstractClientPlayerEntity, PlayerEntityModel<AbstractClientPlayerEntity>> context, HeldItemRenderer heldItemRenderer) {
        super(context, heldItemRenderer);
        this.heldItemRenderer = heldItemRenderer;
    }

    @Override
    public void render(MatrixStack matrixStack, VertexConsumerProvider vertexConsumerProvider, int i, AbstractClientPlayerEntity abstractClientPlayerEntity, float f, float g, float h, float j, float k, float l) {

        ItemStack offHandStack = ((DuckPlayerInventoryMixin) ((PlayerEntity) abstractClientPlayerEntity).getInventory()).bamcore$getOffHand();

        if (!offHandStack.isEmpty() && (((PlayerEntity) abstractClientPlayerEntity).hasStatusEffect(StatusEffectsRegistry.WEAPONS_SHEATHED_EFFECT) || ((PlayerEntity) abstractClientPlayerEntity).hasStatusEffect(StatusEffectsRegistry.TWO_HANDED_EFFECT))) {
            matrixStack.push();
            ModelPart modelPart = this.getContextModel().body;
            modelPart.rotate(matrixStack);
            Item offHandItem = offHandStack.getItem();

            if (offHandItem instanceof SwordItem || offHandItem instanceof BetterAdventureMode_BasicWeaponItem) {
                matrixStack.translate(0.2D, 0.0D, 0.15D);
                if (abstractClientPlayerEntity.hasStackEquipped(EquipmentSlot.CHEST)) {
    //                    matrixStack.translate(0.05F, 0.0F, 0.0F);
                    matrixStack.translate(0.0D, 0.0D, 0.06D);
                }
                matrixStack.multiply(RotationAxis.POSITIVE_Y.rotationDegrees(90.0F));
                matrixStack.multiply(RotationAxis.POSITIVE_X.rotationDegrees(-15.0F));
//                matrixStack.multiply(RotationAxis.POSITIVE_Z.rotationDegrees(90.0F));
//                if (!abstractClientPlayerEntity.hasStackEquipped(EquipmentSlot.CHEST)) {
//                    matrixStack.translate(0.05F, 0.0F, 0.0F);
//                }
//                matrixStack.scale(1.0F, -1.0F, -1.0F);
                heldItemRenderer.renderItem(abstractClientPlayerEntity, offHandStack, ModelTransformationMode.THIRD_PERSON_RIGHT_HAND, false, matrixStack, vertexConsumerProvider, i);
            } else if (offHandItem instanceof ShieldItem || offHandItem instanceof BetterAdventureMode_BasicShieldItem) {
                matrixStack.translate(0.2D, 0.4D, 0.0D);
                if (abstractClientPlayerEntity.hasStackEquipped(EquipmentSlot.CHEST)) {
                    //                    matrixStack.translate(0.05F, 0.0F, 0.0F);
                    matrixStack.translate(0.0D, 0.0D, 0.06D);
                }
                matrixStack.multiply(RotationAxis.POSITIVE_Y.rotationDegrees(-90.0F));
                matrixStack.multiply(RotationAxis.POSITIVE_X.rotationDegrees(15.0F));
//                matrixStack.translate(0.0D, 0.0D, 0.16D);
////                matrixStack.scale(BackSlotMain.CONFIG.backslot_scaling, BackSlotMain.CONFIG.backslot_scaling, BackSlotMain.CONFIG.backslot_scaling);
////                if (backSlotStack.getItem() instanceof FishingRodItem || backSlotStack.getItem() instanceof OnAStickItem) {
////                    matrixStack.multiply(RotationAxis.POSITIVE_Z.rotationDegrees(180.0F));
////                    matrixStack.translate(0.0D, -0.3D, 0.0D);
////                }
//                if (abstractClientPlayerEntity.hasStackEquipped(EquipmentSlot.CHEST)) {
//                    matrixStack.translate(0.0F, 0.0F, 0.06F);
//                }
                heldItemRenderer.renderItem(abstractClientPlayerEntity, offHandStack, ModelTransformationMode.THIRD_PERSON_RIGHT_HAND, false, matrixStack, vertexConsumerProvider, i);
            }

            matrixStack.pop();
        }
    }
}