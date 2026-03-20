package com.palm3.designdecor.register;

import com.palm3.designdecor.DDMain;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;

public class DDTabs {
    public static final DeferredRegister<CreativeModeTab> DD_TABS = DeferredRegister.create(Registries.CREATIVE_MODE_TAB, DDMain.MOD_ID);

    public static final RegistryObject<CreativeModeTab> MAIN_TAB = DD_TABS
            .register("main_tab", () -> CreativeModeTab.builder()
                    .title(Component.translatable("itemGroup.dndecor.base"))
                    .icon(() -> new ItemStack(DDBlocks.BRASS_FRONTLIGHT))
                    .displayItems((params, output) -> {
                        DDMain.DD_REGISTRATE.getAll(Registries.BLOCK)
                                .forEach(entry -> output.accept(entry.get().asItem()));
                    })
                    .build()
            );
}
