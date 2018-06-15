package com.cubgdev.cubga.init;

import com.cubgdev.cubga.item.ItemMedKit;
import net.minecraft.item.Item;

public class ModItems
{
    public static final Item MEDKIT;

    static
    {
        MEDKIT = new ItemMedKit("medkit");
    }

    public static void register()
    {
        register(MEDKIT);
    }

    private static void register(Item item)
    {
        RegistrationHandler.Items.add(item);
    }
}