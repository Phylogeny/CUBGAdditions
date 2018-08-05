package com.cubgdev.cubga.common;

import net.arikia.dev.drpc.DiscordRPC;
import net.arikia.dev.drpc.DiscordRichPresence;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import net.minecraftforge.fml.common.network.FMLNetworkEvent;

/**
 * Author: MrCrayfish
 */
public class CommonEvents {

    public static boolean replaceBricks = false;

    @SubscribeEvent
    public void onServerTick(TickEvent.ServerTickEvent event) {
        if(event.phase == TickEvent.Phase.END && replaceBricks) {
            replaceBricks = false;
        }
    }

    @SubscribeEvent
    public void onServerJoin(FMLNetworkEvent.ClientConnectedToServerEvent event){

        if(event.getManager().getRemoteAddress().toString().split("/")[0].equals("cubg.mrcrayfish.com")){
            DiscordRichPresence rich = new DiscordRichPresence.Builder("")
                            .setDetails("Playing on Beaver.")
                            .setBigImage("cubgcurselogo_png", "CUBG")
                            .setSmallImage("canada_png", "Canada")
                            .build();
            DiscordRPC.discordUpdatePresence(rich);
        }
        if(event.getManager().getRemoteAddress().toString().split("/")[0].equals("cubgau.mrcrayfish.com")){

                    DiscordRichPresence rich = new DiscordRichPresence.Builder("")
                            .setDetails("Playing on Kangaroo.")
                            .setBigImage("cubgcurselogo_png", "CUBG")
                            .setSmallImage("australia_png", "Australia")
                            .build();
            DiscordRPC.discordUpdatePresence(rich);
        }
    }

    @SubscribeEvent
    public void onServerLeave(FMLNetworkEvent.ClientDisconnectionFromServerEvent event){
        DiscordRichPresence rich = new DiscordRichPresence.Builder("")
                        .setDetails("In the Main Menu")
                        .setBigImage("cubgcurselogo_png", "CUBG")
                        .build();
        DiscordRPC.discordUpdatePresence(rich);
    }
}
