package com.cubgdev.cubga.tileentity;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.tileentity.TileEntity;

/**
 * Author: CoffeeCatRailway
 */
public class TileEntityCrystalContainer extends TileEntity {

    private int coordX, coordY, coordZ;
    private boolean renderBeam;

    public float innerRotation = 0.0f;

    public int getCoordX() {
        return coordX;
    }

    public int getCoordY() {
        return coordY;
    }

    public int getCoordZ() {
        return coordZ;
    }

    public boolean RenderBeam() {
        return renderBeam;
    }

    @Override
    public NBTTagCompound writeToNBT(NBTTagCompound compound) {
        compound.setInteger("coordX", 0);
        compound.setInteger("coordY", 0);
        compound.setInteger("coordZ", 0);

        compound.setBoolean("renderBeam", false);
        return super.writeToNBT(compound);
    }

    @Override
    public void readFromNBT(NBTTagCompound compound) {
        super.readFromNBT(compound);
        this.coordX = compound.getInteger("coordX");
        this.coordY = compound.getInteger("coordY");
        this.coordZ = compound.getInteger("coordZ");

        this.renderBeam = compound.getBoolean("renderBeam");
    }
}
