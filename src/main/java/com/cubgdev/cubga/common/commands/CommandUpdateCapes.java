package com.cubgdev.cubga.common.commands;

import com.cubgdev.cubga.common.LootChestManager;
import com.cubgdev.cubga.network.PacketHandler;
import com.cubgdev.cubga.network.message.MessageUpdateCapes;
import com.cubgdev.cubga.utils.cape.Capes;
import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.server.MinecraftServer;
import net.minecraft.world.World;

public class CommandUpdateCapes extends CommandBase {


    @Override
    public String getName()
    {
        return "updatecapes";
    }

    @Override
    public String getUsage(ICommandSender sender)
    {
        return "commands.updatecapes.usage";
    }

    @Override
    public void execute(MinecraftServer server, ICommandSender sender, String[] args) throws CommandException
    {
        PacketHandler.INSTANCE.sendToAll(new MessageUpdateCapes(Capes.getCapes()));
    }
}
