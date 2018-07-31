package com.cubgdev.cubga.tileentity;

import com.google.common.base.Strings;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.server.SPacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntityChest;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.ITickable;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.EnumSkyBlock;
import net.minecraftforge.common.util.Constants;

import javax.annotation.Resource;
import java.util.*;

/**
 * Author: MrCrayfish
 */
public class TileEntityLootChest extends TileEntityChest implements IValueContainer
{
    private int color = 16750848;
    private ResourceLocation chestTexture;
    private boolean glowing;
    private boolean needLightingUpdate = false;

    private static final Random RAND = new Random(System.currentTimeMillis());

    @Override
    public void update()
    {
        super.update();
        if(needLightingUpdate)
        {
            world.checkLightFor(EnumSkyBlock.BLOCK, pos);
            needLightingUpdate = false;
        }
        if(glowing)
        {
            double posX = pos.getX() - 0.5 + RAND.nextDouble() * 2.0;
            double posY = pos.getY() + RAND.nextDouble() * 1.5;
            double posZ = pos.getZ() - 0.5 + RAND.nextDouble() * 2.0;
            world.spawnParticle(EnumParticleTypes.CRIT, posX, posY, posZ, 0, 0, 0);
        }
    }

    @Override
    public void readFromNBT(NBTTagCompound compound)
    {
        super.readFromNBT(compound);
        needLightingUpdate = true;
        chestTexture = null;
        if(compound.hasKey("ChestTexture", Constants.NBT.TAG_STRING))
        {
            chestTexture = new ResourceLocation(compound.getString("ChestTexture"));
        }
        if(compound.hasKey("Color", Constants.NBT.TAG_INT_ARRAY))
        {
            int[] c = compound.getIntArray("Color");
            if(c.length == 3)
            {
                color = ((c[0] & 0xFF) << 16) | ((c[1] & 0xFF) << 8) | ((c[2] & 0xFF));
            }
        }
        if(compound.hasKey("Glowing", Constants.NBT.TAG_BYTE))
        {
            glowing = compound.getBoolean("Glowing");
        }
    }

    @Override
    public NBTTagCompound writeToNBT(NBTTagCompound compound)
    {
        super.writeToNBT(compound);
        if(chestTexture != null)
        {
            compound.setString("ChestTexture", chestTexture.toString());
        }
        compound.setIntArray("Color", this.getColor());
        compound.setBoolean("Glowing", glowing);
        return compound;
    }

    public ResourceLocation getChestTexture()
    {
        return chestTexture;
    }

    public void setChestTexture(ResourceLocation chestTexture)
    {
        this.chestTexture = chestTexture;
    }

    public void setColor(int[] color)
    {
        this.color = ((color[0] & 0xFF) << 16) | ((color[1] & 0xFF) << 8) | ((color[2] & 0xFF));
    }

    public int[] getColor()
    {
        return new int[]{ (color >> 16) & 0xFF, (color >> 8) & 0xFF, color & 0xFF };
    }

    @Override
    public void onDataPacket(NetworkManager net, SPacketUpdateTileEntity pkt)
    {
        this.readFromNBT(pkt.getNbtCompound());
    }

    @Override
    public final NBTTagCompound getUpdateTag()
    {
        return this.writeToNBT(new NBTTagCompound());
    }

    @Override
    public SPacketUpdateTileEntity getUpdatePacket()
    {
        return new SPacketUpdateTileEntity(pos, 0, getUpdateTag());
    }

    public boolean isGlowing()
    {
        return glowing;
    }

    @Override
    public List<Entry> getEntries()
    {
        List<Entry> entries = Lists.newArrayList();
        entries.add(new Entry("LootTable", "Loot Table", Entry.Type.TEXT_FIELD, lootTable));
        entries.add(new Entry("Color", "Color", Entry.Type.TEXT_FIELD, color));
        entries.add(new Entry("ChestTexture", "Chest Texture", Entry.Type.TEXT_FIELD, chestTexture));
        entries.add(new Entry("Glowing", "Glowing", Entry.Type.TOGGLE, glowing));
        return entries;
    }

    @Override
    public void updateEntries(Map<String, String> entries)
    {
        String lootTable = entries.get("LootTable");
        if(!Strings.isNullOrEmpty(lootTable))
        {
            this.lootTable = new ResourceLocation(lootTable);
        }

        String chestTexture = entries.get("ChestTexture");
        if(!Strings.isNullOrEmpty(chestTexture))
        {
            this.chestTexture = new ResourceLocation(chestTexture);
        }
        else
        {
            this.chestTexture = null;
        }

        this.color = Integer.parseInt(entries.get("Color"));
        this.glowing = Boolean.valueOf(entries.get("Glowing"));
    }
}
