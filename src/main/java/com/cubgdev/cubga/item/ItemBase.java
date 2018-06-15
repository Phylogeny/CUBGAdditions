package com.cubgdev.cubga.item;

import com.cubgdev.cubga.CUBG;
import net.minecraft.item.Item;

public class ItemBase extends Item {
    public ItemBase(String id) {
        this.setUnlocalizedName(id);
        this.setRegistryName(id);
        this.setCreativeTab(CUBG.TAB);
    }
}
