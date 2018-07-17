package com.cubgdev.cubga.proxy;

import com.cubgdev.cubga.client.CapeHandler;
import com.cubgdev.cubga.client.particle.ParticleBrick;
import com.cubgdev.cubga.client.particle.ParticleRenderer;
import com.cubgdev.cubga.client.render.entity.RenderThrowableBrick;
import com.cubgdev.cubga.common.EnumParticles;
import com.cubgdev.cubga.entity.EntityThrowableBrick;
import com.cubgdev.cubga.tileentity.TileEntityPlayerPlush;
import com.cubgdev.cubga.tileentity.TileEntityCrystalContainer;
import com.cubgdev.cubga.tileentity.render.TileEntityRendererPlayerPlush;
import com.cubgdev.cubga.tileentity.render.TileEntityCrystalContainerRenderer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import net.minecraft.client.Minecraft;
import net.minecraft.client.particle.Particle;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.world.World;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import net.minecraftforge.fml.client.registry.RenderingRegistry;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import java.util.UUID;

public class ClientProxy extends CommonProxy
{
    private static final HashMap<UUID, String> CAPE_MAP = new HashMap<>();

    public void preInit(FMLPreInitializationEvent event) {
        super.preInit(event);
    }

    public void init(FMLInitializationEvent event) {
        super.init(event);
        //RenderPlayerPlush
        ClientRegistry.bindTileEntitySpecialRenderer(TileEntityPlayerPlush.class, new TileEntityRendererPlayerPlush());

        // RenderThrowableBrick
        RenderManager renderManager = Minecraft.getMinecraft().getRenderManager();
        RenderingRegistry.registerEntityRenderingHandler(EntityThrowableBrick.class, new RenderThrowableBrick(renderManager, Minecraft.getMinecraft().getRenderItem()));

        // Render Containers
        ClientRegistry.bindTileEntitySpecialRenderer(TileEntityCrystalContainer.class, new TileEntityCrystalContainerRenderer());

        // Cape, Particles, etc...
        MinecraftForge.EVENT_BUS.register(CapeHandler.class);
        MinecraftForge.EVENT_BUS.register(ParticleRenderer.getInstance());

        Runnable r = () -> {
            String data = getRemoteString("https://raw.githubusercontent.com/JacksonPlayz/CuBG-Resources/master/capes/capes.json");
            if(data != null) {
                JsonParser parser = new JsonParser();
                JsonObject object = parser.parse(data).getAsJsonObject();
                for(Map.Entry<String, JsonElement> entry : object.entrySet()) {
                    getCapeMap().put(UUID.fromString(entry.getKey()), object.get(entry.getKey()).toString());
                }
            }
        };
        Thread thread = new Thread(r);
        thread.start();
    }

    public void postInit(FMLPostInitializationEvent event) {
        super.postInit(event);
    }

    public static HashMap<UUID, String> getCapeMap()
    {
        return CAPE_MAP;
    }

    public static String getRemoteString(String urlQueryString) {
        try {
            URL url = new URL(urlQueryString);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setDoOutput(true);
            connection.setInstanceFollowRedirects(false);
            connection.setRequestMethod("GET");
            connection.setRequestProperty("Content-Type", "application/json");
            connection.setRequestProperty("charset", "utf-8");
            connection.connect();
            InputStream inStream = connection.getInputStream();
            return convertStreamToString(inStream);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        return null;
    }

    private static String convertStreamToString(InputStream inputStream) {
        String text = new Scanner(inputStream, "UTF-8").useDelimiter("\\Z").next();
        return text;
    }

    @Override
    public void spawnParticle(EnumParticles particleType, double xCoordIn, double yCoordIn, double zCoordIn, double xSpeedIn, double ySpeedIn, double zSpeedIn)
    {
        World world = Minecraft.getMinecraft().world;
        Particle particle = null;
        switch(particleType) {
            case BRICK:
                particle = new ParticleBrick(world, xCoordIn, yCoordIn, zCoordIn, xSpeedIn, ySpeedIn, zSpeedIn);
                break;
        }
        ParticleRenderer.getInstance().addParticle(particle);
    }
}