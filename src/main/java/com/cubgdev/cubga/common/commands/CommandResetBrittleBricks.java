package com.cubgdev.cubga.common.commands;

import com.cubgdev.cubga.common.CommonEvents;
import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.server.MinecraftServer;

/**
 * Author: MrCrayfish
 */
public class CommandResetBrittleBricks extends CommandBase
{
    @Override
    public String getName()
    {
        return "resetbrittlebricks";
    }

    @Override
    public String getUsage(ICommandSender sender)
    {
        return "commands.resetbrittlebricks.usage";
    }

    @Override
    public void execute(MinecraftServer server, ICommandSender sender, String[] args) throws CommandException
    {
        CommonEvents.replaceBricks = true;
    }
}
