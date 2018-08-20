package com.cubgdev.cubga.tileentity.render;

import com.cubgdev.cubga.tileentity.TileEntityCrystalContainer;
import com.cubgdev.cubga.utils.TextureUtils;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelEnderCrystal;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.entity.RenderDragon;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;

import java.util.Set;

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

		te.updateRotation();
		double innerRotation = te.getInnerRotation();
		double innerRotationSin = Math.sin(innerRotation * 0.2F) / 2.0F + 0.45F;
		innerRotationSin = innerRotationSin * innerRotationSin + innerRotationSin;

		double scale = 0.3f * te.getScale();
		double scaleFactor = te.getScale() * (1 / te.getScale());

		GlStateManager.pushMatrix();
		GlStateManager.disableLighting();
		{
			GlStateManager.translate(x, y, z);
			TextureUtils.bindTexture(ENDER_CRYSTAL_TEXTURES);
			GlStateManager.translate(0.5 * scaleFactor, 0.25 * te.getScale(), 0.5 * scaleFactor);
			GlStateManager.scale(scale, scale, scale);
			ModelBase model = te.renderBase() ? ENDER_CRYSTAL_BASE_MODEL : ENDER_CRYSTAL_MODEL;
			model.render(null, 0.0F, (float) (innerRotation * 3.0), (float) (innerRotationSin * 0.1), 0.0F, 0.0F, 0.0625F);
		}
		GlStateManager.enableLighting();
		GlStateManager.popMatrix();

		Set<BlockPos> beamPositions = te.getBeamPositions();
		if (beamPositions != null)
		{
			for(BlockPos beamPos : beamPositions)
			{
				GlStateManager.pushMatrix();
				TextureUtils.bindTexture(RenderDragon.ENDERCRYSTAL_BEAM_TEXTURES);

				float destX = (float) beamPos.getX() + 0.5F;
				float destY = (float) beamPos.getY();
				float destZ = (float) beamPos.getZ() + 0.5F;
				double deltaX = (double) destX - te.getPos().getX();
				double deltaY = (double) destY - te.getPos().getY();
				double deltaZ = (double) destZ - te.getPos().getZ();

				renderCrystalBeams(x + deltaX, y + deltaY, z + deltaZ, partialTicks, destX, destY, destZ, (int) te.getInnerRotation(), te.getPos().getX() + 0.5, te.getPos().getY() - 0.5, te.getPos().getZ() + 0.5);

				GlStateManager.popMatrix();
			}
		}
		GlStateManager.popMatrix();
	}

	public static void renderCrystalBeams(double deltaX, double deltaY, double deltaZ, float partialTicks, double destX, double destY, double destZ, int rotation, double sourceX, double sourceY, double sourceZ)
	{
		float f = (float)(sourceX - destX);
		float f1 = (float)(sourceY - destY + 0.5);
		float f2 = (float)(sourceZ - destZ);
		float distanceHorizontal = MathHelper.sqrt(f * f + f2 * f2);
		float distance = MathHelper.sqrt(f * f + f1 * f1 + f2 * f2);

		GlStateManager.pushMatrix();
		GlStateManager.translate((float)deltaX, (float)deltaY + 0.5F, (float)deltaZ);
		GlStateManager.rotate((float)(-Math.atan2((double)f2, (double)f)) * (180F / (float)Math.PI) - 90.0F, 0.0F, 1.0F, 0.0F);
		GlStateManager.rotate((float)(-Math.atan2((double)distanceHorizontal, (double)f1)) * (180F / (float)Math.PI) - 90.0F, 1.0F, 0.0F, 0.0F);

		RenderHelper.disableStandardItemLighting();
		GlStateManager.disableCull();
		GlStateManager.shadeModel(7425);
		float f5 = -(float) rotation * 0.0025F;
		float f6 = distance / 16.0F - (float) rotation * 0.0025F;

		Tessellator tessellator = Tessellator.getInstance();
		BufferBuilder buffer = tessellator.getBuffer();
		buffer.begin(5, DefaultVertexFormats.POSITION_TEX_COLOR);
		int vertexCount = 16;
		for (int j = 0; j <= vertexCount; ++j)
		{
			float f7 = MathHelper.sin((float)(j % vertexCount) * ((float)Math.PI * 2F) / (float) vertexCount) * 0.25F;
			float f8 = MathHelper.cos((float)(j % vertexCount) * ((float)Math.PI * 2F) / (float) vertexCount) * 0.25F;
			float f9 = (float)(j % 8) / (float) vertexCount;
			buffer.pos((double)(f7 * 0.2F), (double)(f8 * 0.2F), 0.0D).tex((double)f9, (double)f5).color(0, 0, 0, 255).endVertex();
			buffer.pos((double)f7, (double)f8, (double)distance).tex((double)f9, (double)f6).color(255, 255, 255, 255).endVertex();
		}

		tessellator.draw();
		GlStateManager.enableCull();
		GlStateManager.shadeModel(7424);
		RenderHelper.enableStandardItemLighting();
		GlStateManager.popMatrix();
	}
}
