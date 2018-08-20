package com.cubgdev.cubga.utils;

import java.awt.image.BufferedImage;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.imageio.ImageIO;

import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL13;

import com.cubgdev.cubga.CUBG;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.util.ResourceLocation;

/**
 * <em><b>Copyright (c) 2018 The Zerra Team.</b></em>
 * 
 * <br>
 * </br>
 * 
 * Has the capability to load textures to and from memory.
 * 
 * @author Ocelot5836
 */
public class Loader {

	private static List<Integer> textures = new ArrayList<Integer>();
	private static Map<String, ByteBuffer> buffers = new HashMap<String, ByteBuffer>();

	/**
	 * Deletes the vertex arrays, vertex buffer objects, and textures from memory.
	 */
	public static void cleanUp() {
		for (Integer texture : textures) {
			GL11.glDeleteTextures(texture);
		}
	}

	/**
	 * Loads an image to a byte buffer. Used when loading textures.
	 * 
	 * @param image
	 *            The image to load to a byte buffer
	 * @return The buffer created from the image or null if the image was null
	 * @throws NullPointerException
	 *             Throws this if the image was null
	 */
	public static ByteBuffer loadToByteBuffer(BufferedImage image) throws NullPointerException {
		int width = image.getWidth();
		int height = image.getHeight();
		int[] pixels = new int[width * height];
		pixels = image.getRGB(0, 0, width, height, null, 0, width);

		ByteBuffer buffer = BufferUtils.createByteBuffer(width * height * 4);
		for (int y = 0; y < height; y++) {
			for (int x = 0; x < width; x++) {
				int color = pixels[x + y * width];
				buffer.put((byte) ((color >> 16) & 0xff));
				buffer.put((byte) ((color >> 8) & 0xff));
				buffer.put((byte) (color & 0xff));
				buffer.put((byte) ((color >> 24) & 0xff));
			}
		}
		buffer.flip();
		return buffer;
	}

	/**
	 * Loads a texture to memory.
	 * 
	 * @param location
	 *            The location of said texture
	 * @return The texture created
	 */
	public static int loadTexture(ResourceLocation location) {
		try {
			int textureID = GlStateManager.generateTexture();
			textures.add(textureID);
			return Loader.loadTexture(textureID, ImageIO.read(Minecraft.getMinecraft().getResourceManager().getResource(location).getInputStream()), location.toString());
		} catch (Exception e) {
			CUBG.logger().warn("Could not find image at \'" + location + "\'");
			return 0;
		}
	}

	/**
	 * Loads a buffered image to memory.
	 * 
	 * @param textureID
	 *            The id to load the texture into
	 * @param image
	 *            The image to load to memory
	 * @return The texture created
	 * @throws NullPointerException
	 *             Throws this if the image was null
	 */
	public static int loadTexture(int textureID, BufferedImage image, String id) throws NullPointerException {
		int width = image.getWidth();
		int height = image.getHeight();

		ByteBuffer pixels = buffers.get(id);
		if (pixels == null) {
			pixels = loadToByteBuffer(image);
			buffers.put(id, pixels);
		}

		GL13.glActiveTexture(GL13.GL_TEXTURE0);
		GL11.glBindTexture(GL11.GL_TEXTURE_2D, textureID);
		GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MIN_FILTER, GL11.GL_NEAREST);
		GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MAG_FILTER, GL11.GL_NEAREST);
		GL11.glTexImage2D(GL11.GL_TEXTURE_2D, 0, GL11.GL_RGBA, width, height, 0, GL11.GL_RGBA, GL11.GL_UNSIGNED_BYTE, pixels);

		return textureID;
	}

	/**
	 * Clears all loaded texture buffers.
	 */
	public static void clearTextureBuffers() {
		buffers.clear();
	}
}