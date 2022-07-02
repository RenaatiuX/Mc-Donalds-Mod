package com.rena.mcdonalds.common.block;

import com.rena.mcdonalds.common.recipes.ElectricIronRecipe;
import com.rena.mcdonalds.common.tileentities.ElectricIronTe;
import com.rena.mcdonalds.common.util.WorldUtils;
import com.rena.mcdonalds.core.init.RecipeInit;
import net.java.games.input.Keyboard;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.BlockState;
import net.minecraft.block.material.Material;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.Inventory;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;
import net.minecraftforge.common.ToolType;
import net.minecraftforge.common.util.Constants;

import javax.annotation.Nullable;

public class ElectricIron extends RotatableBlock{
    public ElectricIron() {
        super(AbstractBlock.Properties.create(Material.IRON ).harvestLevel(1).harvestTool(ToolType.PICKAXE).hardnessAndResistance(5f).notSolid());
    }


    @Override
    public boolean hasTileEntity(BlockState state) {
        return true;
    }

    @Override
    public ActionResultType onBlockActivated(BlockState state, World worldIn, BlockPos pos, PlayerEntity player, Hand handIn, BlockRayTraceResult hit) {
        if (!worldIn.isRemote){
            ItemStack held = player.getHeldItem(Hand.MAIN_HAND);
            ElectricIronRecipe recipe = worldIn.getRecipeManager().getRecipe(RecipeInit.ELECTRIC_IRON_RECIPE, new Inventory(held), worldIn).orElse(null);
            ElectricIronTe te = WorldUtils.getTileEntity(ElectricIronTe.class, worldIn, pos);
            if (te != null){
                System.out.println(player.isSneaking());
                if (player.isSneaking() && !te.isClosed() && !te.isClosing() && !te.isOpening()){
                    te.setClosing();
                    worldIn.notifyBlockUpdate(pos, state, state, Constants.BlockFlags.BLOCK_UPDATE);
                }else if (held.isEmpty() && !te.isClosed()){
                    player.addItemStackToInventory(te.getInv().getStackInSlot(0));
                    te.getInv().setInventorySlotContents(0, ItemStack.EMPTY);
                    worldIn.notifyBlockUpdate(pos, state, state, Constants.BlockFlags.BLOCK_UPDATE);
                }else if(recipe != null && recipe.getInput().test(held) && te.getInv().getStackInSlot(0).isEmpty()){
                    ItemStack copy = held.copy();
                    held.shrink(1);
                    copy.setCount(1);
                    te.getInv().setInventorySlotContents(0, copy);
                    worldIn.notifyBlockUpdate(pos, state, state, Constants.BlockFlags.BLOCK_UPDATE);
                }
            }
        }
        return super.onBlockActivated(state, worldIn, pos, player, handIn, hit);
    }

    @Nullable
    @Override
    public TileEntity createTileEntity(BlockState state, IBlockReader world) {
        return new ElectricIronTe();
    }
}
