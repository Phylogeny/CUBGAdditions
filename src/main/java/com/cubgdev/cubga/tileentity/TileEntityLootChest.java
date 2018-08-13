package com.cubgdev.cubga.tileentity;

import com.cubgdev.cubga.common.LootChest;
import com.google.common.base.Strings;
import com.google.common.collect.Lists;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.server.SPacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntityChest;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.EnumSkyBlock;
import net.minecraftforge.common.util.Constants;

import java.util.List;
import java.util.Map;
import java.util.Random;

/**
 * Author: MrCrayfish
 */
public class TileEntityLootChest extends TileEntityChest implements IValueContainer
{
    private static final Random RAND = new Random(System.currentTimeMillis());

    private LootChest lootChest = new LootChest();
    private boolean needLightingUpdate = false;

    public TileEntityLootChest() {}

    public TileEntityLootChest(LootChest lootChest)
    {
        this.lootChest = lootChest;
        this.needLightingUpdate = true;
    }

    @Override
    public void update()
    {
        super.update();
        if(needLightingUpdate)
        {
            world.checkLightFor(EnumSkyBlock.BLOCK, pos);
            needLightingUpdate = false;
        }
        if(lootChest.isGlowing())
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
        if(compound.hasKey("LootChest", Constants.NBT.TAG_COMPOUND))
        {
            lootChest = new LootChest(compound.getCompoundTag("LootChest"));
        }
        needLightingUpdate = true;
    }

    @Override
    public NBTTagCompound writeToNBT(NBTTagCompound compound)
    {
        super.writeToNBT(compound);
        if(lootChest != null)
        {
            compound.setTag("LootChest", lootChest.toTag());
        }
        return compound;
    }

    public LootChest getLootChest()
    {
        return lootChest;
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

    @Override
    public List<Entry> getEntries()
    {
        List<Entry> entries = Lists.newArrayList();
        entries.add(new Entry("LootTable", "Loot Table", Entry.Type.TEXT_FIELD, lootChest.getLootTable()));
        entries.add(new Entry("Color", "Color", Entry.Type.TEXT_FIELD, lootChest.getColor()));
        entries.add(new Entry("ChestTexture", "Chest Texture", Entry.Type.TEXT_FIELD, lootChest.getChestTexture()));
        entries.add(new Entry("Glowing", "Glowing", Entry.Type.TOGGLE, lootChest.isGlowing()));
        return entries;
    }

    @Override
    public void updateEntries(Map<String, String> entries)
    {
        String lootTable = entries.get("LootTable");
        if(!Strings.isNullOrEmpty(lootTable))
        {
            lootChest.setLootTable(new ResourceLocation(lootTable));
        }

        String chestTexture = entries.get("ChestTexture");
        if(!Strings.isNullOrEmpty(chestTexture))
        {
            lootChest.setChestTexture(new ResourceLocation(chestTexture));
        }
        else
        {
            lootChest.setChestTexture(null);
        }

        lootChest.setColor(Integer.parseInt(entries.get("Color")));
        lootChest.setGlowing(Boolean.valueOf(entries.get("Glowing")));
    }

    public void resetLootTable()
    {
        this.lootTable = lootChest.getLootTable();
    }

    public void setLootChest(LootChest lootChest)
    {
        this.lootChest = lootChest;
    }
}
