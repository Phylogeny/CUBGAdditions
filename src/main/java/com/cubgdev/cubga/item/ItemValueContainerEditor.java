package com.cubgdev.cubga.item;

import com.cubgdev.cubga.CUBG;
import com.cubgdev.cubga.client.gui.GuiEditValueContainer;
import com.cubgdev.cubga.tileentity.IValueContainer;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

/**
 * Author: MrCrayfish
 */
public class ItemValueContainerEditor extends ItemBase
{
    public ItemValueContainerEditor()
    {
        super("value_container_editor");
    }

    @Override
    public EnumActionResult onItemUseFirst(EntityPlayer player, World world, BlockPos pos, EnumFacing side, float hitX, float hitY, float hitZ, EnumHand hand)
    {
        if(world.isRemote)
        {
            TileEntity tileEntity = world.getTileEntity(pos);
            if(tileEntity instanceof IValueContainer)
            {
                player.openGui(CUBG.instance, 1, world, pos.getX(), pos.getY(), pos.getZ());
            }
        }
        return EnumActionResult.SUCCESS;
    }
}
