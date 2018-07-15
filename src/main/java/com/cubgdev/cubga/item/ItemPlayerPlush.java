package com.cubgdev.cubga.item;

import java.util.List;
import java.util.UUID;

import com.cubgdev.cubga.init.ModBlocks;
import com.cubgdev.cubga.tileentity.TileEntityPlayerPlush;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.util.text.TextFormatting;
import org.apache.commons.lang3.StringUtils;

import com.mojang.authlib.GameProfile;

import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTUtil;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntitySkull;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;

import javax.annotation.Nullable;

public class ItemPlayerPlush extends ItemBlock {

	public ItemPlayerPlush() {
		super(ModBlocks.PLAYER_PLUSH);
	}

	@Override
	public EnumActionResult onItemUse(EntityPlayer player, World world, BlockPos pos, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
		if (facing == EnumFacing.DOWN) {
			return EnumActionResult.FAIL;
		} else {
			if (world.getBlockState(pos).getBlock().isReplaceable(world, pos)) {
				facing = EnumFacing.UP;
				pos = pos.down();
			}
			IBlockState iblockstate = world.getBlockState(pos);
			Block block = iblockstate.getBlock();
			boolean flag = block.isReplaceable(world, pos);

			if (!flag) {
				if (!world.getBlockState(pos).getMaterial().isSolid() && !world.isSideSolid(pos, facing, true)) {
					return EnumActionResult.FAIL;
				}

				pos = pos.offset(facing);
			}

			ItemStack heldItem = player.getHeldItem(hand);

			if (player.canPlayerEdit(pos, facing, heldItem) && ModBlocks.PLAYER_PLUSH.canPlaceBlockAt(world, pos)) {
				world.setBlockState(pos, ModBlocks.PLAYER_PLUSH.getDefaultState(), 11);
				int i = 0;

				if (facing == EnumFacing.UP) {
					i = MathHelper.floor((double) (player.rotationYaw * 16.0F / 360.0F) + 0.5D) & 15;
				}

				TileEntity tileentity = world.getTileEntity(pos);

				if (tileentity instanceof TileEntityPlayerPlush) {
					TileEntityPlayerPlush te = (TileEntityPlayerPlush) tileentity;

					GameProfile gameprofile = null;

					if (heldItem.hasTagCompound()) {
						NBTTagCompound nbt = heldItem.getTagCompound();

						if (nbt.hasKey("Owner", 10)) {
							gameprofile = NBTUtil.readGameProfileFromNBT(nbt.getCompoundTag("Owner"));
						} else if (nbt.hasKey("Owner", 8) && !StringUtils.isBlank(nbt.getString("Owner"))) {
							gameprofile = new GameProfile((UUID) null, nbt.getString("Owner"));
						}
					}

					te.setPlayerProfile(gameprofile);
					te.setRotation(i);
				}

				if (player instanceof EntityPlayerMP) {
					CriteriaTriggers.PLACED_BLOCK.trigger((EntityPlayerMP) player, pos, heldItem);
				}

				heldItem.shrink(1);
				return EnumActionResult.SUCCESS;
			} else {
				return EnumActionResult.FAIL;
			}
		}
	}

	@Override
	public String getItemStackDisplayName(ItemStack stack) {
		if (stack.hasTagCompound()) {
			if (stack.getTagCompound().hasKey("Owner", 8)) {
				return I18n.format(this.getUnlocalizedNameInefficiently(stack) + ".nbt.name", stack.getTagCompound().getString("Owner"));
			}

			if (stack.getTagCompound().hasKey("Owner", 10)) {
				NBTTagCompound nbttagcompound = stack.getTagCompound().getCompoundTag("Owner");

				if (nbttagcompound.hasKey("Name", 8)) {
					return I18n.format(this.getUnlocalizedNameInefficiently(stack) + ".nbt.name", nbttagcompound.getString("Name"));
				}
			}
		}

		return super.getItemStackDisplayName(stack);
	}

	@Override
	public boolean updateItemStackNBT(NBTTagCompound nbt) {
		super.updateItemStackNBT(nbt);

		if (nbt.hasKey("Owner", 8) && !StringUtils.isBlank(nbt.getString("Owner"))) {
			GameProfile gameprofile = new GameProfile((UUID) null, nbt.getString("Owner"));
			gameprofile = TileEntitySkull.updateGameprofile(gameprofile);
			nbt.setTag("Owner", NBTUtil.writeGameProfile(new NBTTagCompound(), gameprofile));
			return true;
		} else {
			return false;
		}
	}

	@Override
	public void addInformation(ItemStack stack, @Nullable World worldIn, List<String> tooltip, ITooltipFlag flagIn) {
		if (GuiScreen.isShiftKeyDown()) {
			String info = I18n.format(this.getUnlocalizedName() + ".info");
			tooltip.addAll(Minecraft.getMinecraft().fontRenderer.listFormattedStringToWidth(info, 150));
		} else {
			tooltip.add(TextFormatting.YELLOW + I18n.format("item.show_info", "SHIFT"));
		}
	}
}