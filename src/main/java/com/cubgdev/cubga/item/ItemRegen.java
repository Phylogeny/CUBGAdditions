package com.cubgdev.cubga.item;

import com.cubgdev.cubga.CUBG;
import com.cubgdev.cubga.init.ModItems;
import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.init.MobEffects;
import net.minecraft.item.EnumAction;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.PotionEffect;
import net.minecraft.stats.StatList;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.world.World;

public class ItemRegen extends ItemBase {

    private int useTime;

    public ItemRegen(String id, int useTime) {
        super(id);
        this.setCreativeTab(CUBG.TAB);
        this.useTime = useTime;
    }

    @Override
    public ItemStack onItemUseFinish(ItemStack stack, World world, EntityLivingBase entityLiving) {
        if (entityLiving instanceof EntityPlayer) {
            EntityPlayer player = (EntityPlayer) entityLiving;
            this.onUse(stack, world, player);
            player.addPotionEffect(new PotionEffect(MobEffects.REGENERATION, 50, 1));
            player.addStat(StatList.getObjectUseStats(this));
            if (player instanceof EntityPlayerMP) {
                CriteriaTriggers.CONSUME_ITEM.trigger((EntityPlayerMP) player, stack);
            }
        }

        stack.shrink(1);
        return stack;
    }

    protected void onUse(ItemStack stack, World world, EntityPlayer player)
    {

    }

    @Override
    public int getMaxItemUseDuration(ItemStack stack) {
        return this.useTime;
    }

    @Override
    public EnumAction getItemUseAction(ItemStack stack) {
        return EnumAction.DRINK;
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
