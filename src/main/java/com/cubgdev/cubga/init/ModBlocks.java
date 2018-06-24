package com.cubgdev.cubga.init;

import com.cubgdev.cubga.blocks.BlockBrittleBrick;
import com.cubgdev.cubga.item.ItemBrittleBrick;
import net.minecraft.block.Block;
import net.minecraft.item.ItemBlock;

public class ModBlocks
{
    public static final Block BRITTLE_BRICK;

    static
    {
        BRITTLE_BRICK = new BlockBrittleBrick("brittle_brick");
    }

    public static void register()
    {
        registerBlock(BRITTLE_BRICK, new ItemBrittleBrick(BRITTLE_BRICK));
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
