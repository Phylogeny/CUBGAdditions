package com.cubgdev.cubga.init;

import com.cubgdev.cubga.blocks.BlockBrittleBrick;
import com.cubgdev.cubga.blocks.BlockPlayerPlush;
import com.cubgdev.cubga.item.ItemBrittleBrick;
import com.cubgdev.cubga.item.ItemPlayerPlush;
import net.minecraft.block.Block;
import net.minecraft.item.ItemBlock;

public class ModBlocks
{
    public static final Block BRITTLE_BRICK;
    public static final Block PLAYER_PLUSH;

    static
    {
        BRITTLE_BRICK = new BlockBrittleBrick("brittle_brick");
        PLAYER_PLUSH = new BlockPlayerPlush();
    }

    public static void register()
    {
        registerBlock(BRITTLE_BRICK, new ItemBrittleBrick(BRITTLE_BRICK));
        registerBlock(PLAYER_PLUSH, new ItemPlayerPlush());
    }

    public static void registerBlock(Block block)
    {
        registerBlock(block, new ItemBlock(block));
        registerBlock(block, new ItemBlock(block));
    }

    public static void registerBlock(Block block, ItemBlock item)
    {
        if(block.getRegistryName() == null)
            throw new IllegalArgumentException("A block being registered does not have a registry name and could be successfully registered.");

        RegistrationHandler.Blocks.add(block);
        item.setRegistryName(block.getRegistryName());
        RegistrationHandler.Items.add(item);
    }
}
