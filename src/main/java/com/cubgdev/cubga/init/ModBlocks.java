package com.cubgdev.cubga.init;

import net.minecraft.block.Block;
import net.minecraft.item.ItemBlock;

public class ModBlocks
{
    static
    {

    }

    public static void register()
    {

    }

    private static void registerBlock(Block block)
    {
        registerBlock(block, new ItemBlock(block));
    }

    private static void registerBlock(Block block, ItemBlock item)
    {
        if(block.getRegistryName() == null)
            throw new IllegalArgumentException("A block being registered does not have a registry name and could be successfully registered.");

        RegistrationHandler.Blocks.add(block);
        item.setRegistryName(block.getRegistryName());
        RegistrationHandler.Items.add(item);
    }
}
