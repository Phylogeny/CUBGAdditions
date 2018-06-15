package com.cubgdev.cubga;

import com.cubgdev.cubga.init.ModItems;
import com.cubgdev.cubga.init.RegistrationHandler;
import com.cubgdev.cubga.proxy.CommonProxy;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

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
    private static CommonProxy proxy;

    @Mod.EventHandler
    public void preInit(FMLPreInitializationEvent event) {
        proxy.preInit(event);
        RegistrationHandler.init();
    }

    @Mod.EventHandler
    public void init(FMLInitializationEvent event) {
        proxy.init(event);
    }

    @Mod.EventHandler
    public void postInit(FMLPostInitializationEvent event) {
        proxy.postInit(event);
    }
}
