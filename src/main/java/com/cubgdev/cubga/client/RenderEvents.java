package com.cubgdev.cubga.client;

import java.util.Map;

import com.cubgdev.cubga.blocks.BlockLootChest;
import com.cubgdev.cubga.init.ModBlocks;
import com.cubgdev.cubga.tileentity.TileEntityCrystalContainer;
import com.cubgdev.cubga.tileentity.TileEntityLootChest;
import com.cubgdev.cubga.utils.TextureUtils;
import com.google.common.collect.Maps;

import com.mrcrayfish.obfuscate.client.event.RenderItemEvent;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelChest;
import net.minecraft.client.model.ModelEnderCrystal;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.block.model.IBakedModel;
import net.minecraft.client.renderer.block.model.ItemCameraTransforms;
import net.minecraft.client.renderer.block.model.ItemTransformVec3f;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.client.renderer.entity.RenderDragon;
import net.minecraft.client.renderer.texture.TextureUtil;
import net.minecraft.client.renderer.tileentity.TileEntityRendererDispatcher;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumHand;
import net.minecraft.util.EnumHandSide;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.client.event.ModelBakeEvent;
import net.minecraftforge.client.event.RenderSpecificHandEvent;
import net.minecraftforge.client.event.RenderWorldLastEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class RenderEvents
{
	private static final ResourceLocation ENDER_CRYSTAL_TEXTURES = new ResourceLocation("textures/entity/endercrystal/endercrystal.png");
	private static final ModelBase ENDER_CRYSTAL_MODEL = new ModelEnderCrystal(0.0f, false);
	private static final ModelBase ENDER_CRYSTAL_BASE_MODEL = new ModelEnderCrystal(0.0f, true);
	private static final Map<BlockPos, TileEntity> NEARBY_TILE_ENTITIES = Maps.<BlockPos, TileEntity>newHashMap();

	@SubscribeEvent
	public void onRenderWorldEvent(RenderWorldLastEvent event)
	{
		EntityPlayer player = Minecraft.getMinecraft().player;
		World world = player.world;
		float partialTicks = event.getPartialTicks();

		double posX = player.lastTickPosX + (player.posX - player.lastTickPosX) * partialTicks;
		double posY = player.lastTickPosY + (player.posY - player.lastTickPosY) * partialTicks;
		double posZ = player.lastTickPosZ + (player.posZ - player.lastTickPosZ) * partialTicks;

		GlStateManager.translate(-posX, -posY, -posZ);

		TextureUtils.bindTexture("textures/entity/endercrystal/endercrystal.png");

		this.renderCrystalContainers(player, world, partialTicks);
	}

	private void renderCrystalContainers(EntityPlayer player, World world, float partialTicks)
	{
		int chunkSize = 8;

		for (int i = 0; i < chunkSize * chunkSize; i++)
		{
			int x = (i % chunkSize) - chunkSize / 2;
			int z = (i / chunkSize) - chunkSize / 2;
			NEARBY_TILE_ENTITIES.putAll(world.getChunkFromBlockCoords(player.getPosition().add(x * 16, 0, z * 16)).getTileEntityMap());
		}

		for (BlockPos position : NEARBY_TILE_ENTITIES.keySet())
		{
			if (NEARBY_TILE_ENTITIES.get(position) instanceof TileEntityCrystalContainer)
			{
				TileEntityCrystalContainer te = (TileEntityCrystalContainer) NEARBY_TILE_ENTITIES.get(position);
				BlockPos fromPos = te.getPos();
				if (Minecraft.getMinecraft().player.getPosition().getDistance(fromPos.getX(), fromPos.getY(), fromPos.getZ()) < 128D)
				{
					te.updateRotation();
					double innerRotation = te.getInnerRotation();
					double innerRotationSin = Math.sin(innerRotation * 0.2F) / 2.0F + 0.45F;
					innerRotationSin = innerRotationSin * innerRotationSin + innerRotationSin;

					double scale = 0.3f * te.getScale();
					double scaleFactor = te.getScale() * (1 / te.getScale());

					GlStateManager.pushMatrix();
					GlStateManager.disableLighting();
					{
						TextureUtils.bindTexture(ENDER_CRYSTAL_TEXTURES);
						GlStateManager.translate(fromPos.getX(), fromPos.getY(), fromPos.getZ());
						GlStateManager.translate(0.5 * scaleFactor, 0.25 * te.getScale(), 0.5 * scaleFactor);
						GlStateManager.scale(scale, scale, scale);
						ModelBase model = te.renderBase() ? ENDER_CRYSTAL_BASE_MODEL : ENDER_CRYSTAL_MODEL;
						model.render(null, 0.0F, (float) (innerRotation * 3.0), (float) (innerRotationSin * 0.1), 0.0F, 0.0F, 0.0625F);
					}
					GlStateManager.enableLighting();
					GlStateManager.popMatrix();

					BlockPos[] beamPositions = te.getBeamPositions();
					if (beamPositions != null)
					{
						for (int i = 0; i < beamPositions.length; i++)
						{
							BlockPos toPos = beamPositions[i];
							if (toPos != null)
							{
								GlStateManager.pushMatrix();
								GlStateManager.translate(0.5 * scaleFactor, 0.5, 0.5 * scaleFactor);
								GlStateManager.scale(scale, scale, scale);
								TextureUtils.bindTexture(RenderDragon.ENDERCRYSTAL_BEAM_TEXTURES);
								float f2 = (float) toPos.getX();
								float f3 = (float) toPos.getY() - 1.75f;
								float f4 = (float) toPos.getZ();
								double d0 = (double) f2 - fromPos.getX();
								double d1 = (double) f3 - fromPos.getY();
								double d2 = (double) f4 - fromPos.getZ();
								// RenderDragon.renderCrystalBeams((fromPos.getX() + d0)*(1/scale), (fromPos.getY() + 0.25D + d1)*(1/scale), (fromPos.getZ() + d2)*(1/scale), partialTicks, (double) f2*scaleFactor, (double) f3*scaleFactor, (double) f4*scaleFactor, (int) innerRotation, fromPos.getX()*scaleFactor, fromPos.getY()*scaleFactor, fromPos.getZ() *scaleFactor);
								RenderDragon.renderCrystalBeams(f2, f3, f4, partialTicks, d0, d1, d2, (int)te.getInnerRotation(), fromPos.getX(), fromPos.getY(), fromPos.getZ());
								GlStateManager.popMatrix();
							}
						}
					}
				}
			}
		}
		NEARBY_TILE_ENTITIES.clear();
	}

	private final TileEntityLootChest LOOT_CHEST_ENTITY = new TileEntityLootChest();

	@SubscribeEvent
	public void onRenderItemGui(RenderItemEvent.Gui.Pre event)
	{
		ItemStack stack = event.getItem();
		if(stack.getItem() == Item.getItemFromBlock(ModBlocks.LOOT_CHEST))
		{
			GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
			GlStateManager.enableRescaleNormal();
			renderLootChest(stack, event.getTransformType(), event.getPartialTicks(), false);
		}
	}

	@SubscribeEvent
	public void onRenderItemGui(RenderItemEvent.Entity.Pre event)
	{
		renderLootChest(event.getItem(), event.getTransformType(), event.getPartialTicks(), false);
	}

	@SubscribeEvent
	public void onRenderItemGui(RenderItemEvent.Held.Pre event)
	{
		renderLootChest(event.getItem(), event.getTransformType(), event.getPartialTicks(), event.getHandSide() == EnumHandSide.LEFT);
	}

	@SubscribeEvent
	public void onRenderItemGui(RenderSpecificHandEvent event)
	{
		if(event.getItemStack().getItem() == Item.getItemFromBlock(ModBlocks.LOOT_CHEST))
		{
			GlStateManager.pushMatrix();
			boolean right = Minecraft.getMinecraft().gameSettings.mainHand == EnumHandSide.RIGHT ? event.getHand() == EnumHand.MAIN_HAND : event.getHand() == EnumHand.OFF_HAND;
			GlStateManager.translate(0, -event.getEquipProgress(), 0);
			GlStateManager.translate(right ? 0.56F : -0.56F, -0.52F, -0.72F);
			renderLootChest(event.getItemStack(), right ? ItemCameraTransforms.TransformType.FIRST_PERSON_RIGHT_HAND : ItemCameraTransforms.TransformType.FIRST_PERSON_LEFT_HAND, event.getPartialTicks(), !right);
			GlStateManager.popMatrix();
		}
	}

	private void renderLootChest(ItemStack stack, ItemCameraTransforms.TransformType transformType, float partialTicks, boolean leftHanded)
	{
		if(stack.getItem() == Item.getItemFromBlock(ModBlocks.LOOT_CHEST))
		{
			IBakedModel model = Minecraft.getMinecraft().getRenderItem().getItemModelMesher().getItemModel(stack);
			ItemTransformVec3f itemTransform = model.getItemCameraTransforms().getTransform(transformType);
			ItemCameraTransforms.applyTransformSide(itemTransform, leftHanded);
			GlStateManager.translate(-0.5, -0.5, -0.5);

			LOOT_CHEST_ENTITY.setChestTexture(BlockLootChest.getChestTexture(stack));
			LOOT_CHEST_ENTITY.setColor(BlockLootChest.getChestColor(stack));
			TileEntityRendererDispatcher.instance.render(LOOT_CHEST_ENTITY, 0.0D, 0.0D, 0.0D, partialTicks);
		}
	}
}