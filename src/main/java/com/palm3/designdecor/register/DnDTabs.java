package com.palm3.designdecor.register;

import com.palm3.designdecor.DnDMain;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;

public class DnDTabs {
    public static final DeferredRegister<CreativeModeTab> DD_TABS = DeferredRegister.create(Registries.CREATIVE_MODE_TAB, DnDMain.MOD_ID);

    public static final RegistryObject<CreativeModeTab> MAIN_TAB = DD_TABS
            .register("main_tab", () -> CreativeModeTab.builder()
                    .title(Component.translatable("itemGroup.dndecor.base"))
                    .icon(() -> new ItemStack(DnDBlocks.BRASS_FRONTLIGHT))
                    .displayItems((params, output) -> {
                        DnDMain.DND_REGISTRATE.getAll(Registries.BLOCK)
                                .forEach(entry -> output.accept(entry.get().asItem()));
                    })
                    .build()
            );
}
