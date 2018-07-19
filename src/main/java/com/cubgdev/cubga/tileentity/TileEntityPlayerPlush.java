package com.cubgdev.cubga.tileentity;

import java.util.UUID;

import javax.annotation.Nullable;

import com.cubgdev.cubga.utils.Access;
import com.google.common.collect.Iterables;
import com.mojang.authlib.GameProfile;
import com.mojang.authlib.minecraft.MinecraftSessionService;
import com.mojang.authlib.properties.Property;

import net.minecraft.block.BlockSkull;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTUtil;
import net.minecraft.network.play.server.SPacketUpdateTileEntity;
import net.minecraft.server.management.PlayerProfileCache;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.Mirror;
import net.minecraft.util.Rotation;
import net.minecraft.util.StringUtils;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class TileEntityPlayerPlush extends TileEntity {

	private int rotation;
	private String skinType;
	private GameProfile playerProfile;

	public TileEntityPlayerPlush() {
		this.rotation = 0;
		this.skinType = "default";
	}

	@Override
	public NBTTagCompound writeToNBT(NBTTagCompound nbt) {
		super.writeToNBT(nbt);
		nbt.setByte("Rot", (byte) (this.rotation & 255));
		nbt.setString("skinType", this.skinType);

		if (this.playerProfile != null) {
			NBTTagCompound nbttagcompound = new NBTTagCompound();
			NBTUtil.writeGameProfile(nbttagcompound, this.playerProfile);
			nbt.setTag("Owner", nbttagcompound);
		}

		return nbt;
	}

	@Override
	public void readFromNBT(NBTTagCompound nbt) {
		super.readFromNBT(nbt);
		this.rotation = nbt.getByte("Rot");
		this.skinType = nbt.getString("skinType");
		
		if (nbt.hasKey("Owner", 10)) {
			this.playerProfile = NBTUtil.readGameProfileFromNBT(nbt.getCompoundTag("Owner"));
		} else if (nbt.hasKey("ExtraType", 8)) {
			String s = nbt.getString("ExtraType");
			
			if (!StringUtils.isNullOrEmpty(s)) {
				this.playerProfile = new GameProfile((UUID) null, s);
				this.updatePlayerProfile();
			}
		}
	}

	@Nullable
	public GameProfile getPlayerProfile() {
		return this.playerProfile;
	}

	@Nullable
	@Override
	public SPacketUpdateTileEntity getUpdatePacket() {
		return new SPacketUpdateTileEntity(this.pos, 4, this.getUpdateTag());
	}

	@Override
	public NBTTagCompound getUpdateTag() {
		return this.writeToNBT(new NBTTagCompound());
	}

	public void setPlayerProfile(@Nullable GameProfile playerProfile) {
		this.playerProfile = playerProfile;
		this.updatePlayerProfile();
	}

	private void updatePlayerProfile() {
		this.playerProfile = updateGameprofile(this.playerProfile);
		world.notifyBlockUpdate(pos, world.getBlockState(pos), world.getBlockState(pos), 3);
		world.scheduleBlockUpdate(pos, this.getBlockType(), 0, 0);
		markDirty();
	}

	public static GameProfile updateGameprofile(GameProfile input) {
		if (input != null && !StringUtils.isNullOrEmpty(input.getName())) {
			if (input.isComplete() && input.getProperties().containsKey("textures")) {
				return input;
			} else if (Access.getPlayerProfileCache() != null && Access.getMinecraftSessionService() != null) {
				GameProfile gameprofile = Access.getPlayerProfileCache().getGameProfileForUsername(input.getName());

				if (gameprofile == null) {
					return input;
				} else {
					Property property = (Property) Iterables.getFirst(gameprofile.getProperties().get("textures"), (Object) null);

					if (property == null) {
						gameprofile = Access.getMinecraftSessionService().fillProfileProperties(gameprofile, true);
					}

					return gameprofile;
				}
			} else {
				return input;
			}
		} else {
			return input;
		}
	}
	
	public static void loadPlayerTexture(GameProfile profile) {
		
	}

	@SideOnly(Side.CLIENT)
	public int getRotation() {
		return this.rotation;
	}

	public String getSkinType() {
		return skinType;
	}

	public void setRotation(int rotation) {
		this.rotation = rotation;
	}

	public void mirror(Mirror mirrorIn) {
		if (this.world != null && this.world.getBlockState(this.getPos()).getValue(BlockSkull.FACING) == EnumFacing.UP) {
			this.rotation = mirrorIn.mirrorRotation(this.rotation, 16);
		}
	}

	public void rotate(Rotation rotationIn) {
		if (this.world != null && this.world.getBlockState(this.getPos()).getValue(BlockSkull.FACING) == EnumFacing.UP) {
			this.rotation = rotationIn.rotate(this.rotation, 16);
		}
	}
}