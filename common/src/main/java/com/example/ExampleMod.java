package com.example;

import java.util.Set;
import java.util.function.Supplier;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.example.block.ExampleBlock;
import com.example.block.entity.ExampleBlockEntity;
import com.example.screen.ExampleScreen;
import com.example.screen.ExampleScreenHandler;
import com.google.common.base.Suppliers;

import dev.architectury.registry.menu.MenuRegistry;
import dev.architectury.registry.registries.Registrar;
import dev.architectury.registry.registries.RegistrarManager;
import dev.architectury.registry.registries.RegistrySupplier;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.flag.FeatureFlags;
import net.minecraft.world.inventory.MenuType;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.CreativeModeTabs;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntityType;

public final class ExampleMod {
    
    public static final String MOD_ID = "architectury_example_mod";
    public static final String MOD_NAME = "Architectury Example Mod";
    public static final Logger LOGGER = LogManager.getLogger(MOD_NAME);


    //Registerの管理クラスを遅延作成
    public static final Supplier<RegistrarManager> REGISTRIES = Suppliers.memoize(() -> RegistrarManager.get(MOD_ID));

    //各種Registerを取得
    public static final Registrar<Block> BLOCK_REGISTRAR = REGISTRIES.get().get(Registries.BLOCK);
    public static final Registrar<Item> ITEM_REGISTRAR = REGISTRIES.get().get(Registries.ITEM);
    public static final Registrar<BlockEntityType<?>> BLOCK_ENTITY_TYPE_REGISTRAR = REGISTRIES.get().get(Registries.BLOCK_ENTITY_TYPE);
    public static final Registrar<MenuType<?>> SCREEN_HANDLER_TYPE_REGISTRAR = REGISTRIES.get().get(Registries.MENU);

    public static final ResourceLocation REGISTRY_NAME = ResourceLocation.fromNamespaceAndPath(ExampleMod.MOD_ID, "example");

    public static final RegistrySupplier<Block> EXAMPLE_BLOCK = 
        BLOCK_REGISTRAR.register(REGISTRY_NAME, () -> new ExampleBlock());
    
    public static final RegistrySupplier<BlockItem> EXAMPLE_BLOCK_ITEM = 
        ITEM_REGISTRAR.register(REGISTRY_NAME, () -> new BlockItem(EXAMPLE_BLOCK.get(), new Item.Properties().arch$tab(CreativeModeTabs.TOOLS_AND_UTILITIES) ));

    public static final RegistrySupplier<MenuType<ExampleScreenHandler>> EXAMPLE_SCREEN_HANDLER = 
        SCREEN_HANDLER_TYPE_REGISTRAR.register(REGISTRY_NAME, () -> new MenuType<>(ExampleScreenHandler::new, FeatureFlags.VANILLA_SET));
    
    public static final RegistrySupplier<BlockEntityType<ExampleBlockEntity>> EXAMPLE_ENTITY_BLOCK = 
        BLOCK_ENTITY_TYPE_REGISTRAR.register(REGISTRY_NAME, () -> new BlockEntityType<ExampleBlockEntity>(ExampleBlockEntity::new, Set.of(EXAMPLE_BLOCK.get()), null));

    
    


    public static void init()
    {
        LOGGER.info("[" + MOD_NAME + "] Common Loading ");
    }

    public static void initClient()
    {        
        MenuRegistry.registerScreenFactory(EXAMPLE_SCREEN_HANDLER.get(), ExampleScreen::new);
        LOGGER.info("[" + MOD_NAME + "] Client Loading ");
    }
}
