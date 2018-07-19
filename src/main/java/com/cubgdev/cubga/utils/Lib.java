package com.cubgdev.cubga.utils;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;

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
	
	public static NBTTagCompound writeVector3d(Vec3d vector) {
		NBTTagCompound tag = new NBTTagCompound();
		tag.setDouble("x", vector.x);
		tag.setDouble("y", vector.y);
		tag.setDouble("z", vector.z);
		return tag;
	}
	
	public static BlockPos readBlockPos(NBTTagCompound nbt) {
		return new BlockPos(nbt.getInteger("x"), nbt.getInteger("y"), nbt.getInteger("z"));
	}
	
	public static Vec3d readVector3d(NBTTagCompound nbt) {
		return new Vec3d(nbt.getDouble("x"), nbt.getDouble("y"), nbt.getDouble("z"));
	}
}