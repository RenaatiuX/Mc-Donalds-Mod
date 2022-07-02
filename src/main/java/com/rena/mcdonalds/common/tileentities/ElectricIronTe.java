package com.rena.mcdonalds.common.tileentities;

import com.rena.mcdonalds.common.recipes.ElectricIronRecipe;
import com.rena.mcdonalds.core.McDonaldsConfig;
import com.rena.mcdonalds.core.init.RecipeInit;
import com.rena.mcdonalds.core.init.TileEntityInit;
import net.minecraft.block.BlockState;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Inventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.server.SUpdateTileEntityPacket;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.tileentity.ITickableTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.common.util.Constants;

import javax.annotation.Nullable;

public class ElectricIronTe extends TileEntity implements ITickableTileEntity {

    public static final int MAX_DEGREES = 60;

    private IInventory inv = new Inventory(1);
    private boolean closed = false, closing = false, opening = false;
    private int counter = 0, maxCounter = 0, burnCounter = 0, maxBurnCounter = 0;
    private float degrees = MAX_DEGREES;

    public ElectricIronTe() {
        super(TileEntityInit.ELECTRIC_IRON_TE.get());
    }

    @Override
    public void tick() {
        if (!world.isRemote) {
            if (closing) {
                if (degrees > 0) {
                    degrees -= 1f;
                } else {
                    degrees = 0;
                    closing = false;
                    closed = true;
                }
                blockUpdate();
            }else if(opening){
                if (degrees < MAX_DEGREES) {
                    degrees += 1f;
                } else {
                    degrees = MAX_DEGREES;
                    opening = false;
                    closed = false;
                }
                blockUpdate();
            }
            if (this.closed) {
                ElectricIronRecipe recipe = getRecipe();
                if (recipe != null) {
                    if (this.maxBurnCounter > 0) {
                        if (inv.getStackInSlot(0).isEmpty())
                            resetBurn();
                        else {
                            this.burnCounter++;
                            if (this.maxBurnCounter < burnCounter) {
                                finishBurning(recipe);
                            }
                        }
                    } else {
                        if (counter <= 0) {
                            startWorking(recipe);
                            counter++;
                        } else {
                            work(recipe);
                        }
                        if (counter > this.maxCounter) {
                            finishWork(recipe);
                        }
                    }
                } else {
                    reset();
                    resetBurn();
                    blockUpdate();
                }
            }
        }
    }

    private void blockUpdate() {
        world.notifyBlockUpdate(pos, getBlockState(), getBlockState(), Constants.BlockFlags.BLOCK_UPDATE);
    }

    public boolean isOpening() {
        return opening;
    }

    public float getDegrees() {
        return degrees;
    }

    private void startWorking(ElectricIronRecipe recipe) {
        this.maxCounter = recipe.getBurnTime();
        world.notifyBlockUpdate(pos, getBlockState(), getBlockState(), Constants.BlockFlags.BLOCK_UPDATE);
    }

    private void work(ElectricIronRecipe recipe) {
        this.counter++;
        for (int i = 0; i < 30; i++) {
            world.addParticle(ParticleTypes.SMOKE, getPos().getX() + world.rand.nextDouble() - 0.5, getPos().getY() + 1, getPos().getZ() + world.rand.nextDouble() - 0.5, 0, 0.01, 0);
        }
    }

    private void finishWork(ElectricIronRecipe recipe) {
        this.inv.setInventorySlotContents(0, recipe.getRecipeOutput());
        int maxBurnCounter = McDonaldsConfig.MACHINE_CONFIG.timeToBurn.get();
        reset();
        if (maxBurnCounter > 0) {
            this.maxBurnCounter = maxBurnCounter;
        }
        world.notifyBlockUpdate(pos, getBlockState(), getBlockState(), Constants.BlockFlags.BLOCK_UPDATE);
    }

    private void finishBurning(ElectricIronRecipe recipe) {
        this.inv.setInventorySlotContents(0, recipe.getBurnt());
        resetBurn();
        world.notifyBlockUpdate(pos, getBlockState(), getBlockState(), Constants.BlockFlags.BLOCK_UPDATE);
    }

    private void reset() {
        counter = 0;
        this.maxCounter = 0;
        this.opening = true;
    }

    private void resetBurn() {
        this.burnCounter = 0;
        this.maxBurnCounter = 0;
    }

    public void setClosing() {
        this.closing = true;
    }

    @Nullable
    @Override
    public SUpdateTileEntityPacket getUpdatePacket() {
        return new SUpdateTileEntityPacket(this.getPos(), 0, write(new CompoundNBT()));
    }

    @Override
    public void onDataPacket(NetworkManager net, SUpdateTileEntityPacket pkt) {
        this.read(null, pkt.getNbtCompound());
    }

    @Override
    public CompoundNBT getUpdateTag() {
        return this.write(new CompoundNBT());
    }

    public boolean isClosing() {
        return closing;
    }

    public boolean isClosed() {
        return closed;
    }

    @Override
    public CompoundNBT write(CompoundNBT compound) {
        CompoundNBT inventory = new CompoundNBT();
        for (int i = 0; i < this.inv.getSizeInventory(); i++) {
            CompoundNBT stack = this.inv.getStackInSlot(i).write(new CompoundNBT());
            inventory.put("stack" + i, stack);
        }
        compound.put("inventory", inventory);
        compound.putInt("counter", this.counter);
        compound.putInt("maxCounter", this.maxCounter);
        compound.putInt("burnCounter", this.burnCounter);
        compound.putInt("maxBurnCounter", this.maxBurnCounter);
        compound.putBoolean("closed", this.closed);
        compound.putBoolean("closing", this.closing);
        compound.putFloat("degrees", this.degrees);
        compound.putBoolean("opening", this.opening);
        return super.write(compound);
    }

    @Override
    public void read(BlockState state, CompoundNBT nbt) {
        super.read(state, nbt);
        CompoundNBT inventory = nbt.getCompound("inventory");
        for (int i = 0; inventory.contains("stack" + i); i++) {
            this.inv.setInventorySlotContents(i, ItemStack.read(inventory.getCompound("stack" + i)));
        }
        this.closed = nbt.getBoolean("closed");
        this.counter = nbt.getInt("counter");
        this.maxCounter = nbt.getInt("maxCounter");
        this.burnCounter = nbt.getInt("burnCounter");
        this.maxBurnCounter = nbt.getInt("maxBurnCounter");
        this.closing = nbt.getBoolean("closing");
        this.degrees = nbt.getFloat("degrees");
        this.opening = nbt.getBoolean("opening");
    }

    @Nullable
    private ElectricIronRecipe getRecipe() {
        return this.world.getRecipeManager().getRecipe(RecipeInit.ELECTRIC_IRON_RECIPE, this.inv, this.world).orElse(null);
    }

    public IInventory getInv() {
        return inv;
    }
}
