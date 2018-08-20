package com.cubgdev.cubga.client.particle;

import com.cubgdev.cubga.client.gui.utilities.CUBGRenderHelper;
import net.minecraft.client.Minecraft;
import net.minecraft.client.particle.Particle;
import net.minecraft.client.renderer.ActiveRenderInfo;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.client.event.RenderWorldLastEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.ArrayList;
import java.util.List;

/**
 * Author: MrCrayfish
 */
public class ParticleRenderer {

    public static ParticleRenderer instance;

    public static ParticleRenderer getInstance() {
        if(instance == null) {
            instance = new ParticleRenderer();
        }
        return instance;
    }

    private List<Particle> particles = new ArrayList<>();

    public void addParticle(Particle p) {
        particles.add(p);
    }

    public void updateParticle() {
        for(int i = 0; i < particles.size(); i++) {
            Particle particle = particles.get(i);
            particle.onUpdate();
            if(!particle.isAlive()) {
                particles.remove(i);
            }
        }
    }

    public void renderParticle(float partialTicks) {
        if (Minecraft.getMinecraft().gameSettings.particleSetting == 2) {
            return;
        }

        float f = ActiveRenderInfo.getRotationX();
        float f1 = ActiveRenderInfo.getRotationZ();
        float f2 = ActiveRenderInfo.getRotationYZ();
        float f3 = ActiveRenderInfo.getRotationXY();
        float f4 = ActiveRenderInfo.getRotationXZ();

        EntityPlayer player = Minecraft.getMinecraft().player;
        Particle.interpPosX = player.lastTickPosX + (player.posX - player.lastTickPosX) * partialTicks;
        Particle.interpPosY = player.lastTickPosY + (player.posY - player.lastTickPosY) * partialTicks;
        Particle.interpPosZ = player.lastTickPosZ + (player.posZ - player.lastTickPosZ) * partialTicks;
        Particle.cameraViewDir = player.getLook(partialTicks);

        GlStateManager.enableAlpha();
        GlStateManager.enableBlend();
        GlStateManager.blendFunc(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE);
        GlStateManager.disableCull();

        Tessellator tessellator = Tessellator.getInstance();
        BufferBuilder buffer = tessellator.getBuffer();
        for(int i = 0; i < particles.size(); i++) {
            particles.get(i).renderParticle(buffer, Minecraft.getMinecraft().player, partialTicks, f, f4, f1, f2, f3);
        }

        GlStateManager.enableCull();
        GlStateManager.blendFunc(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA);
        GlStateManager.disableBlend();
    }

    @SubscribeEvent
    @SideOnly(Side.CLIENT)
    public void onRenderAfterWorld(RenderWorldLastEvent event) {
        getInstance().renderParticle(event.getPartialTicks());
    }

    @SubscribeEvent
    @SideOnly(Side.CLIENT)
    public void onClientTick(TickEvent.ClientTickEvent event) {
        if(event.phase == TickEvent.Phase.START) {
            CUBGRenderHelper.swing+=1;
            getInstance().updateParticle();
        }
    }
}
