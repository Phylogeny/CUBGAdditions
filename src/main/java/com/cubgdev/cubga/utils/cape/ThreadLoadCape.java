package com.cubgdev.cubga.utils.cape;

import java.awt.image.BufferedImage;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Map;

import javax.imageio.ImageIO;

import com.cubgdev.cubga.CUBG;
import com.cubgdev.cubga.utils.TextureUtils;
import com.mojang.authlib.minecraft.MinecraftProfileTexture;

import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.AbstractClientPlayer;
import net.minecraft.client.network.NetworkPlayerInfo;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ResourceLocation;

public class ThreadLoadCape extends Thread {

	private Cape cape;

	protected ThreadLoadCape(Cape cape) {
		this.cape = cape;
	}

	@Override
	public void run() {
		String url = "https://raw.githubusercontent.com/JacksonPlayz/CuBG-Resources/master/capes/" + this.cape.getName();
		try {
			URL texture = new URL(url);
			BufferedImage image = ImageIO.read(texture.openStream());
			Capes.loadCapeTexture(this.cape, image);
		} catch (MalformedURLException e) {
			CUBG.logger().fatal("Invalid URL: \'" + url + "\'", e);
		} catch (Exception e) {
			CUBG.logger().fatal("Could not load cape texture from URL: \'" + url + "\'", e);
		}
	}
}