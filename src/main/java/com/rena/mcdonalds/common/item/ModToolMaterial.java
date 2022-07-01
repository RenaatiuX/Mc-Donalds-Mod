package com.rena.mcdonalds.common.item;

import net.minecraft.item.IItemTier;
import net.minecraft.item.Item;
import net.minecraft.item.Items;
import net.minecraft.item.crafting.Ingredient;

import java.util.function.Supplier;

public enum ModToolMaterial implements IItemTier {
    KNIFE(200, 1, 15, 2f,1f, () -> Ingredient.fromItems(Items.IRON_INGOT));

    private final int maxUses, harvestLevel, enchantability;
    private final float efficiency, attackDamage;
    private final Supplier<Ingredient> repairMaterial;

    ModToolMaterial(int maxUses, int harvestLevel, int enchantability, float efficiency, float attackDamage, Supplier<Ingredient> repairMaterial) {
        this.maxUses = maxUses;
        this.harvestLevel = harvestLevel;
        this.enchantability = enchantability;
        this.efficiency = efficiency;
        this.attackDamage = attackDamage;
        this.repairMaterial = repairMaterial;
    }

    @Override
    public int getMaxUses() {
        return maxUses;
    }

    @Override
    public float getEfficiency() {
        return efficiency;
    }

    @Override
    public float getAttackDamage() {
        return attackDamage;
    }

    @Override
    public int getHarvestLevel() {
        return harvestLevel;
    }

    @Override
    public int getEnchantability() {
        return enchantability;
    }

    @Override
    public Ingredient getRepairMaterial() {
        return repairMaterial.get();
    }
}
