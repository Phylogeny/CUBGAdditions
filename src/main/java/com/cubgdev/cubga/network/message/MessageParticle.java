package com.cubgdev.cubga.network.message;

import com.cubgdev.cubga.CUBG;
import com.cubgdev.cubga.common.EnumParticles;
import io.netty.buffer.ByteBuf;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

public class MessageParticle implements IMessage, IMessageHandler<MessageParticle, IMessage>
{
	private EnumParticles particleType;
	private double xCoordIn, yCoordIn, zCoordIn;
	private double xSpeedIn, ySpeedIn, zSpeedIn;

	public MessageParticle()
	{
	}

	public MessageParticle(EnumParticles particleType, double xCoordIn, double yCoordIn, double zCoordIn, double xSpeedIn, double ySpeedIn, double zSpeedIn)
	{
		this.particleType = particleType;
		this.xCoordIn = xCoordIn;
		this.yCoordIn = yCoordIn;
		this.zCoordIn = zCoordIn;
		this.xSpeedIn = xSpeedIn;
		this.ySpeedIn = ySpeedIn;
		this.zSpeedIn = zSpeedIn;
	}

	@Override
	public void toBytes(ByteBuf buf)
	{
		buf.writeInt(particleType.ordinal());
		buf.writeDouble(xCoordIn);
		buf.writeDouble(yCoordIn);
		buf.writeDouble(zCoordIn);
		buf.writeDouble(xSpeedIn);
		buf.writeDouble(ySpeedIn);
		buf.writeDouble(zSpeedIn);
	}

	@Override
	public void fromBytes(ByteBuf buf)
	{
		this.particleType = EnumParticles.values()[buf.readInt()];
		this.xCoordIn = buf.readDouble();
		this.yCoordIn = buf.readDouble();
		this.zCoordIn = buf.readDouble();
		this.xSpeedIn = buf.readDouble();
		this.ySpeedIn = buf.readDouble();
		this.zSpeedIn = buf.readDouble();
	}

	@Override
	public IMessage onMessage(MessageParticle message, MessageContext ctx)
	{
		CUBG.proxy.spawnParticle(message.particleType, message.xCoordIn, message.yCoordIn, message.zCoordIn, message.xSpeedIn, message.ySpeedIn, message.zSpeedIn);
		return null;
	}
}
