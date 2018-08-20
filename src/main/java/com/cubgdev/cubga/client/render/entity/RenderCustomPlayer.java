package com.cubgdev.cubga.client.render.entity;

import net.minecraft.client.entity.AbstractClientPlayer;
import net.minecraft.client.model.ModelBiped;
import net.minecraft.client.model.ModelElytra;
import net.minecraft.client.model.ModelPlayer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.entity.RenderLivingBase;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.renderer.entity.layers.LayerArrow;
import net.minecraft.client.renderer.entity.layers.LayerBipedArmor;
import net.minecraft.client.renderer.entity.layers.LayerCustomHead;
import net.minecraft.client.renderer.entity.layers.LayerEntityOnShoulder;
import net.minecraft.client.renderer.entity.layers.LayerHeldItem;
import net.minecraft.entity.player.EnumPlayerModelParts;
import net.minecraft.item.EnumAction;
import net.minecraft.item.ItemStack;
import net.minecraft.scoreboard.Score;
import net.minecraft.scoreboard.ScoreObjective;
import net.minecraft.scoreboard.Scoreboard;
import net.minecraft.util.EnumHandSide;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class RenderCustomPlayer extends RenderLivingBase<AbstractClientPlayer> {

	private final boolean smallArms;
	private final ModelElytra modelElytra = new ModelElytra();

	public RenderCustomPlayer(RenderManager renderManager) {
		this(renderManager, false);
	}

	public RenderCustomPlayer(RenderManager renderManager, boolean useSmallArms) {
		super(renderManager, new ModelPlayer(0.0F, useSmallArms), 0.5F);
		this.smallArms = useSmallArms;
		this.addLayer(new LayerBipedArmor(this));
		this.addLayer(new LayerHeldItem(this));
		this.addLayer(new LayerArrow(this));
		this.addLayer(new LayerCustomHead(this.getMainModel().bipedHead));
		// this.addLayer(new LayerElytra(this) {
		// @Override
		// public void doRenderLayer(EntityLivingBase entitylivingbaseIn, float limbSwing, float limbSwingAmount, float partialTicks, float ageInTicks, float netHeadYaw, float headPitch, float scale) {
		// ItemStack itemstack = entitylivingbaseIn.getItemStackFromSlot(EntityEquipmentSlot.CHEST);
		//
		// if (itemstack.getItem() == Items.ELYTRA) {
		// GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
		// GlStateManager.enableBlend();
		// GlStateManager.blendFunc(GlStateManager.SourceFactor.ONE, GlStateManager.DestFactor.ZERO);
		//
		// Capes.bind(Capes.getCape((EntityPlayer) entitylivingbaseIn));
		//
		// GlStateManager.pushMatrix();
		// GlStateManager.translate(0.0F, 0.0F, 0.125F);
		// modelElytra.setRotationAngles(limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch, scale, entitylivingbaseIn);
		// modelElytra.render(entitylivingbaseIn, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch, scale);
		//
		// if (itemstack.isItemEnchanted()) {
		// LayerArmorBase.renderEnchantedGlint(this.renderPlayer, entitylivingbaseIn, modelElytra, limbSwing, limbSwingAmount, partialTicks, ageInTicks, netHeadYaw, headPitch, scale);
		// }
		//
		// GlStateManager.disableBlend();
		// GlStateManager.popMatrix();
		// }
		// }
		// });
		this.addLayer(new LayerEntityOnShoulder(renderManager));
	}

	public ModelPlayer getMainModel() {
		return (ModelPlayer) super.getMainModel();
	}

	/**
	 * Renders the desired {@code T} type Entity.
	 */
	public void doRender(AbstractClientPlayer entity, double x, double y, double z, float entityYaw, float partialTicks) {
		if (!entity.isUser() || this.renderManager.renderViewEntity == entity) {
			double d0 = y;

			if (entity.isSneaking()) {
				d0 = y - 0.125D;
			}

			this.setModelVisibilities(entity);
			GlStateManager.enableBlendProfile(GlStateManager.Profile.PLAYER_SKIN);
			super.doRender(entity, x, d0, z, entityYaw, partialTicks);
			GlStateManager.disableBlendProfile(GlStateManager.Profile.PLAYER_SKIN);
		}
	}

	private void setModelVisibilities(AbstractClientPlayer clientPlayer) {
		ModelPlayer model = this.getMainModel();

		if (clientPlayer.isSpectator()) {
			model.setVisible(false);
			model.bipedHead.showModel = true;
			model.bipedHeadwear.showModel = true;
		} else {
			ItemStack itemstack = clientPlayer.getHeldItemMainhand();
			ItemStack itemstack1 = clientPlayer.getHeldItemOffhand();
			model.setVisible(true);
			model.bipedHeadwear.showModel = clientPlayer.isWearing(EnumPlayerModelParts.HAT);
			model.bipedBodyWear.showModel = clientPlayer.isWearing(EnumPlayerModelParts.JACKET);
			model.bipedLeftLegwear.showModel = clientPlayer.isWearing(EnumPlayerModelParts.LEFT_PANTS_LEG);
			model.bipedRightLegwear.showModel = clientPlayer.isWearing(EnumPlayerModelParts.RIGHT_PANTS_LEG);
			model.bipedLeftArmwear.showModel = clientPlayer.isWearing(EnumPlayerModelParts.LEFT_SLEEVE);
			model.bipedRightArmwear.showModel = clientPlayer.isWearing(EnumPlayerModelParts.RIGHT_SLEEVE);
			model.isSneak = clientPlayer.isSneaking();
			ModelBiped.ArmPose modelbiped$armpose = ModelBiped.ArmPose.EMPTY;
			ModelBiped.ArmPose modelbiped$armpose1 = ModelBiped.ArmPose.EMPTY;

			if (!itemstack.isEmpty()) {
				modelbiped$armpose = ModelBiped.ArmPose.ITEM;

				if (clientPlayer.getItemInUseCount() > 0) {
					EnumAction enumaction = itemstack.getItemUseAction();

					if (enumaction == EnumAction.BLOCK) {
						modelbiped$armpose = ModelBiped.ArmPose.BLOCK;
					} else if (enumaction == EnumAction.BOW) {
						modelbiped$armpose = ModelBiped.ArmPose.BOW_AND_ARROW;
					}
				}
			}

			if (!itemstack1.isEmpty()) {
				modelbiped$armpose1 = ModelBiped.ArmPose.ITEM;

				if (clientPlayer.getItemInUseCount() > 0) {
					EnumAction enumaction1 = itemstack1.getItemUseAction();

					if (enumaction1 == EnumAction.BLOCK) {
						modelbiped$armpose1 = ModelBiped.ArmPose.BLOCK;
					}
					// FORGE: fix MC-88356 allow offhand to use bow and arrow animation
					else if (enumaction1 == EnumAction.BOW) {
						modelbiped$armpose1 = ModelBiped.ArmPose.BOW_AND_ARROW;
					}
				}
			}

			if (clientPlayer.getPrimaryHand() == EnumHandSide.RIGHT) {
				model.rightArmPose = modelbiped$armpose;
				model.leftArmPose = modelbiped$armpose1;
			} else {
				model.rightArmPose = modelbiped$armpose1;
				model.leftArmPose = modelbiped$armpose;
			}
		}
	}

	/**
	 * Returns the location of an entity's texture. Doesn't seem to be called unless you call Render.bindEntityTexture.
	 */
	public ResourceLocation getEntityTexture(AbstractClientPlayer entity) {
		return entity.getLocationSkin();
	}

	public void transformHeldFull3DItemLayer() {
		GlStateManager.translate(0.0F, 0.1875F, 0.0F);
	}

	/**
	 * Allows the render to do state modifications necessary before the model is rendered.
	 */
	protected void preRenderCallback(AbstractClientPlayer entitylivingbaseIn, float partialTickTime) {
		float f = 0.9375F;
		GlStateManager.scale(0.9375F, 0.9375F, 0.9375F);
	}

	protected void renderEntityName(AbstractClientPlayer entityIn, double x, double y, double z, String name, double distanceSq) {
		if (distanceSq < 100.0D) {
			Scoreboard scoreboard = entityIn.getWorldScoreboard();
			ScoreObjective scoreobjective = scoreboard.getObjectiveInDisplaySlot(2);

			if (scoreobjective != null) {
				Score score = scoreboard.getOrCreateScore(entityIn.getName(), scoreobjective);
				this.renderLivingLabel(entityIn, score.getScorePoints() + " " + scoreobjective.getDisplayName(), x, y, z, 64);
				y += (double) ((float) this.getFontRendererFromRenderManager().FONT_HEIGHT * 1.15F * 0.025F);
			}
		}

		super.renderEntityName(entityIn, x, y, z, name, distanceSq);
	}

	public void renderRightArm(AbstractClientPlayer clientPlayer) {
		float f = 1.0F;
		GlStateManager.color(1.0F, 1.0F, 1.0F);
		float f1 = 0.0625F;
		ModelPlayer model = this.getMainModel();
		this.setModelVisibilities(clientPlayer);
		GlStateManager.enableBlend();
		model.swingProgress = 0.0F;
		model.isSneak = false;
		model.setRotationAngles(0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0625F, clientPlayer);
		model.bipedRightArm.rotateAngleX = 0.0F;
		model.bipedRightArm.render(0.0625F);
		model.bipedRightArmwear.rotateAngleX = 0.0F;
		model.bipedRightArmwear.render(0.0625F);
		GlStateManager.disableBlend();
	}

	public void renderLeftArm(AbstractClientPlayer clientPlayer) {
		float f = 1.0F;
		GlStateManager.color(1.0F, 1.0F, 1.0F);
		float f1 = 0.0625F;
		ModelPlayer model = this.getMainModel();
		this.setModelVisibilities(clientPlayer);
		GlStateManager.enableBlend();
		model.isSneak = false;
		model.swingProgress = 0.0F;
		model.setRotationAngles(0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0625F, clientPlayer);
		model.bipedLeftArm.rotateAngleX = 0.0F;
		model.bipedLeftArm.render(0.0625F);
		model.bipedLeftArmwear.rotateAngleX = 0.0F;
		model.bipedLeftArmwear.render(0.0625F);
		GlStateManager.disableBlend();
	}

	/**
	 * Sets a simple glTranslate on a LivingEntity.
	 */
	protected void renderLivingAt(AbstractClientPlayer entityLivingBaseIn, double x, double y, double z) {
		if (entityLivingBaseIn.isEntityAlive() && entityLivingBaseIn.isPlayerSleeping()) {
			super.renderLivingAt(entityLivingBaseIn, x + (double) entityLivingBaseIn.renderOffsetX, y + (double) entityLivingBaseIn.renderOffsetY, z + (double) entityLivingBaseIn.renderOffsetZ);
		} else {
			super.renderLivingAt(entityLivingBaseIn, x, y, z);
		}
	}

	protected void applyRotations(AbstractClientPlayer entityLiving, float p_77043_2_, float rotationYaw, float partialTicks) {
		if (entityLiving.isEntityAlive() && entityLiving.isPlayerSleeping()) {
			GlStateManager.rotate(entityLiving.getBedOrientationInDegrees(), 0.0F, 1.0F, 0.0F);
			GlStateManager.rotate(this.getDeathMaxRotation(entityLiving), 0.0F, 0.0F, 1.0F);
			GlStateManager.rotate(270.0F, 0.0F, 1.0F, 0.0F);
		} else if (entityLiving.isElytraFlying()) {
			super.applyRotations(entityLiving, p_77043_2_, rotationYaw, partialTicks);
			float f = (float) entityLiving.getTicksElytraFlying() + partialTicks;
			float f1 = MathHelper.clamp(f * f / 100.0F, 0.0F, 1.0F);
			GlStateManager.rotate(f1 * (-90.0F - entityLiving.rotationPitch), 1.0F, 0.0F, 0.0F);
			Vec3d vec3d = entityLiving.getLook(partialTicks);
			double d0 = entityLiving.motionX * entityLiving.motionX + entityLiving.motionZ * entityLiving.motionZ;
			double d1 = vec3d.x * vec3d.x + vec3d.z * vec3d.z;

			if (d0 > 0.0D && d1 > 0.0D) {
				double d2 = (entityLiving.motionX * vec3d.x + entityLiving.motionZ * vec3d.z) / (Math.sqrt(d0) * Math.sqrt(d1));
				double d3 = entityLiving.motionX * vec3d.z - entityLiving.motionZ * vec3d.x;
				GlStateManager.rotate((float) (Math.signum(d3) * Math.acos(d2)) * 180.0F / (float) Math.PI, 0.0F, 1.0F, 0.0F);
			}
		} else {
			super.applyRotations(entityLiving, p_77043_2_, rotationYaw, partialTicks);
		}
	}
}