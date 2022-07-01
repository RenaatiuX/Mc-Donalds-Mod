package com.rena.mcdonalds.core.init;

import com.rena.mcdonalds.McDonalds;
import com.rena.mcdonalds.common.block.ChoppingBoard;
import com.rena.mcdonalds.common.tab.McDonaldsTab;
import net.minecraft.block.Block;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.function.Supplier;

public class BlockInit {

    public static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS, McDonalds.MOD_ID);

    public static final RegistryObject<ChoppingBoard> CHOPPING_BOEARD = registerBlock("chopping_board", ChoppingBoard::new, () -> new Item.Properties().group(McDonaldsTab.MC_DONALDS_TAB));


    /**
     * registers a block with its blockitem
     */
    public static final <T extends Block> RegistryObject<T> registerBlock(String name, Supplier<T> blockSupplier, Supplier<Item.Properties> itemProperties){
        RegistryObject<T> block = BLOCKS.register(name, blockSupplier);
        ItemInt.ITEMS.register(name, () -> new BlockItem(block.get(), itemProperties.get()));
        return block;
    }
}
