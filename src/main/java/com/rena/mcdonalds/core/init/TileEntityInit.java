package com.rena.mcdonalds.core.init;

import com.rena.mcdonalds.McDonalds;
import com.rena.mcdonalds.common.tileentities.ChoppingBoardTe;
import net.minecraft.tileentity.TileEntityType;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class TileEntityInit {

    public static final DeferredRegister<TileEntityType<?>> TES = DeferredRegister.create(ForgeRegistries.TILE_ENTITIES, McDonalds.MOD_ID);

    public static final RegistryObject<TileEntityType<ChoppingBoardTe>> CHOPPING_BOEARD_TE = TES.register("chopping_boeard",
            () -> TileEntityType.Builder.create(ChoppingBoardTe::new, BlockInit.CHOPPING_BOEARD.get()).build(null));

}
