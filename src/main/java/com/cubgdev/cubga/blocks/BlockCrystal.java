package com.cubgdev.cubga.blocks;

import com.cubgdev.cubga.CUBG;
import com.cubgdev.cubga.tileentity.TileEntityCrystal;
import com.cubgdev.cubga.tileentity.TileEntityCrystalContainer;

import net.minecraft.block.Block;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.resources.I18n;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumBlockRenderType;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

import javax.annotation.Nullable;
import java.util.List;

/**
 * Author: JacksonPlayzYT
 */

public class BlockCrystal extends Block implements ITileEntityProvider {


    public BlockCrystal(String name) {
        super(Material.IRON);
        this.setUnlocalizedName(name);
        this.setRegistryName(name);
        this.setLightLevel(1.5f);
        this.setCreativeTab(CUBG.TAB);
    }

    @Override
    public void addInformation(ItemStack stack, @Nullable World world, List<String> tooltip, ITooltipFlag flag) {
        if (GuiScreen.isShiftKeyDown()) {
            String info = I18n.format(this.getUnlocalizedName() + ".info");
            tooltip.addAll(Minecraft.getMinecraft().fontRenderer.listFormattedStringToWidth(info, 150));
        } else {
            tooltip.add(TextFormatting.YELLOW + I18n.format("item.show_info", "SHIFT"));
        }
    }

    @Override
    public EnumBlockRenderType getRenderType(IBlockState state) {
        return EnumBlockRenderType.INVISIBLE;
    }

    @Override
    public boolean isOpaqueCube(IBlockState state) {
        return false;
    }

    @Override
    public boolean isFullCube(IBlockState state) {
        return false;
    }

	@Override
	public TileEntity createNewTileEntity(World world, int meta)
	{
		return new TileEntityCrystalContainer();
	}
}

