package com.cubgdev.cubga.entity;

import com.cubgdev.cubga.init.ModItems;
import com.mrcrayfish.guns.entity.EntityProjectile;
import com.mrcrayfish.guns.interfaces.IDamageable;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.projectile.EntityThrowable;
import net.minecraft.item.ItemStack;
import net.minecraft.util.DamageSource;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.World;

import java.util.Random;

public class EntityThrowableBrick extends EntityThrowable {

    private ItemStack brick = new ItemStack(ModItems.BRICK);

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
            this.world.setEntityState(this, (byte) 3);
            this.setDead();

            if (result.typeOfHit == RayTraceResult.Type.BLOCK) {
                BlockPos pos = result.getBlockPos();
                IBlockState state = this.world.getBlockState(pos);

                if (state.getBlock() instanceof IDamageable) {
                    IDamageable damageable = (IDamageable) state.getBlock();

                    EntityProjectile projectile = new EntityProjectile(this.world);
                    projectile.setDamageModifier(1F + new Random().nextInt(1)); // Damage will be random 1 to 2
                    this.world.spawnEntity(projectile);

                    damageable.onProjectileDamaged(this.world, state, pos, projectile);
                } else {
                    doSpawnItem();
                }
            } else {
                if (result.entityHit instanceof EntityPlayer) {
                    result.entityHit.attackEntityFrom(DamageSource.causeThrownDamage(this, this.getThrower()), (float) 2);
                } else {
                    doSpawnItem();
                }
            }
        }
    }

    private void doSpawnItem() {
        EntityItem entityItem = new EntityItem(this.world, this.posX, this.posY, this.posZ, brick);
        this.world.spawnEntity(entityItem);
    }
}
