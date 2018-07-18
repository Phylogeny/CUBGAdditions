package com.cubgdev.cubga.client.render;

import java.util.Map;
import java.util.UUID;

import com.cubgdev.cubga.tileentity.TileEntityPlayerPlush;
import com.cubgdev.cubga.utils.Access;
import com.cubgdev.cubga.utils.TextureUtils;
import com.mojang.authlib.GameProfile;
import com.mojang.authlib.minecraft.MinecraftProfileTexture;
import com.mojang.authlib.minecraft.MinecraftProfileTexture.Type;

import net.minecraft.client.Minecraft;
import net.minecraft.client.model.ModelPlayer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.block.model.ItemCameraTransforms.TransformType;
import net.minecraft.client.resources.DefaultPlayerSkin;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTUtil;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.relauncher.Side;

public class ItemRenderPlayerPlush extends ItemRenderer {

	public static final ModelPlayer DEFAULT_PLAYER = new ModelPlayer(1, false);
	public static final ModelPlayer SLIM_PLAYER = new ModelPlayer(1, true);

	private static Entity fakeEntity;

	public ItemRenderPlayerPlush() {
		super(null, null);
	}

	@Override
	public void renderFirstPersonLeft(ItemStack itemstack, EntityLivingBase entity, TransformType cameraTransformType) {
		GlStateManager.translate(-0.915, -0.1, -0.1);
		GlStateManager.rotate(45, 0, 1, 0);
		render();
	}

	@Override
	public void renderFirstPersonRight(ItemStack itemstack, EntityLivingBase entity, TransformType cameraTransformType) {
		GlStateManager.translate(0.2, -0.1, -0.8);
		GlStateManager.rotate(-45, 0, 1, 0);
		render();
	}

	@Override
	public void renderInInventory(ItemStack itemstack, EntityLivingBase entity, TransformType cameraTransformType) {
		GlStateManager.rotate(30, 1, 0, 0);
		GlStateManager.rotate(45, 0, 1, 0);
		GlStateManager.translate(-1.5, 0.2, 0);
		GlStateManager.scale(1.5, 1.5, 1.5);
		render();
	}

	@Override
	public void renderInWorld(ItemStack itemstack, EntityLivingBase entity, TransformType cameraTransformType) {
		GlStateManager.translate(-0.38, -0.2, -0.38);
		GlStateManager.scale(0.75, 0.75, 0.75);
		render();
	}

	@Override
	public void renderThirdPersonLeft(ItemStack itemstack, EntityLivingBase entity, TransformType cameraTransformType) {
		GlStateManager.rotate(75, 1, 0, 0);
		GlStateManager.rotate(45, 0, 1, 0);
		GlStateManager.translate(-0.45, -0.05, -0.45);
		GlStateManager.scale(0.875, 0.875, 0.875);
		render();
	}

	@Override
	public void renderThirdPersonRight(ItemStack itemstack, EntityLivingBase entity, TransformType cameraTransformType) {
		GlStateManager.rotate(75, 1, 0, 0);
		GlStateManager.rotate(45, 0, 1, 0);
		GlStateManager.rotate(-90, 0, 1, 0);
		GlStateManager.translate(-0.45, -0.05, -0.45);
		GlStateManager.scale(0.875, 0.875, 0.875);
		render();
	}

	@Override
	public void renderFixed(ItemStack itemstack, EntityLivingBase entity, TransformType cameraTransformType) {
	}

	@Override
	public void renderHead(ItemStack itemstack, EntityLivingBase entity, TransformType cameraTransformType) {
	}

	private void render() {
		GameProfile profile = null;
		if (stack.hasTagCompound()) {
			NBTTagCompound nbt = stack.getTagCompound();
			profile = TileEntityPlayerPlush.updateGameprofile(NBTUtil.readGameProfileFromNBT(nbt.getCompoundTag("Owner")));
		}

		float scale = 0.0625f * 0.25f;
		ModelPlayer model = this.DEFAULT_PLAYER;
		ResourceLocation skinTexture = DefaultPlayerSkin.getDefaultSkinLegacy();

		if (profile != null) {
			Minecraft minecraft = Minecraft.getMinecraft();
			Map<Type, MinecraftProfileTexture> map = minecraft.getSkinManager().loadSkinFromCache(profile);

			if (map.containsKey(Type.SKIN)) {
				skinTexture = minecraft.getSkinManager().loadSkin(map.get(Type.SKIN), Type.SKIN);
			} else {
				UUID uuid = EntityPlayer.getUUID(profile);
				skinTexture = DefaultPlayerSkin.getDefaultSkin(uuid);
			}
		}

		if (fakeEntity == null && this.entity != null) {
			fakeEntity = new Entity(this.entity.world) {
				@Override
				protected void writeEntityToNBT(NBTTagCompound nbt) {
				}

				@Override
				protected void readEntityFromNBT(NBTTagCompound nbt) {
				}

				@Override
				protected void entityInit() {
				}
			};
		}
		model.isChild = false;

		GlStateManager.pushMatrix();
		GlStateManager.translate(0.5, 0.4, 0.5);
		GlStateManager.rotate(180, 1, 0, 0);
		GlStateManager.scale(scale, scale, scale);

		if (fakeEntity != null) {
			TextureUtils.bindTexture(skinTexture);
			model.render(fakeEntity, 0, 0.001f, 1, 0, 0, 1);
		}

		GlStateManager.popMatrix();
	}
}