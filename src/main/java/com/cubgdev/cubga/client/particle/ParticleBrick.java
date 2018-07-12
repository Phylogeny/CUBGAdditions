package com.cubgdev.cubga.client.particle;

import com.cubgdev.cubga.init.ModItems;
import net.minecraft.client.Minecraft;
import net.minecraft.client.particle.Particle;
import net.minecraft.client.renderer.*;
import net.minecraft.client.renderer.block.model.ItemCameraTransforms;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.entity.Entity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.common.MinecraftForge;
import org.lwjgl.opengl.GL11;

import javax.annotation.Nullable;

/**
 * Author: MrCrayfish
 */
public class ParticleBrick extends Particle {

    private BlockPos source;
    private float rotation;

    public ParticleBrick(World worldIn, double xCoordIn, double yCoordIn, double zCoordIn, double xSpeedIn, double ySpeedIn, double zSpeedIn) {
        super(worldIn, xCoordIn, yCoordIn, zCoordIn, xSpeedIn, ySpeedIn, zSpeedIn);
        this.source = new BlockPos(xCoordIn, yCoordIn, zCoordIn);
        this.particleGravity = 1F;
        this.particleMaxAge = 30 + rand.nextInt(10);
        this.rotation = (float) (rand.nextGaussian() * 360F);
    }

    @Override
    public int getFXLayer() {
        return 3;
    }

    @Override
    public void renderParticle(BufferBuilder buffer, Entity entityIn, float partialTicks, float rotationX, float rotationZ, float rotationYZ, float rotationXY, float rotationXZ) {
        float posX = (float) (this.prevPosX + (this.posX - this.prevPosX) * (double) partialTicks - interpPosX);
        float posY = (float) (this.prevPosY + (this.posY - this.prevPosY) * (double) partialTicks - interpPosY);
        float posZ = (float) (this.prevPosZ + (this.posZ - this.prevPosZ) * (double) partialTicks - interpPosZ);

        GlStateManager.pushMatrix();
        {
            GlStateManager.translate(posX, posY + 1.5 * 0.0625, posZ);
            GlStateManager.rotate(rotation, 0, 1, 0);
            RenderHelper.enableStandardItemLighting();
            Minecraft.getMinecraft().getRenderItem().renderItem(new ItemStack(ModItems.BRICK), ItemCameraTransforms.TransformType.NONE);
        }
        GlStateManager.popMatrix();
    }

    @Override
    public int getBrightnessForRender(float partialTicks) {
        int i = super.getBrightnessForRender(partialTicks);
        int j = 0;
        if (this.world.isBlockLoaded(this.source)) {
            j = this.world.getCombinedLight(this.source, 0);
        }
        return j;
    }
}
