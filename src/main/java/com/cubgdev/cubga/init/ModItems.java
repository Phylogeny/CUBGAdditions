package com.cubgdev.cubga.init;

import com.cubgdev.cubga.item.ItemBase;
import com.cubgdev.cubga.item.ItemCoffee;
import com.cubgdev.cubga.item.ItemCoffeeEmpty;
import com.cubgdev.cubga.item.ItemHeal;

import net.minecraft.item.Item;

public class ModItems {
	
	public static final Item MEDKIT;
	public static final Item FIRST_AID;
	public static final Item BANDAGE;
	public static final Item BANDAID;

	public static final Item COFFEE_CUP_FULL;
	public static final Item COFFEE_CUP;

	static {
		MEDKIT = new ItemHeal("medkit", 64, 20);
		FIRST_AID = new ItemHeal("first_aid_kit", 32, 10);
		BANDAGE = new ItemHeal("bandage", 24, 4);
		BANDAID = new ItemHeal("bandaid", 1, 1);

		COFFEE_CUP_FULL = new ItemCoffee("coffee_cup_full", 32);
		COFFEE_CUP = new ItemCoffeeEmpty();
	}

	public static void register() {
		register(MEDKIT);
		register(FIRST_AID);
		register(BANDAGE);
		register(BANDAID);

		register(COFFEE_CUP_FULL);
		register(COFFEE_CUP);
	}

	private static void register(Item item) {
		RegistrationHandler.Items.add(item);
	}
}