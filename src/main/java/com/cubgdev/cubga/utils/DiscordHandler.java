package com.cubgdev.cubga.utils;


import net.arikia.dev.drpc.DiscordEventHandlers;
import net.arikia.dev.drpc.DiscordRPC;
import net.arikia.dev.drpc.DiscordRichPresence;

public class DiscordHandler {

    private static DiscordHandler instance = new DiscordHandler();

    public static DiscordHandler getInstance() {
        return instance;
    }

    public void setup(){

        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            System.out.println("Shutting down DiscordHook.");
            DiscordRPC.discordShutdown();
        }));

        DiscordEventHandlers handlers = new DiscordEventHandlers.Builder().setReadyEventHandler((user) -> {}).build();
        DiscordRPC.discordInitialize("474221751119904768", handlers, true);
        DiscordRPC.discordRunCallbacks();

        DiscordRPC.discordUpdatePresence(
                new DiscordRichPresence.Builder("")
                        .setDetails("In the Main Menu")
                        .setBigImage("cubgcurselogo_png", "CUBG")
                        .build());
    }
}
