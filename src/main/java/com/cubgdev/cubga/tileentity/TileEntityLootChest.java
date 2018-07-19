package com.cubgdev.cubga.tileentity;

import com.cubgdev.cubga.Reference;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.server.SPacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntityChest;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.util.Constants;

/**
 * Author: MrCrayfish
 */
public class TileEntityLootChest extends TileEntityChest
{
    private int color = 16750848;
    private ResourceLocation chestTexture = null;

    @Override
    public void readFromNBT(NBTTagCompound compound)
    {
        super.readFromNBT(compound);
        chestTexture = null;
        if(compound.hasKey("ChestTexture", Constants.NBT.TAG_STRING))
        {
            chestTexture = new ResourceLocation(compound.getString("ChestTexture"));
        }
        if(compound.hasKey("Color", Constants.NBT.TAG_INT_ARRAY))
        {
            int[] c = compound.getIntArray("Color");
            if(c.length == 3)
            {
                color = ((c[0] & 0xFF) << 16) | ((c[1] & 0xFF) << 8) | ((c[2] & 0xFF));
            }
        }
    }

    @Override
    public NBTTagCompound writeToNBT(NBTTagCompound compound)
    {
        super.writeToNBT(compound);
        if(chestTexture != null)
        {
            compound.setString("ChestTexture", chestTexture.toString());
        }
        compound.setIntArray("Color", this.getColor());
        return compound;
    }

    public ResourceLocation getChestTexture()
    {
        return chestTexture;
    }

    public void setChestTexture(ResourceLocation chestTexture)
    {
        this.chestTexture = chestTexture;
    }

    public void setColor(int[] color)
    {
        this.color = ((color[0] & 0xFF) << 16) | ((color[1] & 0xFF) << 8) | ((color[2] & 0xFF));
    }

    public int[] getColor()
    {
        return new int[]{ (color >> 16) & 0xFF, (color >> 8) & 0xFF, color & 0xFF };
    }

    @Override
    public void onDataPacket(NetworkManager net, SPacketUpdateTileEntity pkt)
    {
        this.readFromNBT(pkt.getNbtCompound());
    }

    @Override
    public final NBTTagCompound getUpdateTag()
    {
        System.out.println(this.getColor());
        NBTTagCompound t = this.writeToNBT(new NBTTagCompound());
        return t;
    }

    @Override
    public SPacketUpdateTileEntity getUpdatePacket()
    {
        return new SPacketUpdateTileEntity(pos, 0, getUpdateTag());
    }
}
