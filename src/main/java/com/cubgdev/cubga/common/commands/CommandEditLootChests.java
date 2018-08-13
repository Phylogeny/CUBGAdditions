package com.cubgdev.cubga.common.commands;

import com.cubgdev.cubga.common.LootChestManager;
import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.command.WrongUsageException;
import net.minecraft.server.MinecraftServer;
import net.minecraft.world.World;

/**
 * Author: MrCrayfish
 */
public class CommandEditLootChests extends CommandBase
{
    @Override
    public String getName()
    {
        return "editlootchests";
    }

    @Override
    public String getUsage(ICommandSender sender)
    {
        return "commands.editlootchests.usage";
    }

    @Override
    public void execute(MinecraftServer server, ICommandSender sender, String[] args) throws CommandException
    {
        if (args.length < 1)
        {
            throw new WrongUsageException("commands.editlootchests.usage");
        }

        boolean edit = Boolean.parseBoolean(args[0]);

        World world = sender.getEntityWorld();
        if(world.provider.getDimension() == 0)
        {
            LootChestManager.get(world).setEditing(world, edit);
        }
    }
}
