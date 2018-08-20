package com.cubgdev.cubga.client;

import com.cubgdev.cubga.CUBGConfig;
import com.cubgdev.cubga.Reference;
import com.cubgdev.cubga.blocks.BlockLootChest;
import com.cubgdev.cubga.client.gui.utilities.CUBGRenderHelper;
import com.cubgdev.cubga.init.ModBlocks;
import com.cubgdev.cubga.tileentity.TileEntityLootChest;
import com.mrcrayfish.obfuscate.client.event.RenderItemEvent;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.block.model.IBakedModel;
import net.minecraft.client.renderer.block.model.ItemCameraTransforms;
import net.minecraft.client.renderer.block.model.ItemTransformVec3f;
import net.minecraft.client.renderer.tileentity.TileEntityRendererDispatcher;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumHand;
import net.minecraft.util.EnumHandSide;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.client.event.RenderSpecificHandEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class RenderEvents
{
    private final TileEntityLootChest LOOT_CHEST_ENTITY = new TileEntityLootChest();
    private static final ResourceLocation BARS_BOSS = new ResourceLocation(Reference.MOD_ID, "textures/gui/bars_boss.png");
    private static final ResourceLocation BARS_XP = new ResourceLocation(Reference.MOD_ID, "textures/gui/bars_xp.png");

    @SubscribeEvent
    public void onRenderHudEvent(RenderGameOverlayEvent.Pre event)
    {
        if(CUBGConfig.CLIENT.ui.uiEnabled)
        {
            Minecraft mc = Minecraft.getMinecraft();
            ScaledResolution scaledRes = new ScaledResolution(mc);
            EntityPlayer player = mc.player;
            float red = 1F / 255;
            float green = 1F / 255;
            float blue = 1F / 255;

            if(event.getType() == RenderGameOverlayEvent.ElementType.HEALTH && mc.playerController.shouldDrawHUD())
            {
                GlStateManager.color(red * CUBGConfig.CLIENT.ui.health.healthColor.Red, green * CUBGConfig.CLIENT.ui.health.healthColor.Green, blue * CUBGConfig.CLIENT.ui.health.healthColor.Blue);

                if(!CUBGConfig.CLIENT.ui.health.cleanBarEnabled)
                {
                    mc.getTextureManager().bindTexture(BARS_XP);
                }
                else
                {
                    mc.getTextureManager().bindTexture(BARS_BOSS);
                }

                int x = scaledRes.getScaledWidth() / 2 - 91;
                GlStateManager.enableAlpha();
                int k = (int) (player.getHealth() / player.getMaxHealth() * 184.0F);
                int l = scaledRes.getScaledHeight() - 32 + 3;
                Gui.drawScaledCustomSizeModalRect(x, l, 1, 1, 184, 5, 184, 5, 256, 256);

                if(k > 0)
                {
                    Gui.drawScaledCustomSizeModalRect(x, l, 1, 6, k, 5, k, 5, 256, 256);
                }

                if(CUBGConfig.CLIENT.ui.health.numbersEnabled)
                {
                    boolean flag1 = false;
                    int color = flag1 ? 16777215 : 8453920;
                    String text = null;

                    int health = (int) player.getHealth();

                    if(CUBGConfig.CLIENT.ui.health.percentagesEnabled)
                    {
                        text = ((int) Math.round(player.getHealth() / player.getMaxHealth() * 200f) / 2) + "%";
                    }
                    else
                    {
                        text = Float.toString((float) (health / 2f)) + "/" + (player.getMaxHealth() / 2f);
                    }
                    GlStateManager.pushMatrix();
                    GlStateManager.translate(scaledRes.getScaledWidth() / 2, scaledRes.getScaledHeight() - 34, 0);
                    GlStateManager.scale(1, 1, 1);
                    CUBGRenderHelper.renderTextWithShadow(text, -Minecraft.getMinecraft().fontRenderer.getStringWidth(text) / 2, -Minecraft.getMinecraft().fontRenderer.FONT_HEIGHT / 2, color);
                    GlStateManager.popMatrix();
                }
            }
            event.setCanceled(event.getType() == RenderGameOverlayEvent.ElementType.HEALTH || event.getType() == RenderGameOverlayEvent.ElementType.FOOD || event.getType() == RenderGameOverlayEvent.ElementType.EXPERIENCE || event.getType() == RenderGameOverlayEvent.ElementType.ARMOR || event.getType() == RenderGameOverlayEvent.ElementType.AIR);
        }
    }

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
    public void onRenderItemEntity(RenderItemEvent.Entity.Pre event)
    {
        renderLootChest(event.getItem(), event.getTransformType(), event.getPartialTicks(), false);
    }

    @SubscribeEvent
    public void onRenderItemHeld(RenderItemEvent.Held.Pre event)
    {
        renderLootChest(event.getItem(), event.getTransformType(), event.getPartialTicks(), event.getHandSide() == EnumHandSide.LEFT);
    }

    @SubscribeEvent
    public void onRenderSpecificHand(RenderSpecificHandEvent event)
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

            LOOT_CHEST_ENTITY.getLootChest().setChestTexture(BlockLootChest.getChestTexture(stack));
            LOOT_CHEST_ENTITY.getLootChest().setColor(BlockLootChest.getChestColor(stack));
            TileEntityRendererDispatcher.instance.render(LOOT_CHEST_ENTITY, 0.0D, 0.0D, 0.0D, partialTicks);
        }
    }
}