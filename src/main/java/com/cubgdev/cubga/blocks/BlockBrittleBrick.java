package com.cubgdev.cubga.blocks;

import com.cubgdev.cubga.CUBG;
import net.minecraft.block.Block;
import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;

public class BlockBrittleBrick extends Block
{
    public BlockBrittleBrick(String id)
    {
        super(Material.CLAY, MapColor.RED);
        this.setUnlocalizedName(id);
        this.setRegistryName(id);
        this.setCreativeTab(CUBG.TAB);
    }

    @Override
    public boolean isOpaqueCube(IBlockState state)
    {
        return false;
    }

    @Override
    public boolean isFullCube(IBlockState state)
    {
        return false;
    }
}
