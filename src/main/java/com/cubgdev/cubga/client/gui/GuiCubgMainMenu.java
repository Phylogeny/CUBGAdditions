package com.cubgdev.cubga.client.gui;

import com.cubgdev.cubga.Reference;
import com.cubgdev.cubga.client.gui.api.GuiButtonCubg;
import net.minecraft.client.resources.I18n;
import net.minecraft.util.ResourceLocation;

import java.util.ArrayList;

public class GuiCubgMainMenu extends GuiCubg {

    /**
     * Draw Screen - Draw the GUI Screen
     * @param mouseX - Given Mouse X
     * @param mouseY - Given Mouse Y
     * @param partialTicks - Given Partial Ticks
     */
    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        super.drawScreen(mouseX,mouseY,partialTicks);

        setUiTitle("Main Menu");

    }

    /**
     * Initialize GUI - Initialize the GUI
     */
    public void initGui() {

        this.buttonList.add(new GuiButtonCubg(BUTTON_LINK_DISCORD,this.width - 83,3,80,15,I18n.format("gui.button.discord")));
        this.buttonList.add(new GuiButtonCubg(BUTTON_LINK_DISCORD,this.width - 83,22,80,15,I18n.format("gui.button.website")));

        this.buttonList.add(new GuiButtonCubg(BUTTON_PLAY,5,this.height - 35,120,30,I18n.format("gui.button.play"))
                .setScale(2)
                .setYOffset(-3)
                .setImage(new ResourceLocation(Reference.MOD_ID,"textures/gui/menu/play.png")));

    }

}
