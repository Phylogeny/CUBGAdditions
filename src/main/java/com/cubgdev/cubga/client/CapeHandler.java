package com.cubgdev.cubga.client;

import com.cubgdev.cubga.proxy.ClientProxy;
import com.cubgdev.cubga.util.LibObfuscation;
import com.mojang.authlib.minecraft.MinecraftProfileTexture;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.AbstractClientPlayer;
import net.minecraft.client.network.NetworkPlayerInfo;
import net.minecraft.client.renderer.texture.DynamicTexture;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.event.RenderPlayerEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.ReflectionHelper;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.net.URL;
import java.util.*;

public class CapeHandler {

    private static final Set<UUID> COMPLETED_PLAYER = Collections.newSetFromMap(new WeakHashMap<>());

    @SubscribeEvent
    public static void onRenderPlayer(RenderPlayerEvent.Post event) {
        EntityPlayer player = event.getEntityPlayer();
        UUID uuid = player.getUniqueID();
        if(ClientProxy.getCapeMap().containsKey(uuid) && player instanceof AbstractClientPlayer && !COMPLETED_PLAYER.contains(uuid)) {
            AbstractClientPlayer abstractClientPlayer = (AbstractClientPlayer) player;
            if(abstractClientPlayer.hasPlayerInfo()) {
                NetworkPlayerInfo info = ReflectionHelper.getPrivateValue(AbstractClientPlayer.class, abstractClientPlayer, LibObfuscation.PLAYER_INFO);
                Map<MinecraftProfileTexture.Type, ResourceLocation> textures = ReflectionHelper.getPrivateValue(NetworkPlayerInfo.class, info, LibObfuscation.PLAYER_TEXTURES);
                try {
                    URL url = new URL(ClientProxy.getCapeMap().get(uuid).replace("\"", ""));
                    BufferedImage image = ImageIO.read(url);
                    DynamicTexture dynamicTexture = new DynamicTexture(image);
                    dynamicTexture.loadTexture(Minecraft.getMinecraft().getResourceManager());
                    textures.put(MinecraftProfileTexture.Type.CAPE, Minecraft.getMinecraft().getTextureManager().getDynamicTextureLocation(uuid.toString(), dynamicTexture));
                    textures.put(MinecraftProfileTexture.Type.ELYTRA, Minecraft.getMinecraft().getTextureManager().getDynamicTextureLocation(uuid.toString(), dynamicTexture));
                    COMPLETED_PLAYER.add(player.getUniqueID());
                } catch(Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
