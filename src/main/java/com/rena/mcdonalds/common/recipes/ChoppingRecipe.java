package com.rena.mcdonalds.common.recipes;

import com.google.gson.JsonElement;
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

public class ChoppingRecipe implements IRecipe<IInventory> {

    public static final Serializer SERIALIZER = new Serializer();
    private final Ingredient toChop;
    private final ItemStack output;
    private final ResourceLocation id;

    public ChoppingRecipe(Ingredient toChop, ItemStack output, ResourceLocation id) {
        this.toChop = toChop;
        this.output = output;
        this.id = id;
    }

    @Override
    public boolean matches(IInventory inv, World worldIn) {
        return this.toChop.test(inv.getStackInSlot(0));
    }

    @Override
    public ItemStack getCraftingResult(IInventory inv) {
        return this.output.copy();
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
        return RecipeInit.CHOPPING_RECIPE;
    }

    public static JsonElement getJsonElement(JsonObject obj, String name) {
        return JSONUtils.isJsonArray(obj, name) ? JSONUtils.getJsonArray(obj, name)
                : JSONUtils.getJsonObject(obj, name);
    }

    private static final class Serializer extends ForgeRegistryEntry<IRecipeSerializer<?>> implements IRecipeSerializer<ChoppingRecipe>{

        @Override
        public ChoppingRecipe read(ResourceLocation recipeId, JsonObject json) {
            Ingredient input = Ingredient.deserialize(getJsonElement(json, "input"));
            ItemStack output =  ShapedRecipe.deserializeItem(JSONUtils.getJsonObject(json, "output"));
            return new ChoppingRecipe(input, output, recipeId);
        }

        @Nullable
        @Override
        public ChoppingRecipe read(ResourceLocation recipeId, PacketBuffer buffer) {
            Ingredient toChop = Ingredient.read(buffer);
            ItemStack output = buffer.readItemStack();
            return new ChoppingRecipe(toChop, output, recipeId);
        }

        @Override
        public void write(PacketBuffer buffer, ChoppingRecipe recipe) {
            recipe.toChop.write(buffer);
            buffer.writeItemStack(recipe.output);
        }
    }
}
