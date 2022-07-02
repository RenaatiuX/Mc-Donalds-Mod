package com.rena.mcdonalds.core;

import net.minecraftforge.common.ForgeConfig;
import net.minecraftforge.common.ForgeConfigSpec;

public class McDonaldsConfig {

    public static MachineConfig MACHINE_CONFIG;

    public static ForgeConfigSpec init(ForgeConfigSpec.Builder builder){
        MACHINE_CONFIG = new MachineConfig(builder);

        return builder.build();
    }

    public static class MachineConfig{

        public ForgeConfigSpec.IntValue timeToBurn;

        public MachineConfig(ForgeConfigSpec.Builder builder){
            builder.push("machines");
            builder.comment("how much ticks until a finished meat will then be burnt");
            builder.comment("place -1 so things will never burn");
            timeToBurn = builder.defineInRange("timeToBurn", 200, -2, Integer.MAX_VALUE);
        }
    }
}
