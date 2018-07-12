package com.cubgdev.cubga.entity;

import com.cubgdev.cubga.init.ModItems;
import com.mrcrayfish.guns.interfaces.IDamageable;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.projectile.EntityThrowable;
import net.minecraft.item.ItemStack;
import net.minecraft.util.DamageSource;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.World;

public class EntityThrowableBrick extends EntityThrowable {

    public EntityThrowableBrick(World world) {
        super(world);
    }

    public EntityThrowableBrick(World world, EntityPlayer playerEntity) {
        super(world, playerEntity);
    }

    public EntityThrowableBrick(World world, double x, double y, double z) {
        super(world, x, y, z);
    }

    @Override
    protected void onImpact(RayTraceResult result) {
        if (!this.world.isRemote) {
            if (result.typeOfHit == RayTraceResult.Type.BLOCK) {
                BlockPos pos = result.getBlockPos();
                IBlockState state = this.world.getBlockState(pos);

                if(state.getBlock().isReplaceable(world, pos)) {
                    return;
                }

                if (state.getBlock() instanceof IDamageable) {
                    IDamageable damageable = (IDamageable) state.getBlock();
                    damageable.onBlockDamaged(this.world, state, pos, 2);
                } else {
                    dropItem();
                }
                this.world.setEntityState(this, (byte) 3);
                this.setDead();
            } else if (result.entityHit instanceof EntityLivingBase) {
                result.entityHit.attackEntityFrom(DamageSource.causeThrownDamage(this, this.getThrower()), (float) 2);
            } else {
                dropItem();
            }
        }
    }

    private void dropItem() {
        EntityItem entityItem = new EntityItem(this.world, this.posX, this.posY - 0.45, this.posZ, new ItemStack(ModItems.BRICK));
        this.world.spawnEntity(entityItem);
    }
}
