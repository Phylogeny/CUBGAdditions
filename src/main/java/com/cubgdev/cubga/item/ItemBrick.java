package com.cubgdev.cubga.item;

import com.cubgdev.cubga.entity.EntityThrowableBrick;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.resources.I18n;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
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
    public ActionResult<ItemStack> onItemRightClick(World world, EntityPlayer entityPlayer, EnumHand hand) {
        ItemStack parItemStack = entityPlayer.getHeldItem(hand);

        if (!entityPlayer.capabilities.isCreativeMode) {
            parItemStack.shrink(1);
        }

        if (!world.isRemote)  {
            EntityThrowableBrick throwableBrick = new EntityThrowableBrick(world, entityPlayer);
            throwableBrick.shoot(entityPlayer, entityPlayer.rotationPitch, entityPlayer.rotationYaw, 0.0F, 1.5F, 1.0F);
            world.spawnEntity(throwableBrick);
        }

        return super.onItemRightClick(world, entityPlayer, hand);
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
