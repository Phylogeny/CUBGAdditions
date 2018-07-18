package com.cubgdev.cubga.utils;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.math.BlockPos;

public class Lib
{

	public static NBTTagCompound writeBlockPos(BlockPos pos)
	{
		NBTTagCompound tag = new NBTTagCompound();
		tag.setInteger("x", pos.getX());
		tag.setInteger("y", pos.getY());
		tag.setInteger("z", pos.getZ());
		return tag;
	}
	
	public static BlockPos readBlockPos(NBTTagCompound nbt) {
		return new BlockPos(nbt.getInteger("x"), nbt.getInteger("y"), nbt.getInteger("z"));
	}
}