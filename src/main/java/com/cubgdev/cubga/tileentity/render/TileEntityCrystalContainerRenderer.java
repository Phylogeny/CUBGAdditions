package com.cubgdev.cubga.tileentity.render;

import com.cubgdev.cubga.tileentity.TileEntityCrystalContainer;
import com.cubgdev.cubga.utils.TextureUtils;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelEnderCrystal;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.entity.RenderDragon;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;

/**
 * Author: CoffeeCatRailway
 */
public class TileEntityCrystalContainerRenderer extends TileEntitySpecialRenderer<TileEntityCrystalContainer>
{
	private static final ResourceLocation ENDER_CRYSTAL_TEXTURES = new ResourceLocation("textures/entity/endercrystal/endercrystal.png");
	private static final ModelBase ENDER_CRYSTAL_MODEL = new ModelEnderCrystal(0.0f, false);
	private static final ModelBase ENDER_CRYSTAL_BASE_MODEL = new ModelEnderCrystal(0.0f, true);

	@Override
	public void render(TileEntityCrystalContainer te, double x, double y, double z, float partialTicks, int destroyStage, float alpha)
	{
		GlStateManager.pushMatrix();
		GlStateManager.translate(x, y, z);

		te.updateRotation();
		double innerRotation = te.getInnerRotation();
		double innerRotationSin = Math.sin(innerRotation * 0.2F) / 2.0F + 0.45F;
		innerRotationSin = innerRotationSin * innerRotationSin + innerRotationSin;

		double scale = 0.3f * te.getScale();
		double scaleFactor = te.getScale() * (1 / te.getScale());

		GlStateManager.pushMatrix();
		GlStateManager.disableLighting();
		{
			TextureUtils.bindTexture(ENDER_CRYSTAL_TEXTURES);
			GlStateManager.translate(0.5 * scaleFactor, 0.25 * te.getScale(), 0.5 * scaleFactor);
			GlStateManager.scale(scale, scale, scale);
			ModelBase model = te.renderBase() ? ENDER_CRYSTAL_BASE_MODEL : ENDER_CRYSTAL_MODEL;
			model.render(null, 0.0F, (float) (innerRotation * 3.0), (float) (innerRotationSin * 0.1), 0.0F, 0.0F, 0.0625F);
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
					GlStateManager.scale(scale, scale, scale);
					TextureUtils.bindTexture(RenderDragon.ENDERCRYSTAL_BEAM_TEXTURES);
					// float f2 = (float) (toPos.getX() * (1 / scale));
					// float f3 = (float) (toPos.getY() * (1 / scale));
					// float f4 = (float) (toPos.getZ() * (1 / scale));
					// double d0 = (double) f2 - fromPos.getX();
					// double d1 = (double) f3 - fromPos.getY();
					// double d2 = (double) f4 - fromPos.getZ();
					// RenderDragon.renderCrystalBeams((fromPos.getX() + d0)*(1/scale), (fromPos.getY() + 0.25D + d1)*(1/scale), (fromPos.getZ() + d2)*(1/scale), partialTicks, (double) f2*(1/scale), (double) f3*(1/scale), (double) f4*(1/scale), (int) innerRotation, fromPos.getX(), fromPos.getY(), fromPos.getZ());
					// RenderDragon.renderCrystalBeams(f2, f3, f4, partialTicks, f2, f3, f4, (int)te.getInnerRotation(), f2, f3, f4);

					// RenderDragon.renderCrystalBeams(toPos.getX() - fromPos.getX(), toPos.getY() - fromPos.getY() - 1, toPos.getZ() - fromPos.getZ(), partialTicks, 0, 0 - 1 - innerRotationSin * 0.25, 0, (int) te.getInnerRotation(), fromPos.getX() - toPos.getX(), fromPos.getY() - toPos.getY() + 1, fromPos.getZ() - toPos.getZ());
					
					double blockX = toPos.getX() - te.getPos().getX() + 0.5;
					double blockY = toPos.getY() - te.getPos().getY() + 0.5;
					double blockZ = toPos.getZ() - te.getPos().getZ() + 0.5;

					RenderDragon.renderCrystalBeams(blockX, blockY, blockZ, partialTicks, 0, 0 - 0.5 - innerRotationSin * 0.25, -0.5, (int) te.getInnerRotation(), -blockX, -blockY, -blockZ);

					GlStateManager.popMatrix();
				}
			}
		}
		GlStateManager.popMatrix();
	}
}
