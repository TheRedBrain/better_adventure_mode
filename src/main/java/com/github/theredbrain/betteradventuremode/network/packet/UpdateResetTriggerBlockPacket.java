package com.github.theredbrain.betteradventuremode.network.packet;

import com.github.theredbrain.betteradventuremode.BetterAdventureMode;
import com.github.theredbrain.betteradventuremode.util.PacketByteBufUtils;
import net.fabricmc.fabric.api.networking.v1.FabricPacket;
import net.fabricmc.fabric.api.networking.v1.PacketType;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.util.math.BlockPos;

import java.util.List;

public class UpdateResetTriggerBlockPacket implements FabricPacket {
    public static final PacketType<UpdateResetTriggerBlockPacket> TYPE = PacketType.create(
            BetterAdventureMode.identifier("update_reset_trigger_block"),
            UpdateResetTriggerBlockPacket::new
    );

    public final BlockPos resetTriggerBlockPosition;

    public final List<BlockPos> resetBlocks;

    public UpdateResetTriggerBlockPacket(BlockPos resetTriggerBlockPosition, List<BlockPos> resetBlocks) {
        this.resetTriggerBlockPosition = resetTriggerBlockPosition;
        this.resetBlocks = resetBlocks;
    }

    public UpdateResetTriggerBlockPacket(PacketByteBuf buf) {
        this(buf.readBlockPos(), buf.readList(new PacketByteBufUtils.BlockPosReader()));
    }
    @Override
    public PacketType<?> getType() {
        return TYPE;
    }
    @Override
    public void write(PacketByteBuf buf) {
        buf.writeBlockPos(this.resetTriggerBlockPosition);
        buf.writeCollection(this.resetBlocks, new PacketByteBufUtils.BlockPosWriter());
    }

}
