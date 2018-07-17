package com.cubgdev.cubga.tileentity;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;

/**
 * Author: CoffeeCatRailway
 */
public class TileEntityCrystalContainer extends TileEntity {

    public float innerRotation = 0.0f;

    private float speed = 1.0f;
    private float scale = 1.0f;

    public float getSpeed() {
        return speed;
    }

    public float getScale() {
        return scale;
    }

    @Override
    public NBTTagCompound writeToNBT(NBTTagCompound compound) {
        compound.setFloat("speed", this.speed);
        compound.setFloat("scale", this.scale);
        return super.writeToNBT(compound);
    }

    @Override
    public void readFromNBT(NBTTagCompound compound) {
        super.readFromNBT(compound);
        this.speed = compound.getFloat("speed");
        this.scale = compound.getFloat("scale");
    }
}
