package com.github.theredbrain.betteradventuremode.client.network;

import net.minecraft.advancement.Advancement;
//import net.minecraft.advancement.AdvancementEntry;
import net.minecraft.advancement.AdvancementProgress;

public interface DuckClientAdvancementManagerMixin {
//    AdvancementProgress betteradventuremode$getAdvancementProgress(AdvancementEntry advancementEntry);
    AdvancementProgress betteradventuremode$getAdvancementProgress(Advancement advancement);
}
