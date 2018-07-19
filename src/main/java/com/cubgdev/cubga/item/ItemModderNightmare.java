package com.cubgdev.cubga.item;

import net.minecraft.entity.Entity;
import net.minecraft.init.Enchantments;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class ItemModderNightmare extends ItemBase
{

	public ItemModderNightmare()
	{
		super("modder_nightmare");
	}

	@Override
	public void onUpdate(ItemStack itemstack, World par2World, Entity par3Entity, int par4, boolean par5)
	{
		if (itemstack.isItemEnchanted() == false)
		{
			itemstack.addEnchantment(Enchantments.SHARPNESS, 2);
		}
	}

	@Override
	public boolean hasEffect(ItemStack par1ItemStack)
	{
		return false;
	}
}
