package com.cubgdev.cubga.blocks;

import com.cubgdev.cubga.init.ModBlocks;

import net.minecraft.block.Block;
import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.Material;

public class BlockBase extends Block {

	public BlockBase(Material material) {
		this(material, material.getMaterialMapColor());
	}

	public BlockBase(Material material, MapColor color) {
		super(material, color);
	}

	public BlockBase(Material material, String name) {
		this(material, name, name);
	}

	public BlockBase(Material material, String registryName, String unlocalizedName) {
		super(material, material.getMaterialMapColor());
		this.setRegistryName(registryName);
		this.setUnlocalizedName(unlocalizedName);
		ModBlocks.registerBlock(this);
	}

	public BlockBase(Material material, MapColor color, String name) {
		this(material, color, name, name);
	}

	public BlockBase(Material material, MapColor color, String registryName, String unlocalizedName) {
		super(material, color);
		this.setRegistryName(registryName);
		this.setUnlocalizedName(unlocalizedName);
		ModBlocks.registerBlock(this);
	}
}