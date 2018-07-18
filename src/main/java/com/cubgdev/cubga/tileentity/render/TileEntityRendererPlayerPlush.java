package com.cubgdev.cubga.tileentity.render;

import java.util.Map;
import java.util.UUID;

import com.cubgdev.cubga.tileentity.TileEntityPlayerPlush;
import com.cubgdev.cubga.utils.TextureUtils;
import com.mojang.authlib.GameProfile;
import com.mojang.authlib.minecraft.MinecraftProfileTexture;
import com.mojang.authlib.minecraft.MinecraftProfileTexture.Type;

import net.minecraft.client.Minecraft;
import net.minecraft.client.model.ModelPlayer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.client.resources.DefaultPlayerSkin;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;

public class TileEntityRendererPlayerPlush extends TileEntitySpecialRenderer<TileEntityPlayerPlush> {

	private static ModelPlayer model = new ModelPlayer(1, false);
	private static ModelPlayer model_small = new ModelPlayer(1, true);
	private static Entity entity;

	@Override
	public void render(TileEntityPlayerPlush te, double x, double y, double z, float partialTicks, int destroyStage, float alpha) {
		super.render(te, x, y, z, partialTicks, destroyStage, alpha);

		GameProfile profile = te.getPlayerProfile();
		int rotation = te.getRotation();
		float scale = 0.0625f * 0.45f;
		World world = this.getWorld();
		ModelPlayer model = te.getSkinType().equalsIgnoreCase("slim") ? this.model_small : this.model;
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

		if (entity == null) {
			entity = new Entity(world) {
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

        GlStateManager.enableRescaleNormal();
		GlStateManager.enableBlendProfile(GlStateManager.Profile.PLAYER_SKIN);
		GlStateManager.pushMatrix();
		GlStateManager.translate(x + 0.5, y + 0.7, z + 0.5);
		GlStateManager.rotate(180, 1, 0, 0);
		GlStateManager.rotate(180, 0, 1, 0);
		GlStateManager.rotate(360 / 16 * rotation, 0, 1, 0);
		GlStateManager.scale(scale, scale, scale);

		TextureUtils.bindTexture(skinTexture);
		model.render(entity, 0, 0.001f, 0, 0, 0, 1);

		GlStateManager.popMatrix();
		GlStateManager.disableBlendProfile(GlStateManager.Profile.PLAYER_SKIN);
		GlStateManager.disableBlend();
		GlStateManager.disableRescaleNormal();
	}
}