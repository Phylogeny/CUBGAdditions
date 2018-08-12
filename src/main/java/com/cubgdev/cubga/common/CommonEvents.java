package com.cubgdev.cubga.common;

import java.util.HashMap;
import java.util.UUID;

import com.cubgdev.cubga.network.PacketHandler;
import com.cubgdev.cubga.network.message.MessageUpdateCapes;
import com.cubgdev.cubga.utils.DiscordHandler;
import com.cubgdev.cubga.utils.cape.Capes;

import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.scoreboard.ScorePlayerTeam;
import net.minecraft.scoreboard.Scoreboard;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.PlayerEvent.PlayerLoggedInEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import net.minecraftforge.fml.common.network.FMLNetworkEvent;

/**
 * Author: MrCrayfish
 */
public class CommonEvents {

	public static boolean replaceBricks = false;
	// public static boolean timerStarted = false;
	private static int ticks = 0;
	private static DiscordHandler discordHandler = DiscordHandler.getInstance();

	private static final HashMap<UUID, String> STATUS_MAP = new HashMap<>();

	@SubscribeEvent
	public void onPlayerLoggedIn(PlayerLoggedInEvent event) {
		if (event.player instanceof EntityPlayerMP) {
			PacketHandler.INSTANCE.sendTo(new MessageUpdateCapes(Capes.getCapes()), (EntityPlayerMP) event.player);
		}
	}

	@SubscribeEvent
	public void onServerTick(TickEvent.ServerTickEvent event) {
		if (event.phase == TickEvent.Phase.END && replaceBricks) {
			replaceBricks = false;
		}
	}

	@SubscribeEvent
	public void onClientTick(TickEvent.ClientTickEvent event) {
		if (event.phase.equals(TickEvent.Phase.END)) {
			if (Minecraft.getMinecraft().world != null) {
				if (ticks == 240) {
					Scoreboard scoreboard = Minecraft.getMinecraft().world.getScoreboard();
					ScorePlayerTeam scoreplayerteam = scoreboard.getPlayersTeam(Minecraft.getMinecraft().player.getName());
					if (scoreplayerteam != null) {
						if (scoreplayerteam.getDisplayName().equals("Lobby")) {
							discordHandler.getDiscordRichPresence().state = "Waiting For Players";
							// discordHandler.getDiscordRichPresence().startTimestamp=0L;
							/*
							 * if(timerStarted) { timerStarted = false; discordHandler.getDiscordRichPresence().startTimestamp = System.currentTimeMillis() / 1000L; }
							 */
							discordHandler.updateRichPresence();
						} else if (scoreplayerteam.getDisplayName().equals("inGame")) {
							discordHandler.getDiscordRichPresence().state = "Playing - Alive";
							/*
							 * if(!timerStarted) { timerStarted = true; discordHandler.getDiscordRichPresence().startTimestamp = System.currentTimeMillis() / 1000L; }
							 */
							discordHandler.updateRichPresence();
						} else if (scoreplayerteam.getDisplayName().equals("Spectator")) {
							discordHandler.getDiscordRichPresence().state = "Playing - Dead";
							discordHandler.updateRichPresence();
						} else {
							discordHandler.getDiscordRichPresence().state = "";
							discordHandler.updateRichPresence();
						}
					}
					ticks = 0;
				}
				ticks++;
			}

		}

	}

	@SubscribeEvent
	public void onServerJoin(FMLNetworkEvent.ClientConnectedToServerEvent event) {

		if (event.getManager().getRemoteAddress().toString().split("/")[0].equals("cubg.mrcrayfish.com")) {
			discordHandler.setRichPresence("", "Playing on Beaver", "cubgcurselogo_png", "CUBG", "canada_png", "Canada");
		}
		if (event.getManager().getRemoteAddress().toString().split("/")[0].equals("cubgau.mrcrayfish.com")) {
			discordHandler.setRichPresence("", "Playing on Kangaroo", "cubgcurselogo_png", "CUBG", "australia_png", "Australia");
		}
	}

	@SubscribeEvent
	public void onServerLeave(FMLNetworkEvent.ClientDisconnectionFromServerEvent event) {
		discordHandler.setRichPresence("", "In the Main Menu", "cubgcurselogo_png", "CUBG");
	}
}
