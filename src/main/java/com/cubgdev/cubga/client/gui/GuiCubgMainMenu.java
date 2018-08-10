package com.cubgdev.cubga.client.gui;

import com.cubgdev.cubga.Reference;
import com.cubgdev.cubga.client.gui.api.GuiButtonCubg;
import com.cubgdev.cubga.client.gui.utilities.ScissorState;
import net.minecraft.client.gui.*;
import com.cubgdev.cubga.client.gui.utilities.CUBGRenderHelper;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiOptions;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.resources.I18n;
import net.minecraft.launchwrapper.Launch;
import net.minecraft.util.ResourceLocation;

import java.io.IOException;

import static net.minecraft.client.renderer.GlStateManager.pushMatrix;

public class GuiCubgMainMenu extends GuiCubg {

    public static String designatedServerIP = Reference.SERVERIP_BEAVER;

    public GuiCubgMainMenu(){
        super();
        setUiTitle(I18n.format("gui.title.mainmenu"));
    }

    /**
     * Draw Screen - Draw the GUI Screen
     * @param mouseX - Given Mouse X
     * @param mouseY - Given Mouse Y
     * @param partialTicks - Given Partial Ticks
     */
    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        super.drawScreen(mouseX,mouseY,partialTicks);

        CUBGRenderHelper.renderRectWithOutline(width / 2 - 75,3,150,34,0x22FFFFFF,0x22FFFFFF,1);

        CUBGRenderHelper.renderCenteredTextWithShadow(I18n.format("gui.subtitle.status"),width / 2,5,0xFFFFFF);

        GlStateManager.pushMatrix();
        ScissorState.scissor(30, 0, width, height, true);
        float val = (float) (Math.sin(CUBGRenderHelper.swing / 55) * 70);
        CUBGRenderHelper.renderPlayer(width / 2,height / 2 + 155,150,val);
        ScissorState.endScissor();
        GlStateManager.popMatrix();
    }

    @Override
    protected void actionPerformed(GuiButton button) throws IOException
    {
        super.actionPerformed(button);

        switch(button.id){
            case BUTTON_LINK_DISCORD:
                this.openURL(Reference.LINK_DISCORD);
                break;
            case BUTTON_LINK_WEBSITE:
                this.openURL(Reference.LINK_WEBSITE);
                break;
            case BUTTON_BEAVER:
                designatedServerIP = Reference.SERVERIP_BEAVER;
                mc.displayGuiScreen(new GuiCubgMainMenu());
                break;
            case BUTTON_KANGAROO:
                designatedServerIP = Reference.SERVERIP_KANGAROO;
                mc.displayGuiScreen(new GuiCubgMainMenu());
                break;
            case BUTTON_NEWS:

                break;
            case BUTTON_SETTINGS:
                mc.displayGuiScreen(new GuiOptions(this,mc.gameSettings));
                break;
            case BUTTON_QUIT:
                mc.shutdown();
                break;
            case BUTTON_PLAY:
                this.joinServer(designatedServerIP,false);
                break;
            case BUTTON_SINGLEPLAYER:
                mc.displayGuiScreen(new GuiWorldSelection(this));
                break;
            case BUTTON_MULTIPLAYER:
                mc.displayGuiScreen(new GuiMultiplayer(this));
                break;
        }
    }

    /**
     * Initialize GUI - Initialize the GUI
     */
    public void initGui() {

        this.buttonList.add(new GuiButtonCubg(BUTTON_LINK_DISCORD,this.width - 83,3,80,15,I18n.format("gui.button.discord")));
        this.buttonList.add(new GuiButtonCubg(BUTTON_LINK_WEBSITE,this.width - 83,22,80,15,I18n.format("gui.button.website"))
                .setDisabled(true));

        this.buttonList.add(new GuiButtonCubg(BUTTON_NEWS,this.width - 83,height - 18,80,15,I18n.format("gui.button.news"))
                .setDisabled(true));
        this.buttonList.add(new GuiButtonCubg(BUTTON_SETTINGS,this.width - 83,height - 37,80,15,I18n.format("gui.button.settings")));
        this.buttonList.add(new GuiButtonCubg(BUTTON_QUIT,this.width - 83,height - 58,80,15,I18n.format("gui.button.quit")));

        this.buttonList.add(new GuiButtonCubg(BUTTON_PLAY,5,this.height - 35,120,30,I18n.format("gui.button.play"))
                .setScale(2)
                .setYOffset(-3)
                .setImage(new ResourceLocation(Reference.MOD_ID,"textures/gui/menu/play.png")));

        this.buttonList.add(new GuiButtonCubg(BUTTON_BEAVER,5,height - 80,120,15,I18n.format("gui.button.beaver"))
                .setAlwaysHighlighted(designatedServerIP.equalsIgnoreCase(Reference.SERVERIP_BEAVER)));
        this.buttonList.add(new GuiButtonCubg(BUTTON_KANGAROO,5,height - 60,120,15,I18n.format("gui.button.kangaroo"))
                .setAlwaysHighlighted(designatedServerIP.equalsIgnoreCase(Reference.SERVERIP_KANGAROO)));

        if((Boolean) Launch.blackboard.get("fml.deobfuscatedEnvironment")) {
        this.buttonList.add(new GuiButtonCubg(BUTTON_MULTIPLAYER,5,height - 120,120,15,I18n.format("gui.button.mutliplayer")));
        }

    }

}