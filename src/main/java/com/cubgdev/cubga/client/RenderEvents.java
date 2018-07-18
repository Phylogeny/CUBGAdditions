package com.cubgdev.cubga.client;

import com.cubgdev.cubga.tileentity.TileEntityCrystalContainer;
import com.cubgdev.cubga.utils.TextureUtils;

import net.minecraft.client.Minecraft;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelEnderCrystal;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.entity.RenderDragon;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;
import net.minecraftforge.client.event.RenderWorldLastEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class RenderEvents
{

	private static final ResourceLocation ENDER_CRYSTAL_TEXTURES = new ResourceLocation("textures/entity/endercrystal/endercrystal.png");
	private static final ModelBase ENDER_CRYSTAL_MODEL = new ModelEnderCrystal(0.0F, false);

	@SubscribeEvent
	public void onRenderWorldEvent(RenderWorldLastEvent event)
	{
		EntityPlayer player = Minecraft.getMinecraft().player;
		World world = player.world;
		float partialTicks = event.getPartialTicks();

		double posX = player.lastTickPosX + (player.posX - player.lastTickPosX) * partialTicks;
		double posY = player.lastTickPosY + (player.posY - player.lastTickPosY) * partialTicks;
		double posZ = player.lastTickPosZ + (player.posZ - player.lastTickPosZ) * partialTicks;

		GlStateManager.translate(-posX, -posY, -posZ);

		this.renderCrystalContainers(partialTicks);
	}

	private void renderCrystalContainers(float partialTicks)
	{
		for (String pos : TileEntityCrystalContainer.CRYSTALS.keySet())
		{
			TileEntityCrystalContainer te = TileEntityCrystalContainer.CRYSTALS.get(pos);
			BlockPos fromPos = te.getPos();
			if (Minecraft.getMinecraft().player.getPosition().getDistance(fromPos.getX(), fromPos.getY(), fromPos.getZ()) < 128D)
			{
				te.updateRotation();
				float innerRotation = te.getInnerRotation();
				float innerRotationSin = MathHelper.sin(innerRotation * 0.2F) / 2.0F + 0.45F;
				innerRotationSin = innerRotationSin * innerRotationSin + innerRotationSin;

				float scale = 0.3f * te.getScale();
				
				GlStateManager.pushMatrix();
				{
					TextureUtils.bindTexture(ENDER_CRYSTAL_TEXTURES);

					GlStateManager.translate(fromPos.getX(), fromPos.getY(), fromPos.getZ());
					GlStateManager.translate(0.5 * (1 / te.getScale()), 0.25, 0.5 * (1 / te.getScale()));
					GlStateManager.scale(scale, scale, scale);
					this.ENDER_CRYSTAL_MODEL.render(null, 0.0F, innerRotation * 3.0F, innerRotationSin * 0.1F, 0.0F, 0.0F, 0.0F);
				}
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
							float f3 = (float) toPos.getY()-0.25f;
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

		for (int i = 0; i < TileEntityCrystalContainer.REMOVED.size(); i++)
		{
			TileEntityCrystalContainer.CRYSTALS.remove(TileEntityCrystalContainer.REMOVED.get(i).toString());
		}

		TileEntityCrystalContainer.REMOVED.clear();
	}
}