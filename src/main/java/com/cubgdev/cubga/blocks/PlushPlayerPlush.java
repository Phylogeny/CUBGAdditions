package com.cubgdev.cubga.blocks;

import com.cubgdev.cubga.CUBG;
import com.cubgdev.cubga.init.ModBlocks;
import com.cubgdev.cubga.tileentity.TileEntityPlayerPlush;
import com.cubgdev.cubga.utils.PlushUtil;
import com.mojang.authlib.GameProfile;

import net.minecraft.block.Block;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.BlockFaceShape;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.particle.ParticleManager;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTUtil;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumBlockRenderType;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.NonNullList;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public class PlushPlayerPlush extends PlushBase implements ITileEntityProvider {

	public static final String[] SPECIAL_USERS =
			{
					PlushUtil.getPlayerName("68c08594e7cd43fbbdf9240147ee26cf"), //JacksonPlayzYT
					PlushUtil.getPlayerName("b61e1172e4f0450a9f9858ec91bc41dc"), //MinecraftDoodler
					PlushUtil.getPlayerName("62d17f0b524841f4befc2daa457fb266"), //MrCrayfish
					PlushUtil.getPlayerName("000399b665d14979b3c0af309112625f"), //HauntedCorpse
					PlushUtil.getPlayerName("f899be440c2946c1b20f27e950bde621"), //CoffeeCatRailway
					PlushUtil.getPlayerName("8216bff477a0417ab4e4c786e68fa141"), //BrentTheGamer
					PlushUtil.getPlayerName("834205305478462ca08dfeb1375ac3e2"), //Builderboy426
					PlushUtil.getPlayerName("cf21dfba983442bb8f162f9c7418ac76"), //OstenTV
					PlushUtil.getPlayerName("beb10f7ef80b487ba99e661d8d798f90"), //Mastef_Chief
					PlushUtil.getPlayerName("86dc8a9f238e450280211d488095fd8a")  //Ocelot5836
			};


	public static final AxisAlignedBB BOUNDING_BOX = new AxisAlignedBB(0.0625 * 5.5, 0, 0.0625 * 5.5, 0.0625 * 10.5, 0.0625 * 13, 0.0625 * 10.5);

	public PlushPlayerPlush() {
		super(Material.CLOTH);
		setRegistryName("player_plush");
		setUnlocalizedName("player_plush");
		setHardness(0.5f);
		setHarvestLevel("pickaxe", -1);
		setSoundType(SoundType.CLOTH);
		setCreativeTab(CUBG.TAB);
	}

	@Override
	public boolean onBlockActivated(World world, BlockPos pos, IBlockState state, EntityPlayer player, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
		ItemStack heldItem = player.getHeldItem(hand);
		if (world.getTileEntity(pos) instanceof TileEntityPlayerPlush) {
			TileEntityPlayerPlush te = (TileEntityPlayerPlush) world.getTileEntity(pos);
			if (!heldItem.isEmpty() && heldItem.getItem() == Items.NAME_TAG && heldItem.hasDisplayName()) {
				te.setPlayerProfile(new GameProfile(null, heldItem.getDisplayName()));
				if (!world.isRemote && !player.isCreative()) {
					heldItem.shrink(1);
				}
				return true;
			}
		}
		return false;
	}

	@Override
	public void neighborChanged(IBlockState state, World world, BlockPos pos, Block blockIn, BlockPos fromPos) {
		if (!world.getBlockState(pos.down()).isSideSolid(world, pos, EnumFacing.UP)) {
			world.destroyBlock(pos, true);
		}
	}

	@Override
	public void getDrops(net.minecraft.util.NonNullList<ItemStack> drops, IBlockAccess world, BlockPos pos, IBlockState state, int fortune) {
		if (world.getTileEntity(pos) instanceof TileEntityPlayerPlush) {
			TileEntityPlayerPlush te = (TileEntityPlayerPlush) world.getTileEntity(pos);
			if (te.getPlayerProfile() != null) {
				drops.add(getDefaultStack(te.getPlayerProfile()));
				return;
			}
		}
		drops.add(new ItemStack(this, 1, 0));
	}

	@Override
	public void onBlockHarvested(World world, BlockPos pos, IBlockState state, EntityPlayer player) {
		if (!player.isCreative()) {
			this.dropBlockAsItem(world, pos, state, 0);
		}

		super.onBlockHarvested(world, pos, state, player);
	}

	@Override
	public void getSubBlocks(CreativeTabs item, NonNullList<ItemStack> items) {
		items.add(new ItemStack(this));
		for (int i = 0; i < SPECIAL_USERS.length; i++) {
			items.add(getDefaultStack(SPECIAL_USERS[i]));
		}
	}

	@Override
	public ItemStack getPickBlock(IBlockState state, RayTraceResult target, World world, BlockPos pos, EntityPlayer player) {
		if (world.getTileEntity(pos) instanceof TileEntityPlayerPlush) {
			TileEntityPlayerPlush te = (TileEntityPlayerPlush) world.getTileEntity(pos);
			if (te.getPlayerProfile() != null) {
				return getDefaultStack(te.getPlayerProfile());
			}
		}
		return new ItemStack(this, 1, 0);
	}

	@Override
	public boolean addHitEffects(IBlockState state, World world, RayTraceResult target, ParticleManager manager) {
		return true;
	}

	@Override
	public boolean addDestroyEffects(World world, BlockPos pos, ParticleManager manager) {
		manager.addBlockDestroyEffects(pos, Blocks.WOOL.getDefaultState());
		return true;
	}

	@Override
	public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos) {
		return BOUNDING_BOX;
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
	public EnumBlockRenderType getRenderType(IBlockState state) {
		return EnumBlockRenderType.ENTITYBLOCK_ANIMATED;
	}

	@Override
	public boolean canPlaceBlockAt(World world, BlockPos pos) {
		return super.canPlaceBlockAt(world, pos) && world.getBlockState(pos.down()).isSideSolid(world, pos, EnumFacing.UP);
	}

	@Override
	public BlockFaceShape getBlockFaceShape(IBlockAccess worldIn, IBlockState state, BlockPos pos, EnumFacing face) {
		return BlockFaceShape.UNDEFINED;
	}

	@Override
	public TileEntity createNewTileEntity(World world, int meta) {
		return new TileEntityPlayerPlush();
	}

	public static ItemStack getDefaultStack(String username) {
		return getDefaultStack(new GameProfile(null, username));
	}

	public static ItemStack getDefaultStack(GameProfile profile) {
		ItemStack stack = new ItemStack(ModBlocks.PLAYER_PLUSH);
		stack.setTagCompound(new NBTTagCompound());
		NBTTagCompound nbttagcompound = new NBTTagCompound();
		NBTUtil.writeGameProfile(nbttagcompound, profile);
		stack.getTagCompound().setTag("Owner", nbttagcompound);
		return stack;
	}
}