package com.cubgdev.cubga;

import com.cubgdev.cubga.client.gui.GuiHandler;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import org.apache.logging.log4j.Logger;

import com.cubgdev.cubga.common.CommonEvents;
import com.cubgdev.cubga.common.commands.CommandEditLootChests;
import com.cubgdev.cubga.common.commands.CommandGenerateLootChests;
import com.cubgdev.cubga.common.commands.CommandResetBrittleBricks;
import com.cubgdev.cubga.init.ModItems;
import com.cubgdev.cubga.init.RegistrationHandler;
import com.cubgdev.cubga.network.PacketHandler;
import com.cubgdev.cubga.proxy.CommonProxy;
import com.cubgdev.cubga.tileentity.TileEntityBrittleBrick;
import com.cubgdev.cubga.tileentity.TileEntityCrystal;
import com.cubgdev.cubga.tileentity.TileEntityCrystalContainer;
import com.cubgdev.cubga.tileentity.TileEntityLootChest;
import com.cubgdev.cubga.utils.cape.Capes;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.event.FMLServerStartingEvent;
import net.minecraftforge.fml.common.registry.GameRegistry;

@Mod(modid = Reference.MOD_ID, name = Reference.MOD_NAME, version = Reference.MOD_VERSION, acceptedMinecraftVersions = Reference.MOD_COMPATIBILITY, dependencies = Reference.MOD_DEPENDS)
public class CUBG {

	public static final CreativeTabs TAB = new CreativeTabs("tabCUBG") {
		public ItemStack getTabIconItem() {
			return new ItemStack(ModItems.MEDKIT);
		}
	};

	@Mod.Instance
	public static CUBG instance;

	@SidedProxy(clientSide = Reference.PROXY_CLIENT, serverSide = Reference.PROXY_SERVER)
	public static CommonProxy proxy;

	private static Logger logger;

	@Mod.EventHandler
	public void preInit(FMLPreInitializationEvent event) {
		logger = event.getModLog();
		proxy.preInit(event);
		RegistrationHandler.init();
		MinecraftForge.EVENT_BUS.register(new CommonEvents());
		PacketHandler.init();
		NetworkRegistry.INSTANCE.registerGuiHandler(this, new GuiHandler());
	}

	@Mod.EventHandler
	public void init(FMLInitializationEvent event) {
		proxy.init(event);
		GameRegistry.registerTileEntity(TileEntityBrittleBrick.class, new ResourceLocation(Reference.MOD_ID, "brittle_brick"));
		// GameRegistry.registerTileEntity(TileEntityPlayerPlush.class, new ResourceLocation(Reference.MOD_ID, "player_plush"));
		GameRegistry.registerTileEntity(TileEntityCrystalContainer.class, new ResourceLocation(Reference.MOD_ID, "crystal_container"));
		GameRegistry.registerTileEntity(TileEntityCrystal.class, new ResourceLocation(Reference.MOD_ID, "crystal"));
		GameRegistry.registerTileEntity(TileEntityLootChest.class, new ResourceLocation(Reference.MOD_ID, "loot_chest"));

	}

	@Mod.EventHandler
	public void postInit(FMLPostInitializationEvent event) {
		proxy.postInit(event);
	}

	@Mod.EventHandler
	public void onServerStarting(FMLServerStartingEvent event) {
		event.registerServerCommand(new CommandResetBrittleBricks());
		event.registerServerCommand(new CommandEditLootChests());
		event.registerServerCommand(new CommandGenerateLootChests());
		Capes.load();
	}

	public static Logger logger() {
		return logger;
	}
}
