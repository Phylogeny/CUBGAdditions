package com.cubgdev.cubga.common.commands;

import com.cubgdev.cubga.common.LootChestManager;
import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.server.MinecraftServer;
import net.minecraft.world.World;

/**
 * Author: MrCrayfish
 */
public class CommandGenerateLootChests extends CommandBase
{
    @Override
    public String getName()
    {
        return "generatelootchests";
    }

    @Override
    public String getUsage(ICommandSender sender)
    {
        return "commands.editlootchests.usage";
    }

    @Override
    public void execute(MinecraftServer server, ICommandSender sender, String[] args) throws CommandException
    {
        World world = sender.getEntityWorld();
        if(world.provider.getDimension() == 0)
        {
            LootChestManager.get(world).generateChests(world);
        }
    }
}
