package com.cubgdev.cubga.common;

import com.cubgdev.cubga.Reference;
import com.cubgdev.cubga.init.ModBlocks;
import com.cubgdev.cubga.tileentity.TileEntityLootChest;
import com.google.common.collect.Maps;
import net.minecraft.block.BlockChest;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.storage.MapStorage;
import net.minecraft.world.storage.WorldSavedData;
import net.minecraftforge.common.util.Constants;

import java.util.Map;
import java.util.Random;

/**
 * Author: MrCrayfish
 */
public final class LootChestManager extends WorldSavedData
{
    private static final String DATA_NAME = Reference.MOD_ID + "_LootChests";

    public static LootChestManager get(World world)
    {
        MapStorage storage = world.getPerWorldStorage();
        LootChestManager instance = (LootChestManager) storage.getOrLoadData(LootChestManager.class, DATA_NAME);
        if (instance == null)
        {
            instance = new LootChestManager();
            storage.setData(DATA_NAME, instance);
        }
        return instance;
    }

    private final Map<BlockPos, LootChest> LOOT_CHESTS = Maps.newHashMap();

    public LootChestManager()
    {
        super(DATA_NAME);
    }

    public LootChestManager(String name)
    {
        super(name);
    }

    public void generateChests(World world)
    {
        Random rand = new Random(System.currentTimeMillis());
        LOOT_CHESTS.forEach((pos, lootChest) ->
        {
            world.setBlockToAir(pos);
            float f = rand.nextFloat();
            if(f < lootChest.getChance())
            {
                world.setBlockState(pos, ModBlocks.LOOT_CHEST.getDefaultState().withProperty(BlockChest.FACING, lootChest.getFacing()), 3);
                TileEntityLootChest tileEntityLootChest = new TileEntityLootChest(lootChest);
                tileEntityLootChest.resetLootTable();
                world.setTileEntity(pos, tileEntityLootChest);
            }
        });
    }

    public void setEditing(World world, boolean editing)
    {
        if(editing)
        {
            LOOT_CHESTS.forEach((pos, lootChest) ->
            {
                world.setBlockState(pos, ModBlocks.LOOT_CHEST.getDefaultState().withProperty(BlockChest.FACING, lootChest.getFacing()), 3);
                world.setTileEntity(pos, new TileEntityLootChest(lootChest));
            });
        }
        else
        {
            LOOT_CHESTS.forEach((pos, lootChest) -> world.setBlockToAir(pos));
        }
    }

    public void add(BlockPos pos, LootChest lootChest)
    {
        LOOT_CHESTS.put(pos, lootChest);
        this.markDirty();
    }

    public void remove(BlockPos pos)
    {
        LOOT_CHESTS.remove(pos);
        this.markDirty();
    }

    @Override
    public void readFromNBT(NBTTagCompound nbt)
    {
        LOOT_CHESTS.clear();
        NBTTagList tagList = nbt.getTagList("LootChests", Constants.NBT.TAG_COMPOUND);
        for(int i = 0; i < tagList.tagCount(); i++)
        {
            NBTTagCompound tagCompound = tagList.getCompoundTagAt(i);
            BlockPos pos = BlockPos.fromLong(tagCompound.getLong("Pos"));
            LootChest lootChest = new LootChest(tagCompound.getCompoundTag("Data"));
            LOOT_CHESTS.put(pos, lootChest);
        }
    }

    @Override
    public NBTTagCompound writeToNBT(NBTTagCompound compound)
    {
        NBTTagList tagList = new NBTTagList();
        LOOT_CHESTS.forEach((pos, lootChest) ->
        {
            NBTTagCompound lootChestTag = new NBTTagCompound();
            lootChestTag.setLong("Pos", pos.toLong());
            lootChestTag.setTag("Data", lootChest.toTag());
            tagList.appendTag(lootChestTag);
        });
        compound.setTag("LootChests", tagList);
        return compound;
    }
}
