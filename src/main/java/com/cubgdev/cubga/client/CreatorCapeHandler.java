package com.cubgdev.cubga.client;

import com.google.common.collect.ImmutableSet;
import com.cubgdev.cubga.util.LibObfuscation;
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

public class CreatorCapeHandler {

    private static final ImmutableSet<String> UUIDS = ImmutableSet.of(
            "68c08594-e7cd-43fb-bdf9-240147ee26cf",
            "b61e1172-e4f0-450a-9f98-58ec91bc41dc");

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
                ResourceLocation loc = new ResourceLocation("cubga", "textures/misc/creator_cape.png");
                textures.put(MinecraftProfileTexture.Type.CAPE, loc);
                textures.put(MinecraftProfileTexture.Type.ELYTRA, loc);
                done.add(player);
            }
        }
    }

}