package com.cubgdev.cubga.proxy;

import com.cubgdev.cubga.client.BuilderCapeHandler;
import com.cubgdev.cubga.client.CrayfishCapeHandler;
import com.cubgdev.cubga.client.CreatorCapeHandler;
import com.cubgdev.cubga.client.StaffCapeHandler;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

public class ClientProxy extends CommonProxy
{

    public void preInit(FMLPreInitializationEvent event) {
        super.preInit(event);
    }

    public void init(FMLInitializationEvent event) {
        super.init(event);
        MinecraftForge.EVENT_BUS.register(StaffCapeHandler.class);
        MinecraftForge.EVENT_BUS.register(BuilderCapeHandler.class);
        MinecraftForge.EVENT_BUS.register(CreatorCapeHandler.class);
        MinecraftForge.EVENT_BUS.register(CrayfishCapeHandler.class);
    }

    public void postInit(FMLPostInitializationEvent event) {
        super.postInit(event);
    }
}