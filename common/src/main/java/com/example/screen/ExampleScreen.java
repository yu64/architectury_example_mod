package com.example.screen;

import com.example.ExampleMod;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;

@Environment(EnvType.CLIENT)
public class ExampleScreen extends AbstractContainerScreen<ExampleScreenHandler> 
{

    private static final ResourceLocation TEXTURE = ResourceLocation.fromNamespaceAndPath(ExampleMod.MOD_ID, "textures/gui/container/example_gui.png");

    public ExampleScreen(ExampleScreenHandler handler, Inventory inventory, Component title) 
    {
        super(handler, inventory, title);
    }

    @Override
    protected void init() 
    {
        super.init();
        titleLabelX = (imageWidth - font.width(this.title)) / 2;
    }

    @Override
    public void render(GuiGraphics context, int mouseX, int mouseY, float delta) 
    {
        super.render(context, mouseX, mouseY, delta);
        renderTooltip(context, mouseX, mouseY);
    }

    @Override
    protected void renderBg(GuiGraphics guiGraphics, float delta, int mouseX, int mouseY) 
    {
        int x = (width - imageWidth) / 2;
        int y = (height - imageHeight) / 2;
        guiGraphics.blit(TEXTURE, x, y, 0.0F, 0.0F, imageWidth, imageHeight,256, 256);
    }

}
