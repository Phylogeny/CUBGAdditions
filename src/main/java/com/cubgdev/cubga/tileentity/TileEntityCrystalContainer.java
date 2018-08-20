package com.cubgdev.cubga.tileentity;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Nullable;

import com.cubgdev.cubga.utils.Lib;

import com.cubgdev.cubga.utils.TileEntityUtil;
import net.minecraft.client.Minecraft;
import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.server.SPacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.common.util.Constants;

/**
 * Author: CoffeeCatRailway
 */
public class TileEntityCrystalContainer extends TileEntity
{
	private boolean renderBase;
	private float lastInnerRotation;
	private float innerRotation;
	private float speed;
	private float scale;
	private List<BlockPos> beamPositions;

	public TileEntityCrystalContainer()
	{
		this(false, 1.0f);
	}

	public TileEntityCrystalContainer(boolean renderBase, float scale)
	{
		this.renderBase = renderBase;
		this.lastInnerRotation = 0.0f;
		this.innerRotation = 0.0f;
		this.speed = 1.0f;
		this.scale = scale;
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
		nbt.getBoolean("renderBase");
		nbt.setFloat("speed", this.speed);
		nbt.setFloat("scale", this.scale);

		NBTTagList positions = new NBTTagList();
		for (int i = 0; i < this.beamPositions.size(); i++)
		{
			NBTTagCompound beam = new NBTTagCompound();
			beam.setTag("position", Lib.writeBlockPos(this.beamPositions.get(i)));
			positions.appendTag(beam);
		}
		nbt.setTag("beams", positions);
		return nbt;
	}

	@Override
	public void readFromNBT(NBTTagCompound nbt)
	{
		super.readFromNBT(nbt);
		this.renderBase = nbt.getBoolean("renderBase");
		this.speed = nbt.getFloat("speed");
		this.scale = nbt.getFloat("scale");
		this.beamPositions = new ArrayList<>();
		NBTTagList positions = nbt.getTagList("beams", Constants.NBT.TAG_COMPOUND);
		for (NBTBase tag : positions)
		{
			if (tag instanceof NBTTagCompound)
			{
				NBTTagCompound beam = (NBTTagCompound) tag;
				this.beamPositions.add(Lib.readBlockPos(beam.getCompoundTag("position")));
			}
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
		TileEntityUtil.markTileEntityForUpdate(this);
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
		}
		else
		{
			this.beamPositions.remove(index);
			return true;
		}
	}

	@Override
	public void onDataPacket(NetworkManager net, SPacketUpdateTileEntity pkt)
	{
		this.readFromNBT(pkt.getNbtCompound());
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

	public boolean renderBase()
	{
		return renderBase;
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
		return this.beamPositions.toArray(new BlockPos[0]);
	}
}
