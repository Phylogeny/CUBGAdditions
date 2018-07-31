package com.cubgdev.cubga.blocks;

import com.cubgdev.cubga.CUBG;
import com.cubgdev.cubga.Reference;
import com.cubgdev.cubga.init.ModBlocks;
import com.cubgdev.cubga.tileentity.TileEntityLootChest;
import net.minecraft.block.Block;
import net.minecraft.block.BlockChest;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityChest;
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
public class BlockLootChest extends BlockChest {

    private static final int[] BLANK = new int[] { 50, 50, 50 };
    private static final int[][] STANDARD_TYPES = {
            new int[] { 114, 137, 0   },
            new int[] { 114, 137, 218 },
            new int[] { 255, 232, 0   }
    };

    public BlockLootChest() {
        super(Type.BASIC);
        this.setUnlocalizedName("loot_chest");
        this.setRegistryName("loot_chest");
        this.setCreativeTab(CUBG.TAB);
    }

    @Override
    public int getLightValue(IBlockState state, IBlockAccess world, BlockPos pos) {
        TileEntity tileEntity = world.getTileEntity(pos);
        if(tileEntity instanceof TileEntityLootChest) {
            if(((TileEntityLootChest) tileEntity).isGlowing()) {
                return 12;
            }
        }
        return 0;
    }

    @Override
    public void getSubBlocks(CreativeTabs itemIn, NonNullList<ItemStack> items) {
        for(int[] array : STANDARD_TYPES) {
            NBTTagCompound itemTag = new NBTTagCompound();
            NBTTagCompound blockEntityTag = new NBTTagCompound();
            blockEntityTag.setIntArray("Color", array);
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
                    if(blockEntityTag.hasKey("ChestTexture", Constants.NBT.TAG_STRING))
                    {
                        return new ResourceLocation(blockEntityTag.getString("ChestTexture"));
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
                    if(blockEntityTag.hasKey("Color", Constants.NBT.TAG_INT_ARRAY))
                    {
                        return blockEntityTag.getIntArray("Color");
                    }
                }
            }
        }
        return BLANK;
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
        if (tileEntity instanceof TileEntityLootChest)
        {
            int[] color = BlockLootChest.getChestColor(stack);
            ((TileEntityLootChest) tileEntity).setColor(color);
        }
    }
}
