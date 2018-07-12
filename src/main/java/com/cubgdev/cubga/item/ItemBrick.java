package com.cubgdev.cubga.item;

import com.cubgdev.cubga.entity.EntityThrowableBrick;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.resources.I18n;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumAction;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;

import javax.annotation.Nullable;
import java.util.List;

public class ItemBrick extends ItemBase {

    public ItemBrick(String id) {
        super(id);
    }

    @Override
    public EnumAction getItemUseAction(ItemStack stack) {
        return EnumAction.BOW;
    }

    @Override
    public int getMaxItemUseDuration(ItemStack stack) {
        return 72000;
    }

    @Override
    public void onPlayerStoppedUsing(ItemStack stack, World worldIn, EntityLivingBase entityLiving, int timeLeft) {
        if(entityLiving instanceof EntityPlayer) {
            if(!((EntityPlayer) entityLiving).capabilities.isCreativeMode) {
                stack.shrink(1);
            }
        }
        if (!worldIn.isRemote && entityLiving instanceof EntityPlayer)  {
            int duration = this.getMaxItemUseDuration(stack) - timeLeft;

            EntityPlayer player = (EntityPlayer) entityLiving;
            EntityThrowableBrick throwableBrick = new EntityThrowableBrick(worldIn, player);
            throwableBrick.shoot(player, player.rotationPitch, player.rotationYaw, 0.0F, Math.min(1.0F, duration / 20F), 1.0F);
            worldIn.spawnEntity(throwableBrick);
        }
    }

    @Override
    public ActionResult<ItemStack> onItemRightClick(World worldIn, EntityPlayer playerIn, EnumHand handIn) {
        ItemStack stack = playerIn.getHeldItem(handIn);
        playerIn.setActiveHand(handIn);
        return new ActionResult<>(EnumActionResult.SUCCESS, stack);
    }

    @Override
    public void addInformation(ItemStack stack, @Nullable World worldIn, List<String> tooltip, ITooltipFlag flagIn) {
        if (GuiScreen.isShiftKeyDown()) {
            String info = I18n.format("item." + this.getUnlocalizedName() + ".info");
            tooltip.addAll(Minecraft.getMinecraft().fontRenderer.listFormattedStringToWidth(info, 150));
        } else {
            tooltip.add(TextFormatting.YELLOW + I18n.format("item.show_info", "SHIFT"));
        }
    }
}
