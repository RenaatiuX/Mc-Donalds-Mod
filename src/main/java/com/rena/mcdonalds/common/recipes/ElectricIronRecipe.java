package com.rena.mcdonalds.common.recipes;

import com.google.gson.JsonObject;
import com.rena.mcdonalds.core.init.RecipeInit;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.*;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.JSONUtils;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import net.minecraftforge.registries.ForgeRegistryEntry;

import javax.annotation.Nullable;

public class ElectricIronRecipe implements IRecipe<IInventory> {

    public static final Serializer SERIALIZER = new Serializer();

    private final Ingredient input;
    private final ItemStack output, burnt;
    private final int burnTime;
    private final ResourceLocation id;

    public ElectricIronRecipe(Ingredient input, ItemStack output, ItemStack burnt, int burnTime, ResourceLocation id) {
        this.input = input;
        this.output = output;
        this.burnt = burnt;
        this.burnTime = burnTime;
        this.id = id;
    }

    @Override
    public boolean matches(IInventory inv, World worldIn) {
        return input.test(inv.getStackInSlot(0)) || inv.getStackInSlot(0).getItem() == output.getItem();
    }

    @Override
    public ItemStack getCraftingResult(IInventory inv) {
        return this.output;
    }

    public Ingredient getInput() {
        return input;
    }

    public ItemStack getBurnt() {
        return burnt;
    }

    public int getBurnTime() {
        return burnTime;
    }

    @Override
    public boolean canFit(int width, int height) {
        return false;
    }

    @Override
    public ItemStack getRecipeOutput() {
        return this.output.copy();
    }

    @Override
    public ResourceLocation getId() {
        return this.id;
    }

    @Override
    public IRecipeSerializer<?> getSerializer() {
        return SERIALIZER;
    }

    @Override
    public IRecipeType<?> getType() {
        return RecipeInit.ELECTRIC_IRON_RECIPE;
    }

    private static final class Serializer extends ForgeRegistryEntry<IRecipeSerializer<?>> implements IRecipeSerializer<ElectricIronRecipe>{

        @Override
        public ElectricIronRecipe read(ResourceLocation recipeId, JsonObject json) {
            Ingredient input = Ingredient.deserialize(ChoppingRecipe.getJsonElement(json, "input"));
            ItemStack output = ShapedRecipe.deserializeItem(JSONUtils.getJsonObject(json, "output"));
            ItemStack burnt = ShapedRecipe.deserializeItem(JSONUtils.getJsonObject(json, "burnt"));
            int burnTime = JSONUtils.getInt(json, "burnTime", 200);
            return new ElectricIronRecipe(input, output, burnt, burnTime,recipeId);
        }

        @Nullable
        @Override
        public ElectricIronRecipe read(ResourceLocation recipeId, PacketBuffer buffer) {
            Ingredient input = Ingredient.read(buffer);
            ItemStack output = buffer.readItemStack();
            ItemStack burnt = buffer.readItemStack();
            int burnTime = buffer.readInt();
            return new ElectricIronRecipe(input, output, burnt, burnTime, recipeId);
        }

        @Override
        public void write(PacketBuffer buffer, ElectricIronRecipe recipe) {
            recipe.input.write(buffer);
            buffer.writeItemStack(recipe.output);
            buffer.writeItemStack(recipe.burnt);
            buffer.writeInt(recipe.burnTime);
        }
    }
}
