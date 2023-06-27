package com.cutemouse.hello_mod.Tabs;

import com.cutemouse.hello_mod.Items.ItemRegistry;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;

public class CreativeModTabs {

    public static final CreativeModeTab HELLO_MOD_TOOLS = new CreativeModeTab("hello_mod_tools") {
        @Override
        public ItemStack makeIcon() {

            return new ItemStack(ItemRegistry.PINK_PICKAXE.get());
        }
    };
}
