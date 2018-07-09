package com.cubgdev.cubga.tileentity;

import com.cubgdev.cubga.common.CommonEvents;
import net.minecraft.block.BlockTallGrass;
import net.minecraft.init.Blocks;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ITickable;

/**
 * Author: MrCrayfish
 */
public class TileEntityBrittleBrick extends TileEntity implements ITickable {

    private boolean grass;

    public void setGrass(boolean grass)
    {
        this.grass = grass;
    }

    public boolean isGrass()
    {
        return grass;
    }

    @Override
    public void update() {
        if(CommonEvents.replaceBricks) {
            if(grass) {
                world.setBlockState(pos, Blocks.TALLGRASS.getDefaultState().withProperty(BlockTallGrass.TYPE, BlockTallGrass.EnumType.GRASS));
            } else {
                world.setBlockToAir(pos);
            }
        }
    }
}
