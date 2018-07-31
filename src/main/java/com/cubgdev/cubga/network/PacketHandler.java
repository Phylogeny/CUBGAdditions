package com.cubgdev.cubga.network;

import com.cubgdev.cubga.Reference;
import com.cubgdev.cubga.network.message.MessageParticle;
import com.cubgdev.cubga.network.message.MessageUpdateValueContainer;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.fml.common.network.simpleimpl.SimpleNetworkWrapper;
import net.minecraftforge.fml.relauncher.Side;

public class PacketHandler
{
	public static final SimpleNetworkWrapper INSTANCE = NetworkRegistry.INSTANCE.newSimpleChannel(Reference.MOD_ID);

	public static void init()
	{
		INSTANCE.registerMessage(MessageParticle.class, MessageParticle.class, 0, Side.CLIENT);
		INSTANCE.registerMessage(MessageUpdateValueContainer.class, MessageUpdateValueContainer.class, 1, Side.SERVER);
	}
}
