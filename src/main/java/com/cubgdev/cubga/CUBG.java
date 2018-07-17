package com.cubgdev.cubga;

import com.cubgdev.cubga.common.CommandResetBrittleBricks;
import com.cubgdev.cubga.common.CommonEvents;
import com.cubgdev.cubga.init.ModItems;
import com.cubgdev.cubga.init.RegistrationHandler;
import com.cubgdev.cubga.network.PacketHandler;
import com.cubgdev.cubga.proxy.CommonProxy;
import com.cubgdev.cubga.tileentity.TileEntityBrittleBrick;
import com.cubgdev.cubga.tileentity.TileEntityCrystalContainer;
import com.cubgdev.cubga.tileentity.TileEntityPlayerPlush;
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
public class CUBG
{
    public static final CreativeTabs TAB = new CreativeTabs("tabCUBG") {
        public ItemStack getTabIconItem() {
            return new ItemStack(ModItems.MEDKIT);
        }
    };

    @Mod.Instance
    public static CUBG instance;

    @SidedProxy(clientSide = Reference.PROXY_CLIENT, serverSide = Reference.PROXY_SERVER)
    public static CommonProxy proxy;

    @Mod.EventHandler
    public void preInit(FMLPreInitializationEvent event) {
        proxy.preInit(event);
        RegistrationHandler.init();
        MinecraftForge.EVENT_BUS.register(new CommonEvents());
        PacketHandler.init();
    }

    @Mod.EventHandler
    public void init(FMLInitializationEvent event) {
        proxy.init(event);
        GameRegistry.registerTileEntity(TileEntityBrittleBrick.class, Reference.MOD_ID + ":brittle_brick");
        GameRegistry.registerTileEntity(TileEntityPlayerPlush.class, Reference.MOD_ID + ":player_plush");
        GameRegistry.registerTileEntity(TileEntityCrystalContainer.class, Reference.MOD_ID + ":crystal_container");
    }

    @Mod.EventHandler
    public void postInit(FMLPostInitializationEvent event) {
        proxy.postInit(event);
    }

    @Mod.EventHandler
    public void onServerStarting(FMLServerStartingEvent event)
    {
        event.registerServerCommand(new CommandResetBrittleBricks());
    }
}
