package com.cutemouse.hello_mod.world.gen;

import net.minecraft.data.worldgen.placement.OrePlacements;
import net.minecraft.world.level.biome.BiomeGenerationSettings;
import net.minecraft.world.level.levelgen.GenerationStep;
import net.minecraftforge.event.world.BiomeLoadingEvent;

public class OreGeneration {

    public static void generateOres(final BiomeLoadingEvent event){


    }

    private static void oreGenerate(BiomeGenerationSettings.Builder settings){

        settings.addFeature(GenerationStep.Decoration.UNDERGROUND_ORES, OrePlacements.ORE_IRON_UPPER);
    }

    //public BiomeGenerationSettings.Builder addFeature(GenerationStep.Decoration p_204202_, Holder<PlacedFeature> p_204203_)

    /*public static final Holder<PlacedFeature> ORE_PINK =
            PlacementUtils.register("ore_pink", OreFeatures.ORE_DIAMOND_SMALL, )*/

    //   public static final Holder<PlacedFeature> ORE_DIAMOND =
    //   PlacementUtils.register("ore_diamond", OreFeatures.ORE_DIAMOND_SMALL, commonOrePlacement(7, HeightRangePlacement.triangle(VerticalAnchor.aboveBottom(-80), VerticalAnchor.aboveBottom(80))));
}
