package com.github.kisaragieffective.xaeromapnullpotion;

import net.minecraft.potion.Potion;
import net.minecraft.util.ResourceLocation;

public final class NullPotion extends Potion {
    public NullPotion(String modId, String name) {
        super(false, 0xFF000000);
        ResourceLocation rl = new ResourceLocation(modId, name);
        this
                .setRegistryName(rl)
                .setPotionName("effect." + rl.getNamespace() + "." + rl.getPath());
    }
}
