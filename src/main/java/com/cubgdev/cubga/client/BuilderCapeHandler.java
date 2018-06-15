package com.cubgdev.cubga.client;

import com.cubgdev.cubga.util.LibObfuscation;
import com.google.common.collect.ImmutableSet;
import com.mojang.authlib.minecraft.MinecraftProfileTexture;
import net.minecraft.client.entity.AbstractClientPlayer;
import net.minecraft.client.network.NetworkPlayerInfo;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.event.RenderPlayerEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.ReflectionHelper;

import java.util.Collections;
import java.util.Map;
import java.util.Set;
import java.util.WeakHashMap;

public class BuilderCapeHandler {

    private static final ImmutableSet<String> UUIDS = ImmutableSet.of(
            "f899be44-0c29-46c1-b20f-27e950bde621",
            "83420530-5478-462c-a08d-feb1375ac3e2",
            "000399b6-65d1-4979-b3c0-af309112625f",
            "8216bff4-77a0-417a-b4e4-c786e68fa141");

    private static final Set<EntityPlayer> done = Collections.newSetFromMap(new WeakHashMap());

    @SubscribeEvent
    public static void onRenderPlayer(RenderPlayerEvent.Post event) {
        EntityPlayer player = event.getEntityPlayer();
        String uuid = player.getUUID(player.getGameProfile()).toString();
        if(player instanceof AbstractClientPlayer && UUIDS.contains(uuid) && !done.contains(player)) {
            AbstractClientPlayer clplayer = (AbstractClientPlayer) player;
            if(clplayer.hasPlayerInfo()) {
                NetworkPlayerInfo info = ReflectionHelper.getPrivateValue(AbstractClientPlayer.class, clplayer, LibObfuscation.PLAYER_INFO);
                Map<MinecraftProfileTexture.Type, ResourceLocation> textures = ReflectionHelper.getPrivateValue(NetworkPlayerInfo.class, info, LibObfuscation.PLAYER_TEXTURES);
                ResourceLocation loc = new ResourceLocation("cubga", "textures/misc/builder_cape.png");
                textures.put(MinecraftProfileTexture.Type.CAPE, loc);
                textures.put(MinecraftProfileTexture.Type.ELYTRA, loc);
                done.add(player);
            }
        }
    }

}