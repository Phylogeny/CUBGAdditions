package com.cubgdev.cubga.tileentity;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.Nullable;

import com.cubgdev.cubga.utils.Lib;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

import net.minecraft.client.Minecraft;
import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.network.play.server.SPacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;

/**
 * Author: CoffeeCatRailway
 */
public class TileEntityCrystalContainer extends TileEntity
{
	public static final Map<String, TileEntityCrystalContainer> CRYSTALS = Maps.<String, TileEntityCrystalContainer>newHashMap();
	public static final List<BlockPos> REMOVED = Lists.<BlockPos>newArrayList();

	private float lastInnerRotation;
	private float innerRotation;
	private float speed;
	private float scale;
	private List<BlockPos> beamPositions;

	public TileEntityCrystalContainer()
	{
		this.lastInnerRotation = 0.0f;
		this.innerRotation = 0.0f;
		this.speed = 1.0f;
		this.scale = 1.0f;
		this.beamPositions = new ArrayList<BlockPos>();
	}

	public void updateRotation()
	{
		this.lastInnerRotation = this.innerRotation;
		this.innerRotation += 0.5f * this.getSpeed();
	}

	@Override
	public NBTTagCompound writeToNBT(NBTTagCompound nbt)
	{
		super.writeToNBT(nbt);
		nbt.setFloat("speed", this.speed);
		nbt.setFloat("scale", this.scale);

		NBTTagList positions = new NBTTagList();
		for (int i = 0; i < this.beamPositions.size(); i++)
		{
			positions.appendTag(Lib.writeBlockPos(this.beamPositions.get(i)));
		}
		nbt.setTag("beamPositions", positions);
		return nbt;
	}

	@Override
	public void readFromNBT(NBTTagCompound nbt)
	{
		super.readFromNBT(nbt);
		this.speed = nbt.getFloat("speed");
		this.scale = nbt.getFloat("scale");
		this.beamPositions = new ArrayList<BlockPos>();
		NBTTagList positions = nbt.getTagList("beamPositions", 0);
		for (NBTBase tag : positions)
		{
			if (tag instanceof NBTTagCompound)
			{
				this.beamPositions.add(Lib.readBlockPos((NBTTagCompound) tag));
			}
		}

		if (!CRYSTALS.containsKey(this.getPos().toString()))
		{
			CRYSTALS.put(this.pos.toString(), this);
		}
	}

	public boolean addBeamPosition(BlockPos beamPosition)
	{
		for (int i = 0; i < this.beamPositions.size(); i++)
		{
			BlockPos pos = this.beamPositions.get(i);
			if (pos.getX() == beamPosition.getX() && pos.getY() == beamPosition.getY() && pos.getZ() == beamPosition.getZ())
			{
				return false;
			}
		}
		this.beamPositions.add(beamPosition);
		return true;
	}

	public boolean removeBeamPosition(BlockPos beamPosition)
	{
		int index = -1;
		for (int i = 0; i < this.beamPositions.size(); i++)
		{
			BlockPos pos = this.beamPositions.get(i);
			if (pos.getX() == beamPosition.getX() && pos.getY() == beamPosition.getY() && pos.getZ() == beamPosition.getZ())
			{
				index = i;
				break;
			}
		}
		if (index == -1)
		{
			return false;
		} else
		{
			this.beamPositions.remove(index);
			return true;
		}
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

	public float getInnerRotation()
	{
		return this.lastInnerRotation + (this.innerRotation - this.lastInnerRotation) * Minecraft.getMinecraft().getRenderPartialTicks();
	}

	public float getSpeed()
	{
		return speed;
	}

	public float getScale()
	{
		return scale;
	}

	public BlockPos[] getBeamPositions()
	{
		return beamPositions.toArray(new BlockPos[0]);
	}

	public static void removeCrystal(BlockPos pos)
	{
		REMOVED.add(pos);
	}
}
