package com.cubgdev.cubga.blocks;

import com.cubgdev.cubga.init.ModBlocks;

import net.minecraft.block.Block;
import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.Material;

public class PlushBase extends Block {

	public PlushBase(Material material) {
		this(material, material.getMaterialMapColor());
	}

	public PlushBase(Material material, MapColor color) {
		super(material, color);
	}

	public PlushBase(Material material, String name) {
		this(material, name, name);
	}

	public PlushBase(Material material, String registryName, String unlocalizedName) {
		super(material, material.getMaterialMapColor());
		this.setRegistryName(registryName);
		this.setUnlocalizedName(unlocalizedName);
		ModBlocks.registerBlock(this);
	}
}