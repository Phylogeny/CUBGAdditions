package com.cubgdev.cubga.common;

import com.cubgdev.cubga.Reference;
import com.cubgdev.cubga.init.ModBlocks;
import com.cubgdev.cubga.tileentity.TileEntityLootChest;
import com.google.common.collect.Maps;
import net.minecraft.block.BlockChest;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.storage.MapStorage;
import net.minecraft.world.storage.WorldSavedData;

import java.util.Map;

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
        LOOT_CHESTS.forEach((pos, lootChest) ->
        {
            world.setBlockState(pos, ModBlocks.LOOT_CHEST.getDefaultState().withProperty(BlockChest.FACING, lootChest.getFacing()), 3);
            TileEntityLootChest tileEntityLootChest = new TileEntityLootChest(lootChest);
            tileEntityLootChest.resetLootTable();
            world.setTileEntity(pos, tileEntityLootChest);
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
    }

    public void remove(BlockPos pos)
    {
        LOOT_CHESTS.remove(pos);
    }

    @Override
    public void readFromNBT(NBTTagCompound nbt)
    {

    }

    @Override
    public NBTTagCompound writeToNBT(NBTTagCompound compound)
    {
        return null;
    }
}
