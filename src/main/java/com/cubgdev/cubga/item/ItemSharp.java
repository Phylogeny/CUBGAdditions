package com.cubgdev.cubga.item;

import net.minecraft.entity.Entity;
import net.minecraft.init.Enchantments;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;

public class ItemSharp extends ItemBase
{

	public ItemSharp(String id)
	{
		super(id);
	}

	@Override
	public void onUpdate(ItemStack itemstack, World par2World, Entity par3Entity, int par4, boolean par5)
	{
		if (!itemstack.isItemEnchanted())
		{
			itemstack.addEnchantment(Enchantments.SHARPNESS, 2);
		}
	}

	@Override
	public EntityEquipmentSlot getEquipmentSlot(ItemStack stack) {
		return EntityEquipmentSlot.HEAD;
	}

	@Override
	public double getDurabilityForDisplay(ItemStack stack){
		return (double)stack.getItemDamage() / (double)stack.getMaxDamage();
	}

	@Override
	public boolean hasEffect(ItemStack par1ItemStack)
	{
		return false;
	}
}
