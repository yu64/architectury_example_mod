package com.example.neoforge;

import com.example.ExampleMod;
import com.example.screen.ExampleScreen;

import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.event.lifecycle.FMLClientSetupEvent;
import net.neoforged.fml.loading.FMLEnvironment;
import net.neoforged.neoforge.client.event.RegisterMenuScreensEvent;

@Mod(ExampleMod.MOD_ID)
public final class ExampleModNeoForge {

    public ExampleModNeoForge(IEventBus modEventBus) 
    {
        
        if (FMLEnvironment.dist == Dist.CLIENT) {
            modEventBus.addListener(this::setupClient);
            modEventBus.addListener(this::registerScreens);
        }

        ExampleMod.init();
    }

    private void setupClient(final FMLClientSetupEvent event) 
    {
        // ExampleMod.initClient();
    }

    @SuppressWarnings("null")
    private void registerScreens(RegisterMenuScreensEvent event) 
    {
        event.register(ExampleMod.EXAMPLE_SCREEN_HANDLER.get(), ExampleScreen::new);
    }


}
