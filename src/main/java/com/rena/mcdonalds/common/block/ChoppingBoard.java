package com.rena.mcdonalds.common.block;

import com.rena.mcdonalds.common.recipes.ChoppingRecipe;
import com.rena.mcdonalds.common.tileentities.ChoppingBoardTe;
import com.rena.mcdonalds.common.util.WorldUtils;
import com.rena.mcdonalds.core.init.ItemInt;
import com.rena.mcdonalds.core.init.RecipeInit;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.material.Material;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Inventory;
import net.minecraft.inventory.InventoryHelper;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.math.shapes.IBooleanFunction;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.util.math.shapes.VoxelShapes;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.ToolType;
import net.minecraftforge.common.util.Constants;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

import javax.annotation.Nullable;

public class ChoppingBoard extends ShapedBlock {
    public ChoppingBoard() {
        super(AbstractBlock.Properties.create(Material.WOOD).harvestTool(ToolType.AXE).notSolid().hardnessAndResistance(2, 3), makeShape());
        MinecraftForge.EVENT_BUS.register(this);
    }

    @Override
    public boolean hasTileEntity(BlockState state) {
        return true;
    }

    @Nullable
    @Override
    public TileEntity createTileEntity(BlockState state, IBlockReader world) {
        return new ChoppingBoardTe();
    }

    @Override
    public ActionResultType onBlockActivated(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockRayTraceResult hit) {
        if (!world.isRemote){
            ChoppingBoardTe te = WorldUtils.getTileEntity(ChoppingBoardTe.class, world, pos);
            if (te != null){
                IInventory inv = te.getInv();
                if (inv.getStackInSlot(0).isEmpty()){
                    ItemStack stack = player.getHeldItem(hand);
                    if (!stack.isEmpty() && getRecipe(world, new Inventory(stack)) != null){
                        ItemStack copy = stack.copy();
                        copy.setCount(1);
                        inv.setInventorySlotContents(0, copy);
                        stack.shrink(1);
                    }
                }else{
                    if (player.getHeldItem(Hand.MAIN_HAND).isEmpty()){
                        if (player.addItemStackToInventory(inv.getStackInSlot(0))) {
                            inv.setInventorySlotContents(0, ItemStack.EMPTY);
                        }
                    }
                }
                world.notifyBlockUpdate(pos, state, state, Constants.BlockFlags.BLOCK_UPDATE);
            }
        }
        return super.onBlockActivated(state, world, pos, player, hand, hit);
    }

    @Nullable
    private static ChoppingRecipe getRecipe(World world, IInventory inv){
        return world.getRecipeManager().getRecipe(RecipeInit.CHOPPING_RECIPE, inv, world).orElse(null);
    }

    @Override
    public void onReplaced(BlockState state, World worldIn, BlockPos pos, BlockState newState, boolean isMoving) {
        if (!worldIn.isRemote && !state.matchesBlock(newState.getBlock())){
            ChoppingBoardTe te = WorldUtils.getTileEntity(ChoppingBoardTe.class, worldIn, pos);
            InventoryHelper.dropInventoryItems(worldIn, pos, te.getInv());
        }
        super.onReplaced(state, worldIn, pos, newState, isMoving);
    }

    @SubscribeEvent
    public void onLeftClick(PlayerInteractEvent.LeftClickBlock event){
        if (event.getWorld().getBlockState(event.getPos()).getBlock() == this){
            handleKnifeChop(event.getWorld().getBlockState(event.getPos()), event.getWorld(), event.getPos(), event.getPlayer());
        }
    }

    private static void handleKnifeChop(BlockState state, World world, BlockPos pos, PlayerEntity player){
        System.out.println("clicked");
        if (!world.isRemote && player.getHeldItem(Hand.MAIN_HAND).getItem() == ItemInt.KNIFE.get()){
            ChoppingBoardTe te = WorldUtils.getTileEntity(ChoppingBoardTe.class, world, pos);
            ChoppingRecipe recipe = getRecipe(world, te.getInv());
            if (recipe != null){
                te.getInv().setInventorySlotContents(0, recipe.getRecipeOutput().copy());
                player.getHeldItem(Hand.MAIN_HAND).damageItem(1, player, p -> p.sendBreakAnimation(Hand.MAIN_HAND));
            }
        }
    }

    public static VoxelShape makeShape(){
        VoxelShape shape = VoxelShapes.empty();
        shape = VoxelShapes.combineAndSimplify(shape, VoxelShapes.create(0.4375, 0, 0.0625, 0.5625, 0.0625, 0.125), IBooleanFunction.OR);
        shape = VoxelShapes.combineAndSimplify(shape, VoxelShapes.create(0.1875, 0, 0.0625, 0.4375, 0.0625, 0.1875), IBooleanFunction.OR);
        shape = VoxelShapes.combineAndSimplify(shape, VoxelShapes.create(0.5625, 0, 0.0625, 0.8125, 0.0625, 0.1875), IBooleanFunction.OR);
        shape = VoxelShapes.combineAndSimplify(shape, VoxelShapes.create(0.1875, 0, 0.1875, 0.8125, 0.0625, 0.9375), IBooleanFunction.OR);

        return shape;
    }
}
