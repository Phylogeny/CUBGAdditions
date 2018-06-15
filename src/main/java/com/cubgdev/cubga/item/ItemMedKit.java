package com.cubgdev.cubga.item;

import com.cubgdev.cubga.CUBG;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.resources.I18n;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.EnumAction;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;

import javax.annotation.Nullable;
import java.util.List;

public class ItemMedKit extends Item {

    public ItemMedKit(String id) {
        this.setUnlocalizedName(id);
        this.setRegistryName(id);
        this.setCreativeTab(CUBG.TAB);
    }

    @Override
    public void addInformation(ItemStack stack, @Nullable World worldIn, List<String> tooltip, ITooltipFlag flagIn) {
        if (GuiScreen.isShiftKeyDown()) {
            String info = I18n.format("item.medkit.info");
            tooltip.addAll(Minecraft.getMinecraft().fontRenderer.listFormattedStringToWidth(info, 150));
        }
        else
            {
            tooltip.add(TextFormatting.RED + "Hold SHIFT for Info");
            }
    }
}
