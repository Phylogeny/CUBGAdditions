package com.cubgdev.cubga.init;

import com.cubgdev.cubga.blocks.BlockBrittleBrick;
import com.cubgdev.cubga.blocks.BlockCrystal;
import com.cubgdev.cubga.blocks.BlockCrystalContainer;
import com.cubgdev.cubga.blocks.BlockPlayerPlush;
import com.cubgdev.cubga.item.ItemBrittleBrick;

import net.minecraft.block.Block;
import net.minecraft.item.ItemBlock;

public class ModBlocks
{
	public static final Block BRITTLE_BRICK;
	public static final Block PLAYER_PLUSH;

	public static final Block CRYSTAL_CONTAINER;
	public static final Block CRYSTAL;

	static
	{
		BRITTLE_BRICK = new BlockBrittleBrick("brittle_brick");
		PLAYER_PLUSH = new BlockPlayerPlush();

		CRYSTAL_CONTAINER = new BlockCrystalContainer("crystal_container");
		CRYSTAL = new BlockCrystal("crystal");
	}

	public static void register()
	{
		registerBlock(BRITTLE_BRICK, new ItemBrittleBrick(BRITTLE_BRICK));
		// registerBlock(PLAYER_PLUSH, new ItemPlayerPlush());

		registerBlock(CRYSTAL_CONTAINER);
		registerBlock(CRYSTAL);
	}

	public static void registerBlock(Block block)
	{
		RegistrationHandler.Blocks.add(block);
		ItemBlock itemBlock = (ItemBlock) new ItemBlock(block).setRegistryName(block.getRegistryName());
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
