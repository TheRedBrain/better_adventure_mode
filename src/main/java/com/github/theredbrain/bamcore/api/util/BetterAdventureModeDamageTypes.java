package com.github.theredbrain.bamcore.api.util;

import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.damage.DamageType;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.util.Identifier;
import net.minecraft.world.World;

public class BetterAdventureModeDamageTypes {

    /*
     * Store the RegistryKey of our DamageType into a new constant called CUSTOM_DAMAGE_TYPE
     * The Identifier in use here points to our JSON file we created earlier.
     */
    public static final RegistryKey<DamageType> PLAYER_BASHING_DAMAGE_TYPE = RegistryKey.of(RegistryKeys.DAMAGE_TYPE, new Identifier("bamcore", "player_bashing_damage_type"));
    public static final RegistryKey<DamageType> PLAYER_PIERCING_DAMAGE_TYPE = RegistryKey.of(RegistryKeys.DAMAGE_TYPE, new Identifier("bamcore", "player_piercing_damage_type"));
    public static final RegistryKey<DamageType> PLAYER_SLASHING_DAMAGE_TYPE = RegistryKey.of(RegistryKeys.DAMAGE_TYPE, new Identifier("bamcore", "player_slashing_damage_type"));
    public static final RegistryKey<DamageType> PLAYER_UNARMED_DAMAGE_TYPE = RegistryKey.of(RegistryKeys.DAMAGE_TYPE, new Identifier("bamcore", "player_unarmed_damage_type"));

}