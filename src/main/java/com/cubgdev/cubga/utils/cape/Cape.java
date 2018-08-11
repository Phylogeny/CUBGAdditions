package com.cubgdev.cubga.utils.cape;

import java.util.UUID;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraftforge.common.util.Constants;
import net.minecraftforge.common.util.INBTSerializable;

public class Cape implements INBTSerializable<NBTTagCompound> {

	private String name;
	private UUID[] users;

	private Cape() {
	}

	protected Cape(String name, String... users) {
		this.name = name;
		this.users = new UUID[users.length];
		for (int i = 0; i < users.length; i++) {
			this.users[i] = UUID.fromString(users[i]);
		}
	}

	@Override
	public NBTTagCompound serializeNBT() {
		NBTTagCompound nbt = new NBTTagCompound();
		nbt.setString("name", this.name);
		nbt.setInteger("userNum", this.users.length);
		NBTTagList users = new NBTTagList();
		for (int i = 0; i < this.users.length; i++) {
			NBTTagCompound tag = new NBTTagCompound();
			tag.setUniqueId(Integer.toString(i), this.users[i]);
			users.appendTag(tag);
		}
		nbt.setTag("users", users);
		return nbt;
	}

	@Override
	public void deserializeNBT(NBTTagCompound nbt) {
		this.name = nbt.getString("name");
		this.users = new UUID[nbt.getInteger("userNum")];
		NBTTagList users = nbt.getTagList("users", Constants.NBT.TAG_COMPOUND);
		for (int i = 0; i < users.tagCount(); i++) {
			this.users[i] = users.getCompoundTagAt(i).getUniqueId(Integer.toHexString(i));
		}
	}

	public boolean hasCape(EntityPlayer player) {
		for (int i = 0; i < this.users.length; i++) {
			if (this.users[i] != null && this.users[i].equals(player.getUniqueID())) {
				return true;
			}
		}
		return false;
	}

	public String getName() {
		return name;
	}

	public UUID[] getUsers() {
		return users;
	}

	public static Cape fromTag(NBTTagCompound nbt) {
		Cape cape = new Cape();
		cape.deserializeNBT(nbt);
		return cape;
	}
}