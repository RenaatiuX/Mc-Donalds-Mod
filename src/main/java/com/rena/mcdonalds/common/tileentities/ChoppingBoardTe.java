package com.rena.mcdonalds.common.tileentities;

import com.rena.mcdonalds.core.init.TileEntityInit;
import net.minecraft.block.BlockState;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Inventory;
import net.minecraft.inventory.InventoryHelper;
import net.minecraft.inventory.ItemStackHelper;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.server.SUpdateTileEntityPacket;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityType;

import javax.annotation.Nullable;

public class ChoppingBoardTe extends TileEntity {
    private IInventory inv = new Inventory(1);

    public ChoppingBoardTe() {
        super(TileEntityInit.CHOPPING_BOEARD_TE.get());
    }

    public IInventory getInv() {
        return inv;
    }

    @Override
    public CompoundNBT getUpdateTag() {
        return this.write(new CompoundNBT());
    }

    @Override
    public void onDataPacket(NetworkManager net, SUpdateTileEntityPacket pkt) {
       this.read(null, pkt.getNbtCompound());
    }

    @Nullable
    @Override
    public SUpdateTileEntityPacket getUpdatePacket() {
        return new SUpdateTileEntityPacket(this.getPos(), 0, this.write(new CompoundNBT()));
    }

    @Override
    public CompoundNBT write(CompoundNBT nbt) {
        CompoundNBT inventory = new CompoundNBT();
        for (int i = 0; i < this.inv.getSizeInventory(); i++) {
            CompoundNBT stack = this.inv.getStackInSlot(i).write(new CompoundNBT());
            inventory.put("stack" + i, stack);
        }
        nbt.put("inventory", inventory);
        return super.write(nbt);
    }

    @Override
    public void read(BlockState state, CompoundNBT nbt) {
        CompoundNBT inventory = nbt.getCompound("inventory");
        for (int i = 0; inventory.contains("stack" + i); i++) {
            this.inv.setInventorySlotContents(i, ItemStack.read(inventory.getCompound("stack" + i)));
        }
        super.read(state, nbt);
    }
}
