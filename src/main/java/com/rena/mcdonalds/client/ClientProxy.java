package com.rena.mcdonalds.client;

import com.rena.mcdonalds.core.ServerProxy;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.eventbus.api.IEventBus;

@OnlyIn(Dist.CLIENT)
public class ClientProxy extends ServerProxy {

    @Override
    public void init(IEventBus modbus) {
        super.init(modbus);

        modbus.register(MDModels.class);
    }
}
