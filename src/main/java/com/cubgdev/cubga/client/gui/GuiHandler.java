package com.cubgdev.cubga.client.gui;

import com.cubgdev.cubga.tileentity.IValueContainer;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.network.IGuiHandler;

import javax.annotation.Nullable;

/**
 * Author: MrCrayfish
 */
public class GuiHandler implements IGuiHandler
{
    @Nullable
    @Override
    public Object getServerGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z)
    {
        return null;
    }

    @Nullable
    @Override
    public Object getClientGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z)
    {
        TileEntity tileEntity = world.getTileEntity(new BlockPos(x, y, z));
        if(tileEntity instanceof IValueContainer && ID == 1)
        {
            return new GuiEditValueContainer((IValueContainer) tileEntity);
        }
        return null;
    }
}
