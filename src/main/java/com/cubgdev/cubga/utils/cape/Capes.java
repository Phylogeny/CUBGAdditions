package com.cubgdev.cubga.utils.cape;

import java.awt.image.BufferedImage;
import java.lang.reflect.Field;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.annotation.Nullable;

import com.cubgdev.cubga.utils.Lib;
import com.cubgdev.cubga.utils.TextureUtils;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.mojang.authlib.minecraft.MinecraftProfileTexture;
import com.mojang.authlib.minecraft.MinecraftProfileTexture.Type;

import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.AbstractClientPlayer;
import net.minecraft.client.network.NetworkPlayerInfo;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.relauncher.ReflectionHelper;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class Capes {

	private static final List<Cape> CAPES = Lists.<Cape>newArrayList();
	private static final Map<Cape, BufferedImage> REQUESTED_IMAGES = Maps.newHashMap();

	private static final Field PLAYER_INFO = ReflectionHelper.findField(AbstractClientPlayer.class, "playerInfo", "field_175157_a");
	private static final Field PLAYER_TEXTURES = ReflectionHelper.findField(NetworkPlayerInfo.class, "playerTextures", "field_187107_a");

	public static void clear() {
		CAPES.clear();
		REQUESTED_IMAGES.clear();
	}

	public static void update() {
		try {
			if (Minecraft.getMinecraft().world != null) {
				for (Cape cape : REQUESTED_IMAGES.keySet()) {
					BufferedImage image = REQUESTED_IMAGES.get(cape);
					ResourceLocation location = TextureUtils.createBufferedImageTexture(image);

					for (int i = 0; i < cape.getUsers().length; i++) {
						EntityPlayer player = Minecraft.getMinecraft().world.getPlayerEntityByUUID(cape.getUsers()[i]);
						if (player instanceof AbstractClientPlayer) {
							AbstractClientPlayer client = (AbstractClientPlayer) player;
							NetworkPlayerInfo info = (NetworkPlayerInfo) PLAYER_INFO.get(client);
							Map<MinecraftProfileTexture.Type, ResourceLocation> textures = (Map<Type, ResourceLocation>) PLAYER_TEXTURES.get(info);
							try {
								textures.put(MinecraftProfileTexture.Type.CAPE, location);
								textures.put(MinecraftProfileTexture.Type.ELYTRA, location);
							} catch (Exception e) {
								textures.put(MinecraftProfileTexture.Type.CAPE, TextureMap.LOCATION_MISSING_TEXTURE);
								textures.put(MinecraftProfileTexture.Type.ELYTRA, TextureMap.LOCATION_MISSING_TEXTURE);
							}
						}
					}
				}
				REQUESTED_IMAGES.clear();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void load() {
		Capes.clear();

		String data = Lib.getRemoteString("https://raw.githubusercontent.com/JacksonPlayz/CuBG-Resources/master/accessories/accessories.json");
		if (data != null) {
			JsonParser parser = new JsonParser();
			JsonElement json = parser.parse(data);

			JsonObject capes = json.getAsJsonObject().get("capes").getAsJsonObject();

			for (Entry<String, JsonElement> entry : capes.entrySet()) {
				JsonArray cape = entry.getValue().getAsJsonArray();
				String name = entry.getKey();
				String[] users = new String[cape.size()];
				for (int i = 0; i < users.length; i++) {
					users[i] = cape.get(i).getAsString();
				}
				add(new Cape(name, users));
			}
		}
	}

	public static void add(NBTTagCompound cape) {
		add(Cape.fromTag(cape));
	}

	public static void add(String name, String... users) {
		add(new Cape(name, users));
	}

	public static void add(Cape cape) {
		CAPES.add(cape);
		if (FMLCommonHandler.instance().getSide() == Side.CLIENT) {
			new ThreadLoadCape(cape).start();
		}
	}

	public static Cape[] getCapes() {
		return CAPES.toArray(new Cape[0]);
	}

	@Nullable
	public static Cape getCape(EntityPlayer player) {
		for (int i = 0; i < CAPES.size(); i++) {
			Cape cape = CAPES.get(i);
			if (cape.hasCape(player)) {
				return cape;
			}
		}
		return null;
	}

	@SideOnly(Side.CLIENT)
	protected static void loadCapeTexture(Cape cape, BufferedImage image) {
		REQUESTED_IMAGES.put(cape, image);
	}
}