package com.github.theredbrain.betteradventuremode.network.packet;

import com.github.theredbrain.betteradventuremode.block.entity.HousingBlockEntity;
import net.fabricmc.fabric.api.networking.v1.PacketSender;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.Text;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3i;
import net.minecraft.world.World;

public class UpdateHousingBlockCreativePacketReceiver implements ServerPlayNetworking.PlayPacketHandler<UpdateHousingBlockCreativePacket> {
    @Override
    public void receive(UpdateHousingBlockCreativePacket packet, ServerPlayerEntity player, PacketSender responseSender) {

        if (!player.isCreativeLevelTwoOp()) {
            return;
        }

        BlockPos housingBlockPosition = packet.housingBlockPosition;

        boolean showRestrictBlockBreakingArea = packet.showRestrictBlockBreakingArea;

        Vec3i restrictBlockBreakingAreaDimensions = packet.restrictBlockBreakingAreaDimensions;
        BlockPos restrictBlockBreakingAreaPositionOffset = packet.restrictBlockBreakingAreaPositionOffset;
        BlockPos triggeredBlockPositionOffset = packet.triggeredBlockPositionOffset;

        HousingBlockEntity.OwnerMode ownerMode = packet.ownerMode;

        World world = player.getWorld();

        boolean updateSuccessful = true;

        BlockEntity blockEntity = world.getBlockEntity(housingBlockPosition);
        BlockState blockState = world.getBlockState(housingBlockPosition);

        if (blockEntity instanceof HousingBlockEntity housingBlockEntity) {

            if (!housingBlockEntity.setShowInfluenceArea(showRestrictBlockBreakingArea)) {
                player.sendMessage(Text.translatable("housing_block.showRestrictBlockBreakingArea.invalid"), false);
                updateSuccessful = false;
            }
            if (!housingBlockEntity.setInfluenceAreaDimensions(restrictBlockBreakingAreaDimensions)) {
                player.sendMessage(Text.translatable("housing_block.restrictBlockBreakingAreaDimensions.invalid"), false);
                updateSuccessful = false;
            }
            if (!housingBlockEntity.setRestrictBlockBreakingAreaPositionOffset(restrictBlockBreakingAreaPositionOffset)) {
                player.sendMessage(Text.translatable("housing_block.restrictBlockBreakingAreaPositionOffset.invalid"), false);
                updateSuccessful = false;
            }
            if (!housingBlockEntity.setTriggeredBlockPositionOffset(triggeredBlockPositionOffset)) {
                player.sendMessage(Text.translatable("housing_block.triggeredBlockPositionOffset.invalid"), false);
                updateSuccessful = false;
            }
            if (!housingBlockEntity.setOwnerMode(ownerMode)) {
                player.sendMessage(Text.translatable("housing_block.ownerMode.invalid"), false);
                updateSuccessful = false;
            }
            if (updateSuccessful) {
                player.sendMessage(Text.translatable("housing_block.update_successful"), true);
            }
            housingBlockEntity.markDirty();
            world.updateListeners(housingBlockPosition, blockState, blockState, Block.NOTIFY_ALL);
        }
    }
}
