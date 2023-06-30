package com.cutemouse.hello_mod.Blocks;

import com.cutemouse.hello_mod.Blocks.entity.IronBucketBlockEntity;
import com.cutemouse.hello_mod.Main;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import javax.swing.*;

public class BlockEntityRegistry {

    public static final DeferredRegister<BlockEntityType<?>> BLOCK_ENTITIES =
            DeferredRegister.create(ForgeRegistries.BLOCK_ENTITIES,Main.MOD_ID);

    //注册方块实体，并将方块实体与对应的方块绑定。
    public static final RegistryObject<BlockEntityType<IronBucketBlockEntity>> IRON_BUCKET_BLOCK_ENTITY =
            BLOCK_ENTITIES.register("iron_bucket",
                    () -> BlockEntityType.Builder.of(IronBucketBlockEntity::new, BlockRegistry.IRON_BUCKET.get()).build(null));
}
