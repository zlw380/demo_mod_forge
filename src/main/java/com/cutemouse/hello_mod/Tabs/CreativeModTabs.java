package com.cutemouse.hello_mod.Tabs;

import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;

public class CreativeModTabs {

    public static final CreativeModeTab HELLO_MOD_TOOLS = new CreativeModeTab("hello_mod_tools") {
        @Override
        public ItemStack makeIcon() {
            return null;
        }
    };
}
