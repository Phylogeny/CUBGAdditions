package com.cubgdev.cubga.client;

import com.cubgdev.cubga.client.gui.GuiCubg;
import com.cubgdev.cubga.client.gui.GuiCubgMainMenu;
import net.minecraft.client.gui.GuiMainMenu;
import net.minecraftforge.client.event.GuiOpenEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class GuiEvents {

    /**
     * On Gui Launch - Called when Minecraft launches/refreshes a new Gui
     * @param event - Given Event (GuiOpenEvent)
     */
    @SubscribeEvent
    public void onGuiLaunch(GuiOpenEvent event){

        /* If the Gui is instance of Vanilla Menu, replace */
        if(event.getGui() instanceof GuiMainMenu){
            event.setGui(new GuiCubgMainMenu());
        }

    }

}
