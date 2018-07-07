package com.cubgdev.cubga.client;

import com.cubgdev.cubga.util.LibObfuscation;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.mojang.authlib.minecraft.MinecraftProfileTexture;
import javafx.scene.control.Skin;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.AbstractClientPlayer;
import net.minecraft.client.network.NetworkPlayerInfo;
import net.minecraft.client.renderer.ImageBufferDownload;
import net.minecraft.client.renderer.ThreadDownloadImageData;
import net.minecraft.client.renderer.texture.DynamicTexture;
import net.minecraft.client.renderer.texture.ITextureObject;
import net.minecraft.client.renderer.texture.TextureManager;
import net.minecraft.client.resources.DefaultPlayerSkin;
import net.minecraft.client.resources.SkinManager;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.StringUtils;
import net.minecraftforge.client.event.RenderPlayerEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.ReflectionHelper;
import org.lwjgl.Sys;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.*;

public class CapeHandler {

    private static final Set<EntityPlayer> done = Collections.newSetFromMap(new WeakHashMap());


    private static HashMap<UUID, String> validPlayers = new HashMap<>();


    @SubscribeEvent
    public static void onRenderPlayer(RenderPlayerEvent.Post event) {

        JsonParser jsonParser = new JsonParser();
        JsonObject jo = (JsonObject)jsonParser.parse(jsonGetRequest("https://raw.githubusercontent.com/JacksonPlayz/CuBG-Resources/master/capes/capes.json"));
        for (Map.Entry<String, JsonElement> entry: jo.entrySet()) {
            validPlayers.put(UUID.fromString(entry.getKey()), jo.get(entry.getKey()).toString());
        }



        EntityPlayer player = event.getEntityPlayer();
        String uuid = player.getUUID(player.getGameProfile()).toString();
        if(player instanceof AbstractClientPlayer && validPlayers.containsKey(UUID.fromString(uuid)) && !done.contains(player)) {
            AbstractClientPlayer clplayer = (AbstractClientPlayer) player;
            if(clplayer.hasPlayerInfo()) {
                NetworkPlayerInfo info = ReflectionHelper.getPrivateValue(AbstractClientPlayer.class, clplayer, LibObfuscation.PLAYER_INFO);
                Map<MinecraftProfileTexture.Type, ResourceLocation> textures = ReflectionHelper.getPrivateValue(NetworkPlayerInfo.class, info, LibObfuscation.PLAYER_TEXTURES);
                SkinManager skinManager = Minecraft.getMinecraft().getSkinManager();

                try {
                    URL url = new URL(validPlayers.get(UUID.fromString(uuid)).replace("\"", ""));
                    BufferedImage image = ImageIO.read(url);
                    DynamicTexture dynamicTexture = new DynamicTexture(image);
                    dynamicTexture.loadTexture(Minecraft.getMinecraft().getResourceManager());
                    textures.put(MinecraftProfileTexture.Type.CAPE, Minecraft.getMinecraft().getTextureManager().getDynamicTextureLocation(uuid, dynamicTexture));
                    textures.put(MinecraftProfileTexture.Type.ELYTRA, Minecraft.getMinecraft().getTextureManager().getDynamicTextureLocation(uuid, dynamicTexture));
                    }catch (Exception e){
                    e.printStackTrace();
                }
                done.add(player);
            }

        }
    }

    public static String jsonGetRequest(String urlQueryString) {
        String json = null;
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
            json = streamToString(inStream); // input stream to string
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        return json;
    }

    private static String streamToString(InputStream inputStream) {
        String text = new Scanner(inputStream, "UTF-8").useDelimiter("\\Z").next();
        return text;
    }

    public static ThreadDownloadImageData getDownloadImage(ResourceLocation resourceLocationIn, String url)
    {
        TextureManager texturemanager = Minecraft.getMinecraft().getTextureManager();
        ITextureObject itextureobject = texturemanager.getTexture(resourceLocationIn);

        if (itextureobject == null)
        {
            itextureobject = new ThreadDownloadImageData((File)null, url, null, new ImageBufferDownload());
            texturemanager.loadTexture(resourceLocationIn, itextureobject);
        }

        return (ThreadDownloadImageData)itextureobject;
    }

}
