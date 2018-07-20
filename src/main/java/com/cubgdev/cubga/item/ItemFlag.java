package com.cubgdev.cubga.item;

import com.cubgdev.cubga.CUBG;
import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.resources.I18n;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.Entity;
import net.minecraft.init.Enchantments;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;

import javax.annotation.Nullable;
import java.util.List;

public class ItemFlag extends ItemBlockBase
{
    public ItemFlag(Block block) {
        super(block);
    }

    @Override
    public void onUpdate(ItemStack itemstack, World par2World, Entity par3Entity, int par4, boolean par5)
    {
        if(itemstack.isItemEnchanted() == false)
        {
            itemstack.addEnchantment(Enchantments.KNOCKBACK, 3);
        }
    }

    @Override
    public EntityEquipmentSlot getEquipmentSlot(ItemStack stack) {
        return EntityEquipmentSlot.HEAD;
    }

    @Override
    public boolean hasEffect(ItemStack par1ItemStack)
    {
        return false;
    }
}
