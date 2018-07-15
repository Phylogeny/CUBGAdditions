package com.cubgdev.cubga.client.render;

import com.cubgdev.cubga.init.ModBlocks;

import net.minecraft.block.Block;
import net.minecraft.client.renderer.block.model.IBakedModel;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.item.Item;
import net.minecraftforge.client.event.ModelBakeEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@EventBusSubscriber
public class ItemRenderRegistrationHandler {

	private static ModelBakeEvent eventObj;

	@SubscribeEvent
	@SideOnly(Side.CLIENT)
	public static void onModelBake(ModelBakeEvent event) {
		eventObj = event;
		
		register(ModBlocks.PLAYER_PLUSH, new ItemRenderPlayerPlush());
	}

	/**
	 * Registers a custom item renderer for an item or block.
	 * 
	 * <br>
	 * </br>
	 * 
	 * <b><i>Note: this does take in an object parameter, but it will throw an {@link IllegalArgumentException} if the object is not an item or a block.</b></i>
	 * 
	 * @param obj
	 *            The item or block to register.
	 * @param model
	 *            The baked model to register to the specified item or block
	 */
	private static void register(Object obj, IBakedModel model) {
		Item item = null;
		Block block = null;
		if (obj instanceof Item) {
			block = null;
			item = (Item) obj;
		} else if (obj instanceof Block) {
			block = (Block) obj;
			item = Item.getItemFromBlock(block);
		} else {
			throw new IllegalArgumentException("You can only register custom item renders for items or blocks. Any other object is not allowed");
		}

		if (item != null) {
			eventObj.getModelRegistry().putObject(new ModelResourceLocation(item.getRegistryName(), "inventory"), model);
		}
	}
}