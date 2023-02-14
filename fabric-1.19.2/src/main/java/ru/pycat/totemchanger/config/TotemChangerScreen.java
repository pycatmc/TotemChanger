package ru.pycat.totemchanger.config;

import com.mojang.blaze3d.vertex.PoseStack;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.client.gui.components.AbstractSliderButton;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.components.Checkbox;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.CommonComponents;
import net.minecraft.network.chat.Component;
import ru.pycat.totemchanger.config.TotemChangerConfig;

import java.text.DecimalFormat;
import java.util.function.DoubleConsumer;
import java.util.function.DoubleFunction;

public class TotemChangerScreen extends Screen {
    private static final DecimalFormat FORMAT = new DecimalFormat("#.#");

    private final Screen parent;
    private Checkbox enabled;

    public TotemChangerScreen(Screen parent) {
        super(Component.translatable("totemchanger.title"));
        this.parent = parent;
    }

    @Override
    protected void init() {
        addRenderableWidget(enabled = new Checkbox(width / 2 - font.width(Component.translatable("totemchanger.option.enabled")), 40, 24 + font.width(Component.translatable("totemchanger.option.enabled")), 20, Component.translatable("totemchanger.option.enabled"), TotemChangerConfig.enabled));
        addRenderableWidget(new TCSlider(width / 2 - 75, 64, 150, 20, Component.empty(), TotemChangerConfig.scale, value -> Component.translatable("options.percent_value", Component.translatable("totemchanger.option.scale"), Component.literal(Integer.toString((int) (value * 100D)))), value -> TotemChangerConfig.scale = (float) value));
        addRenderableWidget(new TCSlider(width / 2 -  75, 88, 150, 20, Component.empty(), TotemChangerConfig.posX / 4D + 0.5D, value -> CommonComponents.optionNameValue(Component.translatable("totemchanger.option.posX"), Component.literal(FORMAT.format(value * 4D - 2D))), value -> TotemChangerConfig.posX = (float) (value * 4D - 2D)));
        addRenderableWidget(new TCSlider(width / 2 -  75, 112, 150, 20, Component.empty(), TotemChangerConfig.posY / 2D + 0.5D, value -> CommonComponents.optionNameValue(Component.translatable("totemchanger.option.posY"), Component.literal(FORMAT.format(value * 2D - 1D))), value -> TotemChangerConfig.posY = (float) (value * 2D - 1D)));
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
        TotemChangerConfig.enabled = enabled.selected();
    }

    @Override
    public void removed() {
        TotemChangerConfig.enabled = enabled.selected();
        TotemChangerConfig.saveConfig(FabricLoader.getInstance().getConfigDir());
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
