package com.cubgdev.cubga.init;

import com.cubgdev.cubga.block.BlockMansionShowcase;
import com.cubgdev.cubga.item.ItemMansionShowcase;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.item.ItemBlock;

public class ModBlocks
{
        public static final Block MANSION_BOTTOM_1;
        public static final Block MANSION_BOTTOM_2;
        public static final Block MANSION_BOTTOM_3;
        public static final Block MANSION_BOTTOM_4;
        public static final Block MANSION_BOTTOM_5;
        public static final Block MANSION_BOTTOM_6;
        public static final Block MANSION_BOTTOM_7;
        public static final Block MANSION_BOTTOM_8;
        public static final Block MANSION_BOTTOM_9;
        public static final Block MANSION_BOTTOMCENTER_1;
        public static final Block MANSION_BOTTOMCENTER_2;
        public static final Block MANSION_BOTTOMCENTER_3;
        public static final Block MANSION_BOTTOMCENTER_4;
        public static final Block MANSION_BOTTOMCENTER_5;
        public static final Block MANSION_BOTTOMCENTER_6;
        public static final Block MANSION_BOTTOMCENTER_7;
        public static final Block MANSION_BOTTOMCENTER_8;
        public static final Block MANSION_BOTTOMCENTER_9;

        static
        {
            MANSION_BOTTOM_1 = new BlockMansionShowcase("mansion_bottom_1");
            MANSION_BOTTOM_2 = new BlockMansionShowcase("mansion_bottom_2");
            MANSION_BOTTOM_3 = new BlockMansionShowcase("mansion_bottom_3");
            MANSION_BOTTOM_4 = new BlockMansionShowcase("mansion_bottom_4");
            MANSION_BOTTOM_5 = new BlockMansionShowcase("mansion_bottom_5");
            MANSION_BOTTOM_6 = new BlockMansionShowcase("mansion_bottom_6");
            MANSION_BOTTOM_7 = new BlockMansionShowcase("mansion_bottom_7");
            MANSION_BOTTOM_8 = new BlockMansionShowcase("mansion_bottom_8");
            MANSION_BOTTOM_9 = new BlockMansionShowcase("mansion_bottom_9");
            MANSION_BOTTOMCENTER_1 = new BlockMansionShowcase("mansion_bottomcenter_1");
            MANSION_BOTTOMCENTER_2 = new BlockMansionShowcase("mansion_bottomcenter_2");
            MANSION_BOTTOMCENTER_3 = new BlockMansionShowcase("mansion_bottomcenter_3");
            MANSION_BOTTOMCENTER_4 = new BlockMansionShowcase("mansion_bottomcenter_4");
            MANSION_BOTTOMCENTER_5 = new BlockMansionShowcase("mansion_bottomcenter_5");
            MANSION_BOTTOMCENTER_6 = new BlockMansionShowcase("mansion_bottomcenter_6");
            MANSION_BOTTOMCENTER_7 = new BlockMansionShowcase("mansion_bottomcenter_7");
            MANSION_BOTTOMCENTER_8 = new BlockMansionShowcase("mansion_bottomcenter_8");
            MANSION_BOTTOMCENTER_9 = new BlockMansionShowcase("mansion_bottomcenter_9");
        }

        public static void register()
        {
            registerBlock(MANSION_BOTTOM_1, new ItemMansionShowcase(MANSION_BOTTOM_1));
            registerBlock(MANSION_BOTTOM_2, new ItemMansionShowcase(MANSION_BOTTOM_2));
            registerBlock(MANSION_BOTTOM_3, new ItemMansionShowcase(MANSION_BOTTOM_3));
            registerBlock(MANSION_BOTTOM_4, new ItemMansionShowcase(MANSION_BOTTOM_4));
            registerBlock(MANSION_BOTTOM_5, new ItemMansionShowcase(MANSION_BOTTOM_5));
            registerBlock(MANSION_BOTTOM_6, new ItemMansionShowcase(MANSION_BOTTOM_6));
            registerBlock(MANSION_BOTTOM_7, new ItemMansionShowcase(MANSION_BOTTOM_7));
            registerBlock(MANSION_BOTTOM_8, new ItemMansionShowcase(MANSION_BOTTOM_8));
            registerBlock(MANSION_BOTTOM_9, new ItemMansionShowcase(MANSION_BOTTOM_9));
            registerBlock(MANSION_BOTTOMCENTER_1, new ItemMansionShowcase(MANSION_BOTTOMCENTER_1));
            registerBlock(MANSION_BOTTOMCENTER_2, new ItemMansionShowcase(MANSION_BOTTOMCENTER_2));
            registerBlock(MANSION_BOTTOMCENTER_3, new ItemMansionShowcase(MANSION_BOTTOMCENTER_3));
            registerBlock(MANSION_BOTTOMCENTER_4, new ItemMansionShowcase(MANSION_BOTTOMCENTER_4));
            registerBlock(MANSION_BOTTOMCENTER_5, new ItemMansionShowcase(MANSION_BOTTOMCENTER_5));
            registerBlock(MANSION_BOTTOMCENTER_6, new ItemMansionShowcase(MANSION_BOTTOMCENTER_6));
            registerBlock(MANSION_BOTTOMCENTER_7, new ItemMansionShowcase(MANSION_BOTTOMCENTER_7));
            registerBlock(MANSION_BOTTOMCENTER_8, new ItemMansionShowcase(MANSION_BOTTOMCENTER_8));
            registerBlock(MANSION_BOTTOMCENTER_9, new ItemMansionShowcase(MANSION_BOTTOMCENTER_9));
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
