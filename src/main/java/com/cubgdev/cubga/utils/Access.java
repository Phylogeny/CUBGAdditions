package com.cubgdev.cubga.utils;

import static net.minecraft.client.Minecraft.getMinecraft;

import javax.annotation.Nullable;

import com.mojang.authlib.minecraft.MinecraftSessionService;

import net.minecraft.server.integrated.IntegratedServer;
import net.minecraft.server.management.PlayerProfileCache;

public class Access
{
	/**
	 * @return The minecraft integrated server
	 */
	public static IntegratedServer getIntegratedServer()
	{
		return getMinecraft().getIntegratedServer();
	}

	/**
	 * @return The minecraft player profile cache. (Used for skins)
	 */
	@Nullable
	public static PlayerProfileCache getPlayerProfileCache()
	{
		return getIntegratedServer() == null ? null : getIntegratedServer().getPlayerProfileCache();
	}

	/**
	 * @return The minecraft session service. (Used for skins)
	 */
	@Nullable
	public static MinecraftSessionService getMinecraftSessionService()
	{
		return getIntegratedServer() == null ? null : getIntegratedServer().getMinecraftSessionService();
	}
}
