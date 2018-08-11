package com.cubgdev.cubga.network.message;

import com.cubgdev.cubga.utils.cape.Cape;
import com.cubgdev.cubga.utils.cape.Capes;

import io.netty.buffer.ByteBuf;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.fml.common.network.ByteBufUtils;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;
import net.minecraftforge.fml.relauncher.Side;

public class MessageUpdateCapes implements IMessage, IMessageHandler<MessageUpdateCapes, IMessage> {

	private NBTTagCompound capes;
	private boolean valid;

	public MessageUpdateCapes() {
		this.valid = false;
	}

	public MessageUpdateCapes(Cape[] donators) {
		this.capes = new NBTTagCompound();
		this.capes.setInteger("size", donators.length);
		for (int i = 0; i < donators.length; i++) {
			this.capes.setTag(Integer.toHexString(i), donators[i].serializeNBT());
		}
		this.valid = true;
	}

	@Override
	public void toBytes(ByteBuf buf) {
		if (!this.valid)
			return;
		ByteBufUtils.writeTag(buf, this.capes);
	}

	@Override
	public void fromBytes(ByteBuf buf) {
		try {
			this.capes = ByteBufUtils.readTag(buf);
			this.valid = true;
		} catch (Exception e) {
			this.valid = false;
		}
	}

	@Override
	public IMessage onMessage(MessageUpdateCapes message, MessageContext ctx) {
		if (ctx.side == Side.SERVER)
			return null;
		Capes.clear();
		for (int i = 0; i < message.capes.getInteger("size"); i++) {
			Capes.add(Cape.fromTag(message.capes.getCompoundTag(Integer.toHexString(i))));
		}
		return null;
	}
}