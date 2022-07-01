package com.rena.mcdonalds.core.init;

import com.rena.mcdonalds.McDonalds;
import com.rena.mcdonalds.common.item.ModToolMaterial;
import com.rena.mcdonalds.common.tab.McDonaldsTab;
import net.minecraft.item.Item;
import net.minecraft.item.SwordItem;
import net.minecraft.util.registry.Registry;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class ItemInt {

    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, McDonalds.MOD_ID);

    public static final RegistryObject<SwordItem> KNIFE = ITEMS.register("knife", () -> new SwordItem(ModToolMaterial.KNIFE, 3, -2.5f, new Item.Properties().group(McDonaldsTab.MC_DONALDS_TAB)));

    public static final RegistryObject<Item> MEAT_10_1 = ITEMS.register("meat_10_1",
            ()-> new Item(new Item.Properties().group(McDonaldsTab.MC_DONALDS_TAB)));
    public static final RegistryObject<Item> COOKED_MEAT_10_1 = ITEMS.register("cooked_meat_10_1",
            ()-> new Item(new Item.Properties().group(McDonaldsTab.MC_DONALDS_TAB)));
    public static final RegistryObject<Item> BURNT_MEAT_10_1 = ITEMS.register("burnt_meat_10_1",
            ()-> new Item(new Item.Properties().group(McDonaldsTab.MC_DONALDS_TAB)));

    public static final RegistryObject<Item> BREAD_CROWN = ITEMS.register("bread_crown",
            ()-> new Item(new Item.Properties().group(McDonaldsTab.MC_DONALDS_TAB)));
    public static final RegistryObject<Item> BREAD_MIDDLEJ = ITEMS.register("bread_middle",
            ()-> new Item(new Item.Properties().group(McDonaldsTab.MC_DONALDS_TAB)));
    public static final RegistryObject<Item> BREAD_BASE = ITEMS.register("bread_base",
            ()-> new Item(new Item.Properties().group(McDonaldsTab.MC_DONALDS_TAB)));
    public static final RegistryObject<Item> BREAD_SIMPLE = ITEMS.register("bread_simple",
            ()-> new Item(new Item.Properties().group(McDonaldsTab.MC_DONALDS_TAB)));

    public static final RegistryObject<Item> AMERICAN_CHEESE = ITEMS.register("american_cheese",
            ()-> new Item(new Item.Properties().group(McDonaldsTab.MC_DONALDS_TAB)));
    public static final RegistryObject<Item> BIG_MAC_SAUCE = ITEMS.register("big_mac_sauce",
            ()-> new Item(new Item.Properties().group(McDonaldsTab.MC_DONALDS_TAB)));

    public static final RegistryObject<Item> PICKLES = ITEMS.register("pickles",
            ()-> new Item(new Item.Properties().group(McDonaldsTab.MC_DONALDS_TAB)));
    public static final RegistryObject<Item> ONION = ITEMS.register("onion",
            ()-> new Item(new Item.Properties().group(McDonaldsTab.MC_DONALDS_TAB)));
    public static final RegistryObject<Item> ICEBERG_LETTUCE = ITEMS.register("iceberg_lettuce",
            ()-> new Item(new Item.Properties().group(McDonaldsTab.MC_DONALDS_TAB)));
    public static final RegistryObject<Item> TOMATO = ITEMS.register("tomato",
            ()-> new Item(new Item.Properties().group(McDonaldsTab.MC_DONALDS_TAB)));

    public static final RegistryObject<Item> MAYONNAISE = ITEMS.register("mayonnaise",
            ()-> new Item(new Item.Properties().group(McDonaldsTab.MC_DONALDS_TAB)));
    public static final RegistryObject<Item> MUSTARD = ITEMS.register("mustard",
            ()-> new Item(new Item.Properties().group(McDonaldsTab.MC_DONALDS_TAB)));
    public static final RegistryObject<Item> KETCHUP = ITEMS.register("ketchup",
            ()-> new Item(new Item.Properties().group(McDonaldsTab.MC_DONALDS_TAB)));

    public static final RegistryObject<Item> FRIES = ITEMS.register("fries",
            ()-> new Item(new Item.Properties().group(McDonaldsTab.MC_DONALDS_TAB)));
    public static final RegistryObject<Item> FRIES_WITH_BOX = ITEMS.register("fries_with_box",
            ()-> new Item(new Item.Properties().group(McDonaldsTab.MC_DONALDS_TAB)));
    public static final RegistryObject<Item> BOX = ITEMS.register("box",
            ()-> new Item(new Item.Properties().group(McDonaldsTab.MC_DONALDS_TAB)));
    public static final RegistryObject<Item> STRIPPED_POTATOES = ITEMS.register("stripped_potatoes", () -> new Item(new Item.Properties().group(McDonaldsTab.MC_DONALDS_TAB)));

}
