package com.cutemouse.hello_mod.Blocks;

import com.cutemouse.hello_mod.Main;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SandBlock;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.Material;
import net.minecraft.world.level.material.MaterialColor;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class BlockRegistry {

    public static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS, Main.MOD_ID);

    public static final RegistryObject<Block> FERROUS_IRON_BLOCK = BLOCKS.register("ferrous_iron_block",
            () -> new Block(BlockBehaviour.Properties.of(Material.METAL, MaterialColor.METAL)
            .strength(0.5F,0.5F).sound(SoundType.METAL)));

    public static final RegistryObject<Block> MINT_SAND = BLOCKS.register("mint_sand",
            () -> {return new SandBlock(14406560,BlockBehaviour.Properties.of(Material.SAND,MaterialColor.SAND)
            .strength(0.5F).sound(SoundType.SAND));});
}
