package com.github.theredbrain.betteradventuremode.network.packet;

import com.github.theredbrain.betteradventuremode.api.item.BasicWeaponItem;
import com.github.theredbrain.betteradventuremode.entity.player.DuckPlayerEntityMixin;
import com.github.theredbrain.betteradventuremode.registry.ServerPacketRegistry;
import io.netty.buffer.Unpooled;
import net.fabricmc.fabric.api.networking.v1.PacketSender;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.item.ItemStack;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.server.network.ServerPlayerEntity;

public class AttackStaminaCostPacketReceiver implements ServerPlayNetworking.PlayPacketHandler<AttackStaminaCostPacket> {

    @Override
    public void receive(AttackStaminaCostPacket packet, ServerPlayerEntity player, PacketSender responseSender) {

        ItemStack attackHandItemStack = packet.itemStack;

        if (((DuckPlayerEntityMixin) player).betteradventuremode$getStamina() <= 0) {
            PacketByteBuf data = new PacketByteBuf(Unpooled.buffer());
            data.writeInt(player.getId());
            ServerPlayNetworking.send(player, ServerPacketRegistry.CANCEL_ATTACK_PACKET, data);
        } else {
            if (attackHandItemStack.getItem() instanceof BasicWeaponItem weaponItem) {
                ((DuckPlayerEntityMixin) player).betteradventuremode$addStamina(-(weaponItem.getStaminaCost()));
            }
        }
    }
}
