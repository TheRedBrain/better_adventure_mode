package com.github.theredbrain.betteradventuremode.network.packet;

import com.github.theredbrain.betteradventuremode.BetterAdventureMode;
import net.fabricmc.fabric.api.networking.v1.FabricPacket;
import net.fabricmc.fabric.api.networking.v1.PacketType;
import net.minecraft.network.PacketByteBuf;

public class LeaveHouseFromHousingScreenPacket implements FabricPacket {
    public static final PacketType<LeaveHouseFromHousingScreenPacket> TYPE = PacketType.create(
            BetterAdventureMode.identifier("leave_house_from_housing_screen"),
            LeaveHouseFromHousingScreenPacket::new
    );

    public LeaveHouseFromHousingScreenPacket() {}

    public LeaveHouseFromHousingScreenPacket(PacketByteBuf buf) {
        this();
    }
    @Override
    public PacketType<?> getType() {
        return TYPE;
    }
    @Override
    public void write(PacketByteBuf buf) {}
}
