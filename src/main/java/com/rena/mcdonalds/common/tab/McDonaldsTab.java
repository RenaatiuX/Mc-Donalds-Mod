package com.rena.mcdonalds.common.tab;

import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;

public class McDonaldsTab {

    public static final ItemGroup MC_DONALDS_TAB = new ItemGroup("mcdonaldsTab") {
        @Override
        public ItemStack createIcon() {
            return new ItemStack(Items.APPLE);
        }
    };

}
