package com.cubgdev.cubga.common;

import com.cubgdev.cubga.utils.DiscordHandler;
import net.arikia.dev.drpc.DiscordRPC;
import net.arikia.dev.drpc.DiscordRichPresence;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.scoreboard.ScorePlayerTeam;
import net.minecraft.scoreboard.Scoreboard;
import net.minecraft.server.MinecraftServer;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import net.minecraftforge.fml.common.network.FMLNetworkEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import org.lwjgl.Sys;

/**
 * Author: MrCrayfish & MastefChief
 */
public class CommonEvents {

    public static boolean replaceBricks = false;

    DiscordHandler discordHandler = DiscordHandler.getInstance();

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
                Scoreboard scoreboard = Minecraft.getMinecraft().world.getScoreboard();
                ScorePlayerTeam scoreplayerteam = scoreboard.getPlayersTeam(Minecraft.getMinecraft().player.getName());
                if (scoreplayerteam != null) {
                    if (scoreplayerteam.getDisplayName().equals("Lobby")) {
                    discordHandler.getDiscordRichPresence().state="In Lobby";
                    discordHandler.updateRichPresence();
                    }
                }
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
