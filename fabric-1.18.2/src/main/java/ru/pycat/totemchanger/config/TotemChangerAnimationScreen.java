package ru.pycat.totemchanger.config;

import com.mojang.blaze3d.vertex.PoseStack;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.client.gui.components.AbstractSliderButton;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.components.Checkbox;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.CommonComponents;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.network.chat.TranslatableComponent;

import java.text.DecimalFormat;
import java.util.function.DoubleConsumer;
import java.util.function.DoubleFunction;

public class TotemChangerAnimationScreen extends Screen {
    private static final DecimalFormat FORMAT = new DecimalFormat("#.#");

    private final Screen parent;
    private Checkbox enabled;

    public TotemChangerAnimationScreen(Screen parent) {
        super(new TranslatableComponent("totemchanger.animation.title"));
        this.parent = parent;
    }

    @Override
    protected void init() {
        addRenderableWidget(new Button(5, 5, 120, 20, new TranslatableComponent("totemchanger.particles"), button -> minecraft.setScreen(new TotemChangerParticlesScreen(parent))));
        addRenderableWidget(enabled = new Checkbox(width / 2 - font.width(new TranslatableComponent("totemchanger.animation.option.enabled")), 40, 24 + font.width(new TranslatableComponent("totemchanger.animation.option.enabled")), 20, new TranslatableComponent("totemchanger.animation.option.enabled"), TotemChangerAnimationConfig.enabled));
        addRenderableWidget(new TCSlider(width / 2 - 75, 64, 150, 20, TextComponent.EMPTY, TotemChangerAnimationConfig.scale, value -> new TranslatableComponent("options.percent_value", new TranslatableComponent("totemchanger.animation.option.scale"), new TextComponent(Integer.toString((int) (value * 100D)))), value -> TotemChangerAnimationConfig.scale = (float) value));
        addRenderableWidget(new TCSlider(width / 2 -  75, 88, 150, 20, TextComponent.EMPTY, TotemChangerAnimationConfig.posX / 4D + 0.5D, value -> CommonComponents.optionNameValue(new TranslatableComponent("totemchanger.animation.option.posX"), new TextComponent(FORMAT.format(value * 4D - 2D))), value -> TotemChangerAnimationConfig.posX = (float) (value * 4D - 2D)));
        addRenderableWidget(new TCSlider(width / 2 -  75, 112, 150, 20, TextComponent.EMPTY, TotemChangerAnimationConfig.posY / 2D + 0.5D, value -> CommonComponents.optionNameValue(new TranslatableComponent("totemchanger.animation.option.posY"), new TextComponent(FORMAT.format(value * 2D - 1D))), value -> TotemChangerAnimationConfig.posY = (float) (value * 2D - 1D)));
        addRenderableWidget(new Button(width / 2 - 75, height - 24, 150, 20, CommonComponents.GUI_DONE, button -> minecraft.setScreen(parent)));
    }

    @Override
    public void render(PoseStack stack, int mouseX, int mouseY, float delta) {
        renderBackground(stack);
        super.render(stack, mouseX, mouseY, delta);
        drawCenteredString(stack, font, title, width / 2, 10, -1);
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
