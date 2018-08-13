package com.cubgdev.cubga.proxy;

import com.cubgdev.cubga.client.GuiEvents;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.*;

import com.cubgdev.cubga.client.CapeHandler;
import com.cubgdev.cubga.client.RenderEvents;
import com.cubgdev.cubga.client.particle.ParticleBrick;
import com.cubgdev.cubga.client.particle.ParticleRenderer;
import com.cubgdev.cubga.client.render.entity.RenderThrowableBrick;
import com.cubgdev.cubga.common.EnumParticles;
import com.cubgdev.cubga.entity.EntityThrowableBrick;
import com.cubgdev.cubga.tileentity.TileEntityPlayerPlush;
import com.cubgdev.cubga.init.ModBlocks;
import com.cubgdev.cubga.tileentity.TileEntityCrystalContainer;
import com.cubgdev.cubga.tileentity.TileEntityLootChest;
import com.cubgdev.cubga.tileentity.TileEntityPlayerPlush;
import com.cubgdev.cubga.tileentity.render.TileEntityCrystalContainerRenderer;
import com.cubgdev.cubga.tileentity.render.TileEntityLootChestRenderer;
import com.cubgdev.cubga.tileentity.render.TileEntityRendererPlayerPlush;
import com.cubgdev.cubga.utils.DiscordHandler;
import com.cubgdev.cubga.utils.cape.Capes;

import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.particle.Particle;
import net.minecraft.client.renderer.block.model.ModelBakery;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.client.renderer.block.statemap.IStateMapper;
import net.minecraft.client.renderer.block.statemap.StateMap;
import net.minecraft.client.renderer.block.statemap.StateMapperBase;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import net.minecraftforge.fml.client.registry.RenderingRegistry;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

public class ClientProxy extends CommonProxy {

	@Override
	public void preInit(FMLPreInitializationEvent event) {
		super.preInit(event);
		/* Register Render Events */
		MinecraftForge.EVENT_BUS.register(new RenderEvents());

		/* Register Gui Events */
		MinecraftForge.EVENT_BUS.register(new GuiEvents());

		/* Register DiscordHandler */
		DiscordHandler.getInstance().setup();

		/** Get fields for the client capes */
		Capes.initClient();

		/** Loot Chests */
		ModelLoader.setCustomStateMapper(ModBlocks.LOOT_CHEST, blockIn -> Collections.emptyMap());
	}

	@Override
	public void init(FMLInitializationEvent event) {
		super.init(event);
		// RenderPlayerPlush
		ClientRegistry.bindTileEntitySpecialRenderer(TileEntityPlayerPlush.class, new TileEntityRendererPlayerPlush());
		ClientRegistry.bindTileEntitySpecialRenderer(TileEntityLootChest.class, new TileEntityLootChestRenderer());

		// RenderThrowableBrick
		RenderManager renderManager = Minecraft.getMinecraft().getRenderManager();
		RenderingRegistry.registerEntityRenderingHandler(EntityThrowableBrick.class, new RenderThrowableBrick(renderManager, Minecraft.getMinecraft().getRenderItem()));

		// Particles, etc...
		MinecraftForge.EVENT_BUS.register(ParticleRenderer.getInstance());
	}

	@Override
	public void postInit(FMLPostInitializationEvent event) {
		super.postInit(event);
	}

	@Override
	public void spawnParticle(EnumParticles particleType, double xCoordIn, double yCoordIn, double zCoordIn, double xSpeedIn, double ySpeedIn, double zSpeedIn) {
		World world = Minecraft.getMinecraft().world;
		Particle particle = null;
		switch (particleType) {
		case BRICK:
			particle = new ParticleBrick(world, xCoordIn, yCoordIn, zCoordIn, xSpeedIn, ySpeedIn, zSpeedIn);
			break;
		}
		ParticleRenderer.getInstance().addParticle(particle);
	}
}