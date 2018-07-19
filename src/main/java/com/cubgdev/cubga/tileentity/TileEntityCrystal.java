package com.cubgdev.cubga.tileentity;

import javax.annotation.Nullable;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.play.server.SPacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class TileEntityCrystal extends TileEntity
{

	public float innerRotation;
	private float speed;
	private float scale;

	public TileEntityCrystal()
	{
		this.innerRotation = 0.0f;
		this.speed = 1.0f;
		this.scale = 2.0f;
	}

	@Override
	public NBTTagCompound writeToNBT(NBTTagCompound nbt)
	{
		super.writeToNBT(nbt);
		nbt.setFloat("speed", this.speed);
		nbt.setFloat("scale", this.scale);
		return nbt;
	}

	@Override
	public void readFromNBT(NBTTagCompound nbt)
	{
		super.readFromNBT(nbt);
		this.speed = nbt.getFloat("speed");
		this.scale = nbt.getFloat("scale");
	}

	@Nullable
	@Override
	public SPacketUpdateTileEntity getUpdatePacket()
	{
		return new SPacketUpdateTileEntity(this.pos, 0, this.getUpdateTag());
	}

	@Override
	public NBTTagCompound getUpdateTag()
	{
		return this.writeToNBT(new NBTTagCompound());
	}

	@Override
	public AxisAlignedBB getRenderBoundingBox()
	{
		return INFINITE_EXTENT_AABB;
	}

	public float getInnerRotation()
	{
		return innerRotation;
	}

	public float getSpeed()
	{
		return speed;
	}

	public float getScale()
	{
		return scale;
	}
}