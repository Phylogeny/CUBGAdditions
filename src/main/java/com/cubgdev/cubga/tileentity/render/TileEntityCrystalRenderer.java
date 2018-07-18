package com.cubgdev.cubga.tileentity.render;

import com.cubgdev.cubga.Reference;
import com.cubgdev.cubga.tileentity.TileEntityCrystal;
import com.cubgdev.cubga.tileentity.TileEntityCrystalContainer;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelEnderCrystal;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.MathHelper;

public class TileEntityCrystalRenderer extends TileEntitySpecialRenderer<TileEntityCrystal> {

    private static final ResourceLocation CRYSTAL_TEXTURES = new ResourceLocation(Reference.MOD_ID, "textures/models/crystal.png");
    private final ModelBase modelEnderCrystal = new ModelEnderCrystal(0.0F, false);

    @Override
    public void render(TileEntityCrystal te, double x, double y, double z, float partialTicks, int destroyStage, float alpha) {
        te.innerRotation += 0.5f * te.getSpeed();
        float f = te.innerRotation + partialTicks;
        float f1 = MathHelper.sin(f * 0.2F) / 2.0F + 0.45F;
        f1 = f1 * f1 + f1;

        GlStateManager.pushMatrix();
        {
            GlStateManager.translate((float) x + 0.5f, (float) y + -0.05f, (float) z + 0.5f);
            this.bindTexture(CRYSTAL_TEXTURES);

            float scale = 0.35f * te.getScale();
            GlStateManager.scale(scale, scale, scale);
            this.modelEnderCrystal.render(null, 0.0F, f * 3.0F, f1 * 0.1F, 0.0F, 0.0F, 0.0625F);
        }
        GlStateManager.popMatrix();
    }
}
