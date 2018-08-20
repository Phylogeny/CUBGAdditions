package com.cubgdev.cubga.item;

import java.util.List;

import javax.annotation.Nullable;

import com.cubgdev.cubga.tileentity.TileEntityCrystalContainer;
import com.cubgdev.cubga.utils.Lib;

import net.minecraft.client.resources.I18n;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;

public class ItemBeamRod extends ItemBase
{

	public ItemBeamRod()
	{
		super("beam_rod");
	}

	@Override
	public void addInformation(ItemStack stack, World world, List<String> tooltip, ITooltipFlag flag)
	{
		super.addInformation(stack, world, tooltip, flag);

		BlockPos crystalPosition = ItemBeamRod.getCrystalPosition(stack);
		tooltip.add("");
		if (crystalPosition != null)
		{
			tooltip.add(I18n.format(this.getUnlocalizedNameInefficiently(stack) + ".position", TextFormatting.WHITE + Integer.toString(crystalPosition.getX()) + "," + Integer.toString(crystalPosition.getY()) + "," + Integer.toString(crystalPosition.getZ())));
		} else
		{
			tooltip.add(I18n.format(this.getUnlocalizedNameInefficiently(stack) + ".position", TextFormatting.WHITE + "Unbound"));
		}
	}

	@Override
	public ActionResult<ItemStack> onItemRightClick(World world, EntityPlayer player, EnumHand hand)
	{
		if (player.isSneaking())
		{
			ItemStack heldItem = player.getHeldItem(hand);
			if (ItemBeamRod.getCrystalPosition(heldItem) != null)
			{
				heldItem.setTagCompound(null);
			}
		}
		return super.onItemRightClick(world, player, hand);
	}

	@Override
	public EnumActionResult onItemUse(EntityPlayer player, World world, BlockPos pos, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ)
	{
		ItemStack heldItem = player.getHeldItem(hand);

		if (world.getTileEntity(pos) instanceof TileEntityCrystalContainer)
		{
			ItemBeamRod.setCrystalPosition(heldItem, pos);
			return EnumActionResult.SUCCESS;
		}

		if (ItemBeamRod.getCrystalPosition(heldItem) != null)
		{
			BlockPos crystalPosition = ItemBeamRod.getCrystalPosition(heldItem);
			TileEntity tileEntity = world.getTileEntity(crystalPosition);
			if (tileEntity instanceof TileEntityCrystalContainer)
			{
				TileEntityCrystalContainer te = (TileEntityCrystalContainer) tileEntity;
				return te.addBeamPosition(pos) ? EnumActionResult.SUCCESS : te.removeBeamPosition(pos) ? EnumActionResult.SUCCESS : EnumActionResult.FAIL;
			}
		}

		return EnumActionResult.FAIL;
	}

	@Override
	public boolean hasEffect(ItemStack stack)
	{
		return super.hasEffect(stack) || ItemBeamRod.getCrystalPosition(stack) != null;
	}

	@Nullable
	public static BlockPos getCrystalPosition(ItemStack stack)
	{
		NBTTagCompound nbt = stack.getTagCompound();
		if (nbt != null)
		{
			return Lib.readBlockPos(nbt.getCompoundTag("crystalPosition"));
		}
		return null;
	}

	public static void setCrystalPosition(ItemStack stack, BlockPos crystalPosition)
	{
		NBTTagCompound nbt = stack.getTagCompound();
		if (nbt == null)
		{
			nbt = new NBTTagCompound();
		}
		nbt.setTag("crystalPosition", Lib.writeBlockPos(crystalPosition));
		stack.setTagCompound(nbt);
	}
}
