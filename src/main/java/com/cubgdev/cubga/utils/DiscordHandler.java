package com.cubgdev.cubga.utils;


import net.arikia.dev.drpc.DiscordEventHandlers;
import net.arikia.dev.drpc.DiscordRPC;
import net.arikia.dev.drpc.DiscordRichPresence;

public class DiscordHandler {

    private static DiscordHandler instance = new DiscordHandler();

    public static DiscordHandler getInstance() {
        return instance;
    }

    private DiscordRichPresence discordRichPresence;

    public void setup(){

        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            System.out.println("Shutting down DiscordHook.");
            DiscordRPC.discordShutdown();
        }));
        DiscordEventHandlers handlers = new DiscordEventHandlers.Builder().setReadyEventHandler((user) -> {}).build();
        DiscordRPC.discordInitialize("474221751119904768", handlers, true);
        DiscordRPC.discordRunCallbacks();

        setRichPresence("", "In the Main Menu", "cubgcurselogo_png", "CUBG");
    }
    public void setRichPresence(String state, String details, String bigImage, String bigImageText){
        discordRichPresence = new DiscordRichPresence.Builder(state)
                .setDetails(details)
                .setBigImage(bigImage, bigImageText)
                .build();
        DiscordRPC.discordUpdatePresence(discordRichPresence);
    }
    public void setRichPresence(String state, String details, String bigImage, String bigImageText, String smallImage, String smallImageText){
            discordRichPresence = new DiscordRichPresence.Builder(state)
                    .setDetails(details)
                    .setBigImage(bigImage, bigImageText)
                    .setSmallImage(smallImage, smallImageText)
                    .build();
            DiscordRPC.discordUpdatePresence(discordRichPresence);
    }

    public DiscordRichPresence getDiscordRichPresence(){return discordRichPresence;}

    public void updateRichPresence(){DiscordRPC.discordUpdatePresence(discordRichPresence);}

}
