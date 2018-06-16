package com.cubgdev.cubga.item;

import com.cubgdev.cubga.CUBG;
import com.cubgdev.cubga.init.ModItems;
import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.resources.I18n;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.init.MobEffects;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.EnumAction;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.PotionEffect;
import net.minecraft.stats.StatList;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;

import javax.annotation.Nullable;
import java.util.List;

/**
 * Author: Ocelot5836
 */
public class ItemCoffee extends Item {

	private int useTime;

	public ItemCoffee(String id, int useTime) {
		this.useTime = useTime;
		this.setUnlocalizedName(id);
		this.setRegistryName(id);
		this.setCreativeTab(CUBG.TAB);
	}

	@Override
	public EntityEquipmentSlot getEquipmentSlot(ItemStack stack)
	{
		return EntityEquipmentSlot.HEAD;
	}

	@Override
	public void addInformation(ItemStack stack, @Nullable World worldIn, List<String> tooltip, ITooltipFlag flagIn) {
		if (GuiScreen.isShiftKeyDown()) {
			String info = I18n.format(this.getUnlocalizedName() + ".info");
			tooltip.addAll(Minecraft.getMinecraft().fontRenderer.listFormattedStringToWidth(info, 150));
		} else {
			tooltip.add(TextFormatting.YELLOW + I18n.format("item.show_info", "SHIFT"));
		}
	}

	@Override
	public ItemStack onItemUseFinish(ItemStack stack, World world, EntityLivingBase entityLiving) {
		if (entityLiving instanceof EntityPlayer) {
			EntityPlayer player = (EntityPlayer) entityLiving;
			this.onUse(stack, world, player);
			player.addPotionEffect(new PotionEffect(MobEffects.SPEED, 400, 2));
			player.inventory.addItemStackToInventory(new ItemStack(ModItems.COFFEE_CUP));
			player.addStat(StatList.getObjectUseStats(this));
			if (player instanceof EntityPlayerMP) {
				CriteriaTriggers.CONSUME_ITEM.trigger((EntityPlayerMP) player, stack);
			}
		}

		stack.shrink(1);
		return stack;
	}

	protected void onUse(ItemStack stack, World world, EntityPlayer player) {
	}

	@Override
	public int getMaxItemUseDuration(ItemStack stack) {
		return this.useTime;
	}

	@Override
	public EnumAction getItemUseAction(ItemStack stack) {
		return EnumAction.DRINK;
	}

	public ActionResult<ItemStack> onItemRightClick(World worldIn, EntityPlayer playerIn, EnumHand handIn)
	{
		playerIn.setActiveHand(handIn);
		return new ActionResult<ItemStack>(EnumActionResult.SUCCESS, playerIn.getHeldItem(handIn));
	}
}