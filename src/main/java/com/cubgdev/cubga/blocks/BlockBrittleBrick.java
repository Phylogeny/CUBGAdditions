package com.cubgdev.cubga.blocks;

import com.cubgdev.cubga.CUBG;
import com.cubgdev.cubga.common.EnumParticles;
import com.cubgdev.cubga.init.ModItems;
import com.cubgdev.cubga.network.PacketHandler;
import com.cubgdev.cubga.network.message.MessageParticle;
import com.cubgdev.cubga.tileentity.TileEntityBrittleBrick;
import com.mrcrayfish.guns.entity.EntityProjectile;
import com.mrcrayfish.guns.interfaces.IDamageable;
import net.minecraft.block.Block;
import net.minecraft.block.BlockTallGrass;
import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyInteger;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.NonNullList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.Explosion;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.network.NetworkRegistry;

import javax.annotation.Nullable;

public class BlockBrittleBrick extends Block implements IDamageable {

    public static final PropertyInteger HEALTH = PropertyInteger.create("health", 0, 9);

    public BlockBrittleBrick(String id) {
        super(Material.CLAY, MapColor.RED);
        this.setUnlocalizedName(id);
        this.setRegistryName(id);
        this.setCreativeTab(CUBG.TAB);
        this.setHardness(0.15F);
        this.setResistance(0.5F);
        this.setDefaultState(this.blockState.getBaseState().withProperty(HEALTH, 9));
    }

    @Override
    public boolean isOpaqueCube(IBlockState state) {
        return false;
    }

    @Override
    public boolean isFullCube(IBlockState state) {
        return false;
    }

    @Override
    public IBlockState getStateForPlacement(World world, BlockPos pos, EnumFacing facing, float hitX, float hitY, float hitZ, int meta, EntityLivingBase placer, EnumHand hand) {
        return this.getDefaultState();
    }

    @Override
    public boolean removedByPlayer(IBlockState state, World world, BlockPos pos, EntityPlayer player, boolean willHarvest) {
        if(player.capabilities.isCreativeMode) {
            return replaceGrass(world, pos);
        }
        return damageBlock(world, pos, state, 1, player.dimension);
    }

    @Override
    public void onBlockExploded(World world, BlockPos pos, Explosion explosion) {
        replaceGrass(world, pos);
    }

    @Override
    public void onProjectileDamaged(World world, IBlockState state, BlockPos pos, EntityProjectile projectile) {
        int damage = (int) Math.ceil(projectile.getDamage() / 2.0) + 1;
        damageBlock(world, pos, state, damage, projectile.dimension);
    }

    private boolean damageBlock(World world, BlockPos pos, IBlockState state, int damage, int dimension) {
        int healthLeft = state.getValue(HEALTH);
        spawnBrickParticles(world, pos, healthLeft, damage, dimension);
        if(healthLeft - damage < 0) {
            world.playEvent(2001, pos, Block.getStateId(state));
            return replaceGrass(world, pos);
        } else {
            TileEntity tileEntity = world.getTileEntity(pos);
            world.setBlockState(pos, state.withProperty(HEALTH, healthLeft - damage));
            if(tileEntity != null) {
                tileEntity.validate();
                world.setTileEntity(pos, tileEntity);
            }
        }
        return false;
    }

    private void spawnBrickParticles(World world, BlockPos pos, int healthLeft, int damage, int dimension) {
        if(world.isRemote)
            return;
        damage = Math.min(healthLeft, damage);
        for(int i = 0; i < 4 * damage; i++) {
            PacketHandler.INSTANCE.sendToAllTracking(new MessageParticle(EnumParticles.BRICK, pos.getX() + 0.5, pos.getY() + 0.5, pos.getZ() + 0.5, RANDOM.nextGaussian(), 0.5, RANDOM.nextGaussian()), new NetworkRegistry.TargetPoint(dimension, pos.getX() + 0.5, pos.getY() + 0.5, pos.getZ() + 0.5, 32));
        }
    }

    private boolean replaceGrass(World world, BlockPos pos) {
        TileEntity tileEntity = world.getTileEntity(pos);
        if(tileEntity instanceof TileEntityBrittleBrick) {
            world.destroyBlock(pos, true);
            if(((TileEntityBrittleBrick) tileEntity).isGrass()) {
                world.setBlockState(pos, Blocks.TALLGRASS.getDefaultState().withProperty(BlockTallGrass.TYPE, BlockTallGrass.EnumType.GRASS));
            }
            return true;
        }
        return false;
    }

    @Override
    public boolean canPlaceBlockOnSide(World worldIn, BlockPos pos, EnumFacing side) {
        IBlockState state = worldIn.getBlockState(pos);
        return !state.getMaterial().isLiquid();
    }

    @Override
    public void getDrops(NonNullList<ItemStack> drops, IBlockAccess world, BlockPos pos, IBlockState state, int fortune) {
        drops.add(new ItemStack(ModItems.BRICK, 2));
    }

    @Override
    public int getMetaFromState(IBlockState state) {
        return state.getValue(HEALTH);
    }

    @Override
    public IBlockState getStateFromMeta(int meta) {
        return this.getDefaultState().withProperty(HEALTH, meta);
    }

    @Override
    protected BlockStateContainer createBlockState() {
        return new BlockStateContainer(this, HEALTH);
    }

    @Override
    public boolean hasTileEntity(IBlockState state) {
        return true;
    }

    @Nullable
    @Override
    public TileEntity createTileEntity(World world, IBlockState state) {
        return new TileEntityBrittleBrick();
    }
}
