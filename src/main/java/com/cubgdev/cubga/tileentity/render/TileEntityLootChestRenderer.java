package com.cubgdev.cubga.tileentity.render;

import com.cubgdev.cubga.Reference;
import com.cubgdev.cubga.tileentity.TileEntityLootChest;
import net.minecraft.block.Block;
import net.minecraft.block.BlockChest;
import net.minecraft.client.model.ModelChest;
import net.minecraft.client.model.ModelLargeChest;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.util.ResourceLocation;

/**
 * Author: MrCrayfish
 */
public class TileEntityLootChestRenderer extends TileEntitySpecialRenderer<TileEntityLootChest>
{
    private static final ResourceLocation TEXTURE_NORMAL = new ResourceLocation(Reference.MOD_ID, "textures/entity/loot_chest/normal.png");
    private static final ResourceLocation TEXTURE_DOUBLE = new ResourceLocation(Reference.MOD_ID, "textures/entity/loot_chest/normal_double.png");

    private final ModelChest SMALL_CHEST = new ModelChest();
    private final ModelChest LARGE_CHEST = new ModelLargeChest();

    @Override
    public void render(TileEntityLootChest te, double x, double y, double z, float partialTicks, int destroyStage, float alpha)
    {
        GlStateManager.enableDepth();
        GlStateManager.depthFunc(515);
        GlStateManager.depthMask(true);

        int i;

        if (te.hasWorld())
        {
            Block block = te.getBlockType();
            i = te.getBlockMetadata();

            if (block instanceof BlockChest && i == 0)
            {
                ((BlockChest)block).checkForSurroundingChests(te.getWorld(), te.getPos(), te.getWorld().getBlockState(te.getPos()));
                i = te.getBlockMetadata();
            }

            te.checkForAdjacentChests();
        }
        else
        {
            i = 0;
        }

        if (te.adjacentChestZNeg == null && te.adjacentChestXNeg == null)
        {
            ModelChest modelChest;

            if (te.adjacentChestXPos == null && te.adjacentChestZPos == null)
            {
                modelChest = this.SMALL_CHEST;

                if (destroyStage >= 0)
                {
                    this.bindTexture(DESTROY_STAGES[destroyStage]);
                    GlStateManager.matrixMode(5890);
                    GlStateManager.pushMatrix();
                    GlStateManager.scale(4.0F, 4.0F, 1.0F);
                    GlStateManager.translate(0.0625F, 0.0625F, 0.0625F);
                    GlStateManager.matrixMode(5888);
                }
                else
                {
                    ResourceLocation chestTexture = te.getLootChest().getChestTexture();
                    if(chestTexture != null)
                    {
                        this.bindTexture(chestTexture);
                    }
                    else
                    {
                        this.bindTexture(TEXTURE_NORMAL);
                    }
                }
            }
            else
            {
                modelChest = this.LARGE_CHEST;

                if (destroyStage >= 0)
                {
                    this.bindTexture(DESTROY_STAGES[destroyStage]);
                    GlStateManager.matrixMode(5890);
                    GlStateManager.pushMatrix();
                    GlStateManager.scale(8.0F, 4.0F, 1.0F);
                    GlStateManager.translate(0.0625F, 0.0625F, 0.0625F);
                    GlStateManager.matrixMode(5888);
                }
                else
                {
                    ResourceLocation chestTexture = te.getLootChest().getChestTexture();
                    if(chestTexture != null)
                    {
                        this.bindTexture(chestTexture);
                    }
                    else
                    {
                        this.bindTexture(TEXTURE_DOUBLE);
                    }
                }
            }

            GlStateManager.pushMatrix();
            GlStateManager.enableRescaleNormal();

            if (destroyStage < 0)
            {
                int[] color = te.getLootChest().getRGBColor();
                GlStateManager.color(color[0] / 255F, color[1] / 255F, color[2] / 255F, alpha);
            }

            GlStateManager.translate((float)x, (float)y + 1.0F, (float)z + 1.0F);
            GlStateManager.scale(1.0F, -1.0F, -1.0F);
            GlStateManager.translate(0.5F, 0.5F, 0.5F);
            int j = 0;

            if (i == 2)
            {
                j = 180;
            }

            if (i == 3)
            {
                j = 0;
            }

            if (i == 4)
            {
                j = 90;
            }

            if (i == 5)
            {
                j = -90;
            }

            if (i == 2 && te.adjacentChestXPos != null)
            {
                GlStateManager.translate(1.0F, 0.0F, 0.0F);
            }

            if (i == 5 && te.adjacentChestZPos != null)
            {
                GlStateManager.translate(0.0F, 0.0F, -1.0F);
            }

            GlStateManager.rotate((float)j, 0.0F, 1.0F, 0.0F);
            GlStateManager.translate(-0.5F, -0.5F, -0.5F);
            float f = te.prevLidAngle + (te.lidAngle - te.prevLidAngle) * partialTicks;

            if (te.adjacentChestZNeg != null)
            {
                float f1 = te.adjacentChestZNeg.prevLidAngle + (te.adjacentChestZNeg.lidAngle - te.adjacentChestZNeg.prevLidAngle) * partialTicks;

                if (f1 > f)
                {
                    f = f1;
                }
            }

            if (te.adjacentChestXNeg != null)
            {
                float f2 = te.adjacentChestXNeg.prevLidAngle + (te.adjacentChestXNeg.lidAngle - te.adjacentChestXNeg.prevLidAngle) * partialTicks;

                if (f2 > f)
                {
                    f = f2;
                }
            }

            f = 1.0F - f;
            f = 1.0F - f * f * f;
            modelChest.chestLid.rotateAngleX = -(f * ((float)Math.PI / 2F));
            modelChest.chestKnob.rotateAngleX = modelChest.chestLid.rotateAngleX;
            modelChest.chestLid.render(0.0625F);
            modelChest.chestBelow.render(0.0625F);

            GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
            modelChest.chestKnob.render(0.0625F);

            GlStateManager.disableRescaleNormal();
            GlStateManager.popMatrix();
            GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);

            if (destroyStage >= 0)
            {
                GlStateManager.matrixMode(5890);
                GlStateManager.popMatrix();
                GlStateManager.matrixMode(5888);
            }
        }
    }
}
