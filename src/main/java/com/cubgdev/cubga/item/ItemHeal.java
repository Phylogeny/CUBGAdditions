package com.cubgdev.cubga.item;

import java.util.List;

import javax.annotation.Nullable;

import com.cubgdev.cubga.CUBG;

import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.resources.I18n;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.EnumAction;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.stats.StatList;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;

/**
 * Author: Ocelot5836
 */
public class ItemHeal extends Item {

	private int useTime;
	private float healAmount;

	public ItemHeal(String id, int useTime, float healAmount) {
		this.useTime = useTime;
		this.healAmount = healAmount;
		this.setUnlocalizedName(id);
		this.setRegistryName(id);
		this.setCreativeTab(CUBG.TAB);
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
			player.heal(this.healAmount);
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
		return EnumAction.BOW;
	}

	@Override
	public ActionResult<ItemStack> onItemRightClick(World world, EntityPlayer player, EnumHand hand) {
		if (player.getHealth() != player.getMaxHealth()) {
			player.setActiveHand(hand);
			return new ActionResult<ItemStack>(EnumActionResult.SUCCESS, player.getHeldItem(hand));
		} else {
			return new ActionResult<ItemStack>(EnumActionResult.FAIL, player.getHeldItem(hand));
		}
	}
}