package com.cubgdev.cubga.utils;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Scanner;

import com.cubgdev.cubga.CUBG;

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

	public static String getRemoteString(String urlQueryString) {
		try {
			URL url = new URL(urlQueryString);
			HttpURLConnection connection = (HttpURLConnection) url.openConnection();
			connection.setDoOutput(true);
			connection.setInstanceFollowRedirects(false);
			connection.setRequestMethod("GET");
			connection.setRequestProperty("Content-Type", "application/json");
			connection.setRequestProperty("charset", "utf-8");
			connection.connect();
			InputStream inStream = connection.getInputStream();
			return convertStreamToString(inStream);
		} catch (Exception e) {
			CUBG.logger().warn("Could not load \'" + urlQueryString + "\'", e);
		}
		return null;
	}

	private static String convertStreamToString(InputStream inputStream) {
		Scanner scanner = new Scanner(inputStream, "UTF-8");
		String text = scanner.useDelimiter("\\Z").next();
		scanner.close();
		return text;
	}
}