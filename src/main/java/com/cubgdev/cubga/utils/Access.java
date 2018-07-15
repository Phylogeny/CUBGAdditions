package com.cubgdev.cubga.utils;

import com.mojang.authlib.minecraft.MinecraftSessionService;
import net.minecraft.server.integrated.IntegratedServer;
import net.minecraft.server.management.PlayerProfileCache;

import static net.minecraft.client.Minecraft.getMinecraft;

public class Access
{
    /**
     * @return The minecraft integrated server
     */
    public static IntegratedServer getIntegratedServer() {
        return getMinecraft().getIntegratedServer();
    }

    /**
     * @return The minecraft player profile cache. (Used for skins)
     */
    public static PlayerProfileCache getPlayerProfileCache() {
        return getIntegratedServer().getPlayerProfileCache();
    }

    /**
     * @return The minecraft session service. (Used for skins)
     */
    public static MinecraftSessionService getMinecraftSessionService() {
        return getIntegratedServer().getMinecraftSessionService();
    }
}
