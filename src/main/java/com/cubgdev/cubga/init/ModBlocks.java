package com.cubgdev.cubga.init;

import com.cubgdev.cubga.blocks.*;
import com.cubgdev.cubga.item.ItemBlockBase;
import com.cubgdev.cubga.item.ItemBrittleBrick;

import com.cubgdev.cubga.item.ItemFlag;
import com.cubgdev.cubga.item.ItemPlayerPlush;
import net.minecraft.block.Block;
import net.minecraft.item.ItemBlock;

public class ModBlocks
{

	public static final Block BRITTLE_BRICK;
	public static final Block PLAYER_PLUSH;

	public static final Block CRYSTAL_CONTAINER;
	public static final Block CRYSTAL;

	public static final Block FLAG;

    public static final Block LOOT_CHEST;


	static
	{
		BRITTLE_BRICK = new BlockBrittleBrick("brittle_brick");
		PLAYER_PLUSH = new BlockPlayerPlush();

		CRYSTAL_CONTAINER = new BlockCrystalContainer("crystal_container");
		CRYSTAL = new BlockCrystal("crystal");

		FLAG = new BlockFlag("flag");

        LOOT_CHEST = new BlockLootChest();

	}

	public static void register()
	{
		registerBlock(BRITTLE_BRICK, new ItemBrittleBrick(BRITTLE_BRICK));
		// registerBlock(PLAYER_PLUSH, new ItemPlayerPlush());

		registerBlock(CRYSTAL_CONTAINER);
		registerBlock(CRYSTAL);

		registerBlock(FLAG, new ItemFlag(FLAG));

        registerBlock(LOOT_CHEST);
	}

	public static void registerBlock(Block block)
	{
		RegistrationHandler.Blocks.add(block);
		ItemBlock itemBlock = (ItemBlock) new ItemBlockBase(block).setRegistryName(block.getRegistryName());
		RegistrationHandler.Items.add(itemBlock);
	}

	public static void registerBlock(Block block, ItemBlock item)
	{
		if (block.getRegistryName() == null)
			throw new IllegalArgumentException("A block being registered does not have a registry name and could be successfully registered.");

		RegistrationHandler.Blocks.add(block);
		item.setRegistryName(block.getRegistryName());
		RegistrationHandler.Items.add(item);
	}
}
