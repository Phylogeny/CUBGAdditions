package com.cubgdev.cubga.tileentity.render;

import com.cubgdev.cubga.tileentity.TileEntityCrystalContainer;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelEnderCrystal;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.MathHelper;

/**
 * Author: CoffeeCatRailway
 */
@Deprecated
public class TileEntityCrystalContainerRenderer extends TileEntitySpecialRenderer<TileEntityCrystalContainer>
{
	private static final ResourceLocation ENDER_CRYSTAL_TEXTURES = new ResourceLocation("textures/entity/endercrystal/endercrystal.png");
	private static final ModelBase MODEL = new ModelEnderCrystal(0.0F, false);

	@Override
	public void render(TileEntityCrystalContainer te, double x, double y, double z, float partialTicks, int destroyStage, float alpha)
	{
		te.updateRotation();
		float scale = 0.35f * te.getScale();
		float innerRotation = te.getInnerRotation();
		float innerRotationSin = MathHelper.sin(innerRotation * 0.2F) / 2.0F + 0.45F;
		innerRotationSin = innerRotationSin * innerRotationSin + innerRotationSin;

		GlStateManager.pushMatrix();
		{
			GlStateManager.translate((float) x + 0.5f, (float) y + 0.25f, (float) z + 0.5f);
			this.bindTexture(ENDER_CRYSTAL_TEXTURES);

			GlStateManager.scale(scale, scale, scale);
			this.MODEL.render(null, 0.0F, innerRotation * 3.0F, innerRotationSin * 0.1F, 0.0F, 0.0F, 0.0625F);
		}
		GlStateManager.popMatrix();
	}
}
