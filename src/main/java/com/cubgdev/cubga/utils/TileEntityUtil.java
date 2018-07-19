package com.cubgdev.cubga.utils;

import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

/**
 * Author: MrCrayfish
 */
public class TileEntityUtil
{
    public static void markBlockForUpdate(World world, BlockPos pos)
    {
        world.notifyBlockUpdate(pos, world.getBlockState(pos), world.getBlockState(pos), 3);
    }

    public static void markTileEntityForUpdate(TileEntity tileEntity)
    {
        World world = tileEntity.getWorld();
        BlockPos pos = tileEntity.getPos();
        world.notifyBlockUpdate(pos, world.getBlockState(pos), world.getBlockState(pos), 3);
    }
}
