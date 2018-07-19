package com.cubgdev.cubga.init;

import com.cubgdev.cubga.CUBG;
import com.cubgdev.cubga.Reference;
import com.cubgdev.cubga.entity.EntityThrowableBrick;
import net.minecraft.entity.Entity;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.common.registry.EntityRegistry;

public class ModEntities {

    // CoffeeCatRailway - Forgot to change entity id!
    public static void init() {
        registerEntity("brick", EntityThrowableBrick.class, 0, 80);
    }

    private static void registerEntity(String name, Class<? extends Entity> entity, int id, int trackingRange) {
        EntityRegistry.registerModEntity(new ResourceLocation(Reference.MOD_ID + ":" + name), entity, name, id, CUBG.instance, trackingRange, 1, true);
    }
}
