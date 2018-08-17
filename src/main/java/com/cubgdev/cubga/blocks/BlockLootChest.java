package com.cubgdev.cubga.blocks;

import com.cubgdev.cubga.CUBG;
import com.cubgdev.cubga.common.LootChest;
import com.cubgdev.cubga.common.LootChestManager;
import com.cubgdev.cubga.init.ModBlocks;
import com.cubgdev.cubga.tileentity.TileEntityLootChest;
import net.minecraft.block.BlockChest;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.NonNullList;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.common.util.Constants;

import javax.annotation.Nullable;

/**
 * Author: MrCrayfish
 */
public class BlockLootChest extends BlockChest
{
    private static final int[] BLANK = new int[]{ 50, 50, 50 };
    private static final LootTableEntry[] STANDARD_TYPES = {
            new LootTableEntry(75, 83, 32, new ResourceLocation("crayfishunknown", "chests/nonstandard"), false, 0.9F),
            new LootTableEntry(114, 137, 218, new ResourceLocation("crayfishunknown", "chests/attachments"), false, 0.75F),
            new LootTableEntry(128, 128, 128, new ResourceLocation("crayfishunknown", "chests/standard"), false, 0.8F),
            new LootTableEntry(218, 165, 32, new ResourceLocation("crayfishunknown", "chests/rare"), true, 0.5F)
    };

    public BlockLootChest()
    {
        super(Type.BASIC);
        this.setUnlocalizedName("loot_chest");
        this.setRegistryName("loot_chest");
        this.setCreativeTab(CUBG.TAB);
    }

    @Override
    public int getLightValue(IBlockState state, IBlockAccess world, BlockPos pos)
    {
        TileEntity tileEntity = world.getTileEntity(pos);
        if(tileEntity instanceof TileEntityLootChest)
        {
            if(((TileEntityLootChest) tileEntity).getLootChest().isGlowing())
            {
                return 12;
            }
        }
        return 0;
    }

    @Override
    public void getSubBlocks(CreativeTabs itemIn, NonNullList<ItemStack> items)
    {
        for(LootTableEntry lootTableEntries : STANDARD_TYPES)
        {
            NBTTagCompound itemTag = new NBTTagCompound();
            NBTTagCompound blockEntityTag = new NBTTagCompound();
            NBTTagCompound lootChest = new NBTTagCompound();
            lootChest.setIntArray("Color", lootTableEntries.getColor());
            lootChest.setBoolean("Glowing", lootTableEntries.isGlowing());
            lootChest.setString("LootTable", String.valueOf(lootTableEntries.getLootTable()));
            lootChest.setFloat("Chance", lootTableEntries.getChance());
            blockEntityTag.setTag("LootChest", lootChest);
            itemTag.setTag("BlockEntityTag", blockEntityTag);
            ItemStack stack = new ItemStack(this);
            stack.setTagCompound(itemTag);
            items.add(stack);
        }
    }

    @Nullable
    public static ResourceLocation getChestTexture(ItemStack stack)
    {
        if(!stack.isEmpty() && stack.getItem() == Item.getItemFromBlock(ModBlocks.LOOT_CHEST))
        {
            NBTTagCompound itemTag = stack.getTagCompound();
            if(itemTag != null)
            {
                NBTTagCompound blockEntityTag = itemTag.getCompoundTag("BlockEntityTag");
                if(!blockEntityTag.hasNoTags())
                {
                    NBTTagCompound lootChest = blockEntityTag.getCompoundTag("LootChest");
                    if(!lootChest.hasNoTags() && lootChest.hasKey("ChestTexture", Constants.NBT.TAG_STRING))
                    {
                        return new ResourceLocation(lootChest.getString("ChestTexture"));
                    }
                }
            }
        }
        return null;
    }

    public static int[] getChestColor(ItemStack stack)
    {
        if(!stack.isEmpty() && stack.getItem() == Item.getItemFromBlock(ModBlocks.LOOT_CHEST))
        {
            NBTTagCompound itemTag = stack.getTagCompound();
            if(itemTag != null)
            {
                NBTTagCompound blockEntityTag = itemTag.getCompoundTag("BlockEntityTag");
                if(!blockEntityTag.hasNoTags())
                {
                    NBTTagCompound lootChest = blockEntityTag.getCompoundTag("LootChest");
                    if(!lootChest.hasNoTags() && lootChest.hasKey("Color", Constants.NBT.TAG_INT_ARRAY))
                    {
                        return lootChest.getIntArray("Color");
                    }
                }
            }
        }
        return BLANK;
    }

    public static LootChest getLootChest(ItemStack stack)
    {
        if(!stack.isEmpty() && stack.getItem() == Item.getItemFromBlock(ModBlocks.LOOT_CHEST))
        {
            NBTTagCompound itemTag = stack.getTagCompound();
            if(itemTag != null)
            {
                NBTTagCompound blockEntityTag = itemTag.getCompoundTag("BlockEntityTag");
                if(!blockEntityTag.hasNoTags())
                {
                    NBTTagCompound lootChest = blockEntityTag.getCompoundTag("LootChest");
                    return new LootChest(lootChest);
                }
            }
        }
        return new LootChest();
    }

    @Nullable
    @Override
    public TileEntity createTileEntity(World world, IBlockState state)
    {
        return new TileEntityLootChest();
    }

    @Override
    public void onBlockPlacedBy(World worldIn, BlockPos pos, IBlockState state, EntityLivingBase placer, ItemStack stack)
    {
        super.onBlockPlacedBy(worldIn, pos, state, placer, stack);
        TileEntity tileEntity = worldIn.getTileEntity(pos);
        if(tileEntity instanceof TileEntityLootChest)
        {
            TileEntityLootChest tileEntityLootChest = (TileEntityLootChest) tileEntity;
            tileEntityLootChest.setLootChest(BlockLootChest.getLootChest(stack));
            tileEntityLootChest.getLootChest().setFacing(placer.getHorizontalFacing().getOpposite());
            tileEntityLootChest.markDirty();

            if(!worldIn.isRemote && placer instanceof EntityPlayer && ((EntityPlayer) placer).dimension == 0)
            {
                LootChestManager.get(worldIn).add(pos, tileEntityLootChest.getLootChest());
            }
        }
    }

    @Override
    public void onBlockDestroyedByPlayer(World worldIn, BlockPos pos, IBlockState state)
    {
        if(!worldIn.isRemote && worldIn.provider.getDimension() == 0)
        {
            LootChestManager.get(worldIn).remove(pos);
        }
    }

    private static class LootTableEntry
    {
        private int[] color;
        private ResourceLocation lootTable;
        private boolean glowing;
        private float chance;

        public LootTableEntry(int r, int g, int b, ResourceLocation resource, boolean glowing, float chance)
        {
            this.color = new int[] { r, g, b };
            this.lootTable = resource;
            this.glowing = glowing;
            this.chance = chance;
        }

        public int[] getColor()
        {
            return color;
        }

        public ResourceLocation getLootTable()
        {
            return lootTable;
        }

        public boolean isGlowing()
        {
            return glowing;
        }

        public float getChance()
        {
            return chance;
        }
    }
}
