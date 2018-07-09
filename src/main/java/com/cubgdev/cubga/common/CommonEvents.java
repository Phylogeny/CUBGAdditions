package com.cubgdev.cubga.common;

import net.minecraftforge.fml.common.event.FMLServerStartingEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;

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
}
