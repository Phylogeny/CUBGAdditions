package com.cubgdev.cubga.init;

import com.cubgdev.cubga.item.*;

import net.minecraft.item.Item;

public class ModItems
{

	public static final Item MEDKIT;
	public static final Item FIRST_AID;
	public static final Item BANDAGE;
	public static final Item BANDAID;
	public static final Item PAINKILLERS;

	public static final Item COFFEE_CUP_FULL;
	public static final Item COFFEE_CUP;
	public static final Item MODDER_NIGHTMARE;
	public static final Item MASTER_GAUNTLET;
	public static final Item CRAYFISH_CLAW;

	public static final Item BRICK;

	public static final Item BEAM_ROD;
	public static final Item CONTAINER_EDITOR;

	static
	{
		MEDKIT = new ItemHeal("medkit", 64, 20);
		FIRST_AID = new ItemHeal("first_aid_kit", 32, 10);
		BANDAGE = new ItemHeal("bandage", 24, 4);
		BANDAID = new ItemHeal("bandaid", 1, 1);
		PAINKILLERS = new ItemRegen("painkillers",16);

		COFFEE_CUP_FULL = new ItemCoffee(true);
		COFFEE_CUP = new ItemCoffee(false);
		MODDER_NIGHTMARE = new ItemSharp("modder_nightmare");
		MASTER_GAUNTLET = new ItemMasterGauntlet();
		CRAYFISH_CLAW = new ItemSharp("crayfish_claw");

		BRICK = new ItemBrick("brick");

		BEAM_ROD = new ItemBeamRod();
		CONTAINER_EDITOR = new ItemValueContainerEditor();
	}

	public static void register()
	{
		register(MEDKIT);
		register(FIRST_AID);
		register(BANDAGE);
		register(BANDAID);
		register(PAINKILLERS);

		register(COFFEE_CUP_FULL);
		register(COFFEE_CUP);
		register(MODDER_NIGHTMARE);
		register(MASTER_GAUNTLET);
		register(CRAYFISH_CLAW);

		register(BRICK);
		
		register(BEAM_ROD);
		register(CONTAINER_EDITOR);
	}

	private static void register(Item item)
	{
		RegistrationHandler.Items.add(item);
	}
}