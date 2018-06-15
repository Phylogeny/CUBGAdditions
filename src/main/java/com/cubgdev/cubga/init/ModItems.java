package com.cubgdev.cubga.init;

import com.cubgdev.cubga.item.ItemHeal;

import net.minecraft.item.Item;

public class ModItems {
	
	public static final Item MEDKIT;
	public static final Item FIRST_AID;
	public static final Item BANDAGE;

	static {
		MEDKIT = new ItemHeal("medkit", 64, 20);
		FIRST_AID = new ItemHeal("first_aid_kit", 64, 10);
		BANDAGE = new ItemHeal("bandage", 64, 1);
	}

	public static void register() {
		register(MEDKIT);
		register(FIRST_AID);
		register(BANDAGE);
	}

	private static void register(Item item) {
		RegistrationHandler.Items.add(item);
	}
}