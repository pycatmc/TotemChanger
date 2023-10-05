package ru.pycat.totemchanger.config;

import com.mojang.blaze3d.vertex.PoseStack;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.AbstractSliderButton;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.components.Checkbox;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.CommonComponents;
import net.minecraft.network.chat.Component;

import java.text.DecimalFormat;
import java.util.function.DoubleConsumer;
import java.util.function.DoubleFunction;

public class TotemChangerAnimationScreen extends Screen {
    private static final DecimalFormat FORMAT = new DecimalFormat("#.#");

    private final Screen parent;
    private Checkbox enabled;

    public TotemChangerAnimationScreen(Screen parent) {
        super(Component.translatable("totemchanger.animation.title"));
        this.parent = parent;
    }

    @Override
    protected void init() {
        addRenderableWidget(Button.builder(Component.translatable("totemchanger.particles"), button -> minecraft.setScreen(new TotemChangerParticlesScreen(parent))).bounds(5, 5, 120, 20).build());
        addRenderableWidget(enabled = new Checkbox(width / 2 - font.width(Component.translatable("totemchanger.animation.option.enabled")), 40, 24 + font.width(Component.translatable("totemchanger.animation.option.enabled")), 20, Component.translatable("totemchanger.animation.option.enabled"), TotemChangerAnimationConfig.enabled));
        addRenderableWidget(new TCSlider(width / 2 - 75, 64, 150, 20, Component.empty(), TotemChangerAnimationConfig.scale, value -> Component.translatable("options.percent_value", Component.translatable("totemchanger.animation.option.scale"), Component.literal(Integer.toString((int) (value * 100D)))), value -> TotemChangerAnimationConfig.scale = (float) value));
        addRenderableWidget(new TCSlider(width / 2 -  75, 88, 150, 20, Component.empty(), TotemChangerAnimationConfig.posX / 4D + 0.5D, value -> CommonComponents.optionNameValue(Component.translatable("totemchanger.animation.option.posX"), Component.literal(FORMAT.format(value * 4D - 2D))), value -> TotemChangerAnimationConfig.posX = (float) (value * 4D - 2D)));
        addRenderableWidget(new TCSlider(width / 2 -  75, 112, 150, 20, Component.empty(), TotemChangerAnimationConfig.posY / 2D + 0.5D, value -> CommonComponents.optionNameValue(Component.translatable("totemchanger.animation.option.posY"), Component.literal(FORMAT.format(value * 2D - 1D))), value -> TotemChangerAnimationConfig.posY = (float) (value * 2D - 1D)));
        addRenderableWidget(Button.builder(CommonComponents.GUI_DONE, button -> minecraft.setScreen(parent)).bounds(width / 2 - 75, height - 24, 150, 20).build());
    }

    @Override
    public void render(GuiGraphics guiGraphics, int i, int j, float f) {
        renderBackground(guiGraphics);
        super.render(guiGraphics, i, j, f);
        guiGraphics.drawCenteredString(font, title, width / 2, 10, -1);
    }

    @Override
    public void tick() {
        TotemChangerAnimationConfig.enabled = enabled.selected();
    }

    @Override
    public void removed() {
        TotemChangerAnimationConfig.enabled = enabled.selected();
        TotemChangerAnimationConfig.saveConfig(FabricLoader.getInstance().getConfigDir());
    }

    public static class TCSlider extends AbstractSliderButton {
        private final DoubleFunction<Component> messageProvider;
        private final DoubleConsumer setter;

        public TCSlider(int x, int y, int width, int height, Component text, double value,
                        DoubleFunction<Component> messageProvider, DoubleConsumer setter) {
            super(x, y, width, height, text, value);
            this.messageProvider = messageProvider;
            this.setter = setter;
            updateMessage();
        }

        @Override
        protected void updateMessage() {
            setMessage(messageProvider.apply(value));
        }

        @Override
        protected void applyValue() {
            setter.accept(value);
        }
    }
}
