package com.cubgdev.cubga.client;

import java.util.Map;

import com.cubgdev.cubga.Reference;
import com.cubgdev.cubga.tileentity.TileEntityCrystalContainer;
import com.cubgdev.cubga.utils.TextureUtils;
import com.google.common.collect.Maps;

import net.minecraft.client.Minecraft;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelEnderCrystal;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.entity.RenderDragon;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.client.event.RenderWorldLastEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class RenderEvents
{

	private static final ResourceLocation ENDER_CRYSTAL_TEXTURES = new ResourceLocation(Reference.MOD_ID,"textures/models/crystal.png");
	private static final ModelBase ENDER_CRYSTAL_MODEL = new ModelEnderCrystal(0.0F, false);
	private static final Map<BlockPos, TileEntity> NEARBY_TILE_ENTITIES = Maps.<BlockPos, TileEntity>newHashMap();

	@SubscribeEvent
	public void onRenderWorldEvent(RenderWorldLastEvent event)
	{
		GlStateManager.pushMatrix();

		EntityPlayer player = Minecraft.getMinecraft().player;
		World world = player.world;
		float partialTicks = event.getPartialTicks();

		double posX = player.lastTickPosX + (player.posX - player.lastTickPosX) * partialTicks;
		double posY = player.lastTickPosY + (player.posY - player.lastTickPosY) * partialTicks;
		double posZ = player.lastTickPosZ + (player.posZ - player.lastTickPosZ) * partialTicks;

		GlStateManager.translate(-posX, -posY, -posZ);

		TextureUtils.bindTexture(ENDER_CRYSTAL_TEXTURES);

		this.renderCrystalContainers(player, world, partialTicks);

		GlStateManager.popMatrix();
	}

	private void renderCrystalContainers(EntityPlayer player, World world, float partialTicks)
	{
		int chunkSize = 8;

		for (int i = 0; i < chunkSize * chunkSize; i++)
		{
			int x = (i % chunkSize) - chunkSize / 2;
			int z = (i / chunkSize) - chunkSize / 2;
			NEARBY_TILE_ENTITIES.putAll(world.getChunkFromBlockCoords(player.getPosition().add(x * 16, 0, z * 16)).getTileEntityMap());
		}

		for (BlockPos position : NEARBY_TILE_ENTITIES.keySet())
		{
			if (NEARBY_TILE_ENTITIES.get(position) instanceof TileEntityCrystalContainer)
			{
				TileEntityCrystalContainer te = (TileEntityCrystalContainer) NEARBY_TILE_ENTITIES.get(position);
				BlockPos fromPos = te.getPos();
				if (Minecraft.getMinecraft().player.getPosition().getDistance(fromPos.getX(), fromPos.getY(), fromPos.getZ()) < 128D)
				{
					te.updateRotation();
					double innerRotation = te.getInnerRotation();
					double innerRotationSin = Math.sin(innerRotation * 0.2F) / 2.0F + 0.45F;
					innerRotationSin = innerRotationSin * innerRotationSin + innerRotationSin;

					double scale = 0.3f * te.getScale();

					GlStateManager.pushMatrix();
					GlStateManager.disableLighting();
					{
						TextureUtils.bindTexture(ENDER_CRYSTAL_TEXTURES);
						GlStateManager.translate(fromPos.getX(), fromPos.getY(), fromPos.getZ());
						GlStateManager.translate(0.5 * (1 / te.getScale()), 0.25, 0.5 * (1 / te.getScale()));
						GlStateManager.scale(scale, scale, scale);
						this.ENDER_CRYSTAL_MODEL.render(null, 0.0F, (float) (innerRotation * 3.0), (float) (innerRotationSin * 0.1), 0.0F, 0.0F, 0.0625F);
					}
					GlStateManager.enableLighting();
					GlStateManager.popMatrix();

					BlockPos[] beamPositions = te.getBeamPositions();
					if (beamPositions != null)
					{
						for (int i = 0; i < beamPositions.length; i++)
						{
							BlockPos toPos = beamPositions[i];
							if (toPos != null)
							{
								GlStateManager.pushMatrix();
								GlStateManager.translate(0.5 * (1 / te.getScale()), -0.1 * te.getScale(), 0.5 * (1 / te.getScale()));
								GlStateManager.scale(scale, scale, scale);
								TextureUtils.bindTexture(RenderDragon.ENDERCRYSTAL_BEAM_TEXTURES);
								float f2 = (float) toPos.getX();
								float f3 = (float) toPos.getY() - 0.25f;
								float f4 = (float) toPos.getZ();
								double d0 = (double) f2 - fromPos.getX();
								double d1 = (double) f3 - fromPos.getY();
								double d2 = (double) f4 - fromPos.getZ();
								RenderDragon.renderCrystalBeams((fromPos.getX() + d0) * (1 / scale), (fromPos.getY() + 0.25D + d1) * (1 / scale), (fromPos.getZ() + d2) * (1 / scale), partialTicks, (double) f2 * (1 / scale), (double) f3 * (1 / scale), (double) f4 * (1 / scale), (int) innerRotation, fromPos.getX() * (1 / scale), fromPos.getY() * (1 / scale), fromPos.getZ() * (1 / scale));
								GlStateManager.popMatrix();
							}
						}
					}
				}
			}
		}
		NEARBY_TILE_ENTITIES.clear();
	}
}