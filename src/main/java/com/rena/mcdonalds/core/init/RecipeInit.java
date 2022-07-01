package com.rena.mcdonalds.core.init;

import com.rena.mcdonalds.McDonalds;
import com.rena.mcdonalds.common.recipes.ChoppingRecipe;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.item.crafting.IRecipeSerializer;
import net.minecraft.item.crafting.IRecipeType;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.registry.Registry;
import net.minecraftforge.event.RegistryEvent;

public class RecipeInit {

    public static final IRecipeType<ChoppingRecipe> CHOPPING_RECIPE = create("chopping_recipe");

    public static final void registerRecipes(RegistryEvent.Register<IRecipeSerializer<?>> event){
        registerRecipe(event, ChoppingRecipe.SERIALIZER, CHOPPING_RECIPE);
    }

    private static void registerRecipe(RegistryEvent.Register<IRecipeSerializer<?>> event, IRecipeSerializer<?> serializer, IRecipeType<?> type){
        Registry.register(Registry.RECIPE_TYPE, type.toString(), type);
        event.getRegistry().register(serializer.setRegistryName(new ResourceLocation(type.toString())));
    }

    private static <T extends IRecipe<?>> IRecipeType<T> create(final String key) {
        return new IRecipeType<T>() {
            public String toString() {
                return McDonalds.modLoc(key).toString();
            }
        };
    }
}
