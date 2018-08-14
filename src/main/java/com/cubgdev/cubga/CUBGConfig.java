package com.cubgdev.cubga;

import net.minecraftforge.common.config.Config;
import net.minecraftforge.common.config.ConfigManager;
import net.minecraftforge.fml.client.event.ConfigChangedEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

@Config(modid = Reference.MOD_ID)
@Config.LangKey("config." + Reference.MOD_ID + ".title")
@Mod.EventBusSubscriber(modid = Reference.MOD_ID)
public class CUBGConfig
{
    private static final String PREFIX = "config." + Reference.MOD_ID;

    @Config.Name("Client")
    @Config.Comment("Client-only configs.")
    @Config.LangKey(PREFIX + "client")
    public static final Client CLIENT = new Client();

    public static class Client
    {
        @Config.Name("UI")
        @Config.Comment("UI settings / customization.")
        @Config.LangKey(PREFIX + "client.ui")
        public UI ui = new UI();
    }

    public static class UI {
        @Config.Name("Health Bar")
        @Config.Comment("Health bar settings / customization.")
        @Config.LangKey(PREFIX + "client.ui.health")
        public Health health = new Health();

        @Config.Name("Enable UI")
        @Config.Comment("Determines whether or not the custom UI will render show.")
        @Config.LangKey(PREFIX + "client.ui.enabled")
        public boolean uiEnabled = true; // Default: Enabled
    }

    public static class Health {
        @Config.Name("Enable Clean Health Bar")
        @Config.Comment("Determines whether or not the health bar will have the clean bar texture (Colorable)")
        @Config.LangKey(PREFIX + "client.ui.health.cleanbar")
        public boolean cleanBarEnabled = false; // Default: False

        @Config.Name("Percentages")
        @Config.Comment("Determines whether the health bar will use percentages")
        @Config.LangKey(PREFIX + "client.ui.health.percentages")
        public boolean percentagesEnabled = true; // Default: True

        @Config.Name("Enable Health Numbers")
        @Config.Comment("Determines whether the numbers above the health bar will show.")
        @Config.LangKey(PREFIX + "client.ui.health.numbers")
        public boolean numbersEnabled = true; // Default: True

        @Config.Name("Health Bar Colors")
        @Config.Comment("Determines what color will be used on the health bar.")
        @Config.LangKey(PREFIX + "client.ui.health.color")
        public HealthColor healthColor = new HealthColor();
    }

    public static class HealthColor {
        @Config.Name("Blue")
        @Config.Comment("Blue value of the health bar RGB.")
        @Config.LangKey(PREFIX + "client.ui.health.color.blue")
        @Config.RangeInt(min = 0, max = 255)
        public int Blue = 85; // Default: 85
        @Config.Name("Green")
        @Config.Comment("Green value of the health bar RGB.")
        @Config.LangKey(PREFIX + "client.ui.health.color.green")
        @Config.RangeInt(min = 0, max = 255)
        public int Green = 85; // Default: 85
        @Config.Name("Red")
        @Config.Comment("Red value of the health bar RGB.")
        @Config.LangKey(PREFIX + "client.ui.health.color.red")
        @Config.RangeInt(min = 0, max = 255)
        public int Red = 255; // Default: 255
    }

    @SubscribeEvent
    public static void onConfigChanged(ConfigChangedEvent.OnConfigChangedEvent event)
    {
        if (event.getModID().equalsIgnoreCase(Reference.MOD_ID))
        {
            ConfigManager.sync(Reference.MOD_ID, Config.Type.INSTANCE);
        }
    }
}
