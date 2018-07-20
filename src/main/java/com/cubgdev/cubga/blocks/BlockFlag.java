package com.cubgdev.cubga.blocks;

import com.cubgdev.cubga.CUBG;
import com.cubgdev.cubga.init.ModBlocks;
import com.cubgdev.cubga.utils.aabb.Bounds;
import com.google.common.collect.Lists;
import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyBool;
import net.minecraft.block.properties.PropertyInteger;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.EnumDyeColor;
import net.minecraft.item.ItemAppleGold;
import net.minecraft.item.ItemDye;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.NonNullList;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.Mod;

import java.util.List;

public class BlockFlag extends Block
{
    public static final PropertyInteger COLOUR = PropertyInteger.create("colour", 0, 15);
    public static final PropertyBool UP = PropertyBool.create("up");
    public static final PropertyBool DOWN = PropertyBool.create("down");

    private static final Bounds bounds = new Bounds(5, 16, 5, 11, 0, 11);

    public BlockFlag(String name) {
        super(Material.ROCK);
        this.setUnlocalizedName(name);
        this.setRegistryName(name);
        this.setCreativeTab(CUBG.TAB);
        this.setDefaultState(this.blockState.getBaseState().withProperty(COLOUR, 0));
    }

    @Override
    public boolean isOpaqueCube(IBlockState state)
    {
        return false;
    }

    @Override
    public boolean isFullCube(IBlockState state)
    {
        return false;
    }

    @Override
    public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess world, BlockPos pos) {
        return bounds.toAABB();
    }

    @Override
    public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
        ItemStack heldItem = playerIn.getHeldItem(hand);
        if (!heldItem.isEmpty()) {
            if (heldItem.getItem() instanceof ItemDye) {
                worldIn.setBlockState(pos, state.withProperty(COLOUR, 15 - heldItem.getItemDamage()));
                if (!playerIn.isCreative()) heldItem.shrink(1);
                return true;
            }
        }
        return true;
    }

    @Override
    public IBlockState getActualState(IBlockState state, IBlockAccess worldIn, BlockPos pos)
    {
        boolean up = worldIn.getBlockState(pos.up()).getBlock() instanceof BlockFlag;
        boolean down = worldIn.getBlockState(pos.down()).getBlock() instanceof BlockFlag;
        return state.withProperty(UP, up).withProperty(DOWN, down);
    }

    @Override
    public IBlockState getStateFromMeta(int meta)
    {
        return getDefaultState().withProperty(COLOUR, meta);
    }

    @Override
    public int getMetaFromState(IBlockState state)
    {
        return ((Integer) state.getValue(COLOUR)).intValue();
    }

    @Override
    protected BlockStateContainer createBlockState()
    {
        return new BlockStateContainer(this, new IProperty[]{ COLOUR, UP, DOWN});
    }

    @Override
    public IBlockState getStateForPlacement(World world, BlockPos pos, EnumFacing facing, float hitX, float hitY, float hitZ, int meta, EntityLivingBase placer, EnumHand hand)
    {
        return super.getStateForPlacement(world, pos, facing, hitX, hitY, hitZ, meta, placer, hand).withProperty(COLOUR, Math.min(meta, 15));
    }

    @Override
    public void getDrops(NonNullList<ItemStack> drops, IBlockAccess world, BlockPos pos, IBlockState state, int fortune)
    {
        drops.add(new ItemStack(ModBlocks.FLAG, 1, Math.min(state.getValue(COLOUR), 15)));
    }

    @Override
    public ItemStack getPickBlock(IBlockState state, RayTraceResult target, World world, BlockPos pos, EntityPlayer player)
    {
        return new ItemStack(ModBlocks.FLAG, 1, state.getValue(COLOUR));
    }
}
