package com.cubgdev.cubga.common;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.util.Constants;

import javax.annotation.Nullable;

/**
 * Author: MrCrayfish
 */
public class LootChest
{
    private ResourceLocation lootTable;
    private int color = 16750848;
    private ResourceLocation chestTexture;
    private boolean glowing;
    private float chance = 1.0F;
    private EnumFacing facing = EnumFacing.NORTH;

    public LootChest() {}

    public LootChest(NBTTagCompound tagCompound)
    {
        this.fromTag(tagCompound);
    }

    @Nullable
    public ResourceLocation getLootTable()
    {
        return lootTable;
    }

    public void setLootTable(ResourceLocation lootTable)
    {
        this.lootTable = lootTable;
    }

    public int getColor()
    {
        return color;
    }

    public int[] getRGBColor()
    {
        return new int[]{ (color >> 16) & 0xFF, (color >> 8) & 0xFF, color & 0xFF };
    }

    public void setColor(int color)
    {
        this.color = color;
    }

    public void setColor(int[] color)
    {
        if(color.length == 3)
        {
            this.color = ((color[0] & 0xFF) << 16) | ((color[1] & 0xFF) << 8) | ((color[2] & 0xFF));
        }
    }

    @Nullable
    public ResourceLocation getChestTexture()
    {
        return chestTexture;
    }

    public void setChestTexture(ResourceLocation chestTexture)
    {
        this.chestTexture = chestTexture;
    }

    public boolean isGlowing()
    {
        return glowing;
    }

    public void setGlowing(boolean glowing)
    {
        this.glowing = glowing;
    }

    public EnumFacing getFacing()
    {
        return facing;
    }

    public void setFacing(EnumFacing facing)
    {
        this.facing = facing;
    }

    public float getChance()
    {
        return chance;
    }

    public void setChance(float chance)
    {
        this.chance = chance;
    }

    public NBTTagCompound toTag()
    {
        NBTTagCompound tagCompound = new NBTTagCompound();
        if(lootTable != null)
        {
            tagCompound.setString("LootTable", lootTable.toString());
        }
        tagCompound.setInteger("Color", color);
        if(chestTexture != null)
        {
            tagCompound.setString("ChestTexture", chestTexture.toString());
        }
        tagCompound.setBoolean("Glowing", glowing);
        if(facing != null)
        {
            tagCompound.setInteger("Facing", facing.getHorizontalIndex());
        }
        tagCompound.setFloat("Chance", chance);
        return tagCompound;
    }

    private void fromTag(NBTTagCompound tagCompound)
    {
        if(tagCompound.hasKey("LootTable", Constants.NBT.TAG_STRING))
        {
            lootTable = new ResourceLocation(tagCompound.getString("LootTable"));
        }
        if(tagCompound.hasKey("Color", Constants.NBT.TAG_INT_ARRAY))
        {
            this.setColor(tagCompound.getIntArray("Color"));
        }
        else
        {
            this.color = tagCompound.getInteger("Color");
        }
        if(tagCompound.hasKey("ChestTexture", Constants.NBT.TAG_STRING))
        {
            chestTexture = new ResourceLocation(tagCompound.getString("ChestTexture"));
        }
        glowing = tagCompound.getBoolean("Glowing");
        if(tagCompound.hasKey("Facing", Constants.NBT.TAG_INT))
        {
            facing = EnumFacing.getHorizontal(tagCompound.getInteger("Facing"));
        }
        if(tagCompound.hasKey("Chance", Constants.NBT.TAG_FLOAT))
        {
            chance = tagCompound.getFloat("Chance");
        }
    }
}
