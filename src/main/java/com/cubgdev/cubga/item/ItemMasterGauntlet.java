package com.cubgdev.cubga.item;

import com.cubgdev.cubga.CUBG;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.resources.I18n;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityFireworkRocket;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Enchantments;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;

import javax.annotation.Nullable;
import java.util.List;

public class ItemMasterGauntlet extends ItemBase
{
    public ItemMasterGauntlet() {
        super("master_gauntlet");
    }

    @Override
    public void onUpdate(ItemStack itemstack, World par2World, Entity par3Entity, int par4, boolean par5)
    {
        if(!itemstack.isItemEnchanted())
        {
            itemstack.addEnchantment(Enchantments.KNOCKBACK, 2);
        }
    }

    @Override
    public boolean hasEffect(ItemStack par1ItemStack)
    {
        return true;
    }

    @Override
    public ActionResult<ItemStack> onItemRightClick(World worldIn, EntityPlayer playerIn, EnumHand handIn)
    {
        if (playerIn.isElytraFlying())
        {
            ItemStack itemstack = playerIn.getHeldItem(handIn);

            if (!worldIn.isRemote)
            {
                EntityFireworkRocket entityfireworkrocket = new  EntityFireworkRocket(worldIn, itemstack, playerIn);
                worldIn.spawnEntity(entityfireworkrocket);
            }
            return new ActionResult<ItemStack>(EnumActionResult.SUCCESS, playerIn.getHeldItem(handIn));
        }
        else
        {
            return new ActionResult<ItemStack>(EnumActionResult.PASS, playerIn.getHeldItem(handIn));
        }
    }
}
