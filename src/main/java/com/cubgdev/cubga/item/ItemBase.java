package com.cubgdev.cubga.item;

import com.cubgdev.cubga.CUBG;

import net.minecraft.item.EnumAction;
import net.minecraft.item.Item;
import net.minecraftforge.common.util.EnumHelper;

public class ItemBase extends Item {

	public ItemBase(String id) {
		this.setUnlocalizedName(id);
		this.setRegistryName(id);
		this.setCreativeTab(CUBG.TAB);
	}
}
