package com.cubgdev.cubga.item;

import com.cubgdev.cubga.tileentity.TileEntityBrittleBrick;
import net.minecraft.block.Block;
import net.minecraft.block.BlockTallGrass;
import net.minecraft.block.SoundType;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.resources.I18n;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;

import javax.annotation.Nullable;
import java.util.List;

public class ItemBrittleBrick extends ItemBlock {

	public ItemBrittleBrick(Block block) {
		super(block);
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

	@Override
	public EnumActionResult onItemUse(EntityPlayer player, World worldIn, BlockPos pos, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
		IBlockState state = worldIn.getBlockState(pos);
		Block block = state.getBlock();
		boolean placedOnGrass = false;

		if (block != Blocks.TALLGRASS || state.getValue(BlockTallGrass.TYPE) != BlockTallGrass.EnumType.GRASS) {
			pos = pos.offset(facing);
		} else {
			placedOnGrass = true;
		}

		ItemStack stack = player.getHeldItem(hand);
		if (!stack.isEmpty() && worldIn.mayPlace(this.block, pos, false, facing, null)) {
			int i = this.getMetadata(stack.getMetadata());
			IBlockState placementState = this.block.getStateForPlacement(worldIn, pos, facing, hitX, hitY, hitZ, i, player, hand);

			if (placeBlockAt(stack, player, worldIn, pos, facing, hitX, hitY, hitZ, placementState)) {
				placementState = worldIn.getBlockState(pos);
				SoundType soundtype = placementState.getBlock().getSoundType(placementState, worldIn, pos, player);
				worldIn.playSound(player, pos, soundtype.getPlaceSound(), SoundCategory.BLOCKS, (soundtype.getVolume() + 1.0F) / 2.0F, soundtype.getPitch() * 0.8F);
				stack.shrink(1);

				TileEntity tileEntity = worldIn.getTileEntity(pos);
				if(tileEntity instanceof TileEntityBrittleBrick)
				{
					((TileEntityBrittleBrick) tileEntity).setGrass(placedOnGrass);
				}
			}

			return EnumActionResult.SUCCESS;
		} else {
			return EnumActionResult.FAIL;
		}
	}
}