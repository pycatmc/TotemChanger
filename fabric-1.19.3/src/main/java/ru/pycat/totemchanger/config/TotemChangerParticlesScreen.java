package ru.pycat.totemchanger.config;

import com.mojang.blaze3d.platform.GlStateManager;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.components.AbstractButton;
import net.minecraft.client.gui.components.AbstractSliderButton;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.components.Checkbox;
import net.minecraft.client.gui.narration.NarrationElementOutput;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.CommonComponents;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import org.lwjgl.glfw.GLFW;

import java.awt.*;
import java.text.DecimalFormat;
import java.util.function.DoubleConsumer;
import java.util.function.DoubleFunction;

public class TotemChangerParticlesScreen extends Screen {
    private static final DecimalFormat FORMAT1 = new DecimalFormat("#.#");
    private static final DecimalFormat FORMAT2 = new DecimalFormat("#.##");

    private final ResourceLocation CHECKBOX_TEXTURE = new ResourceLocation("textures/gui/checkbox.png");

    public final Screen parent;

    private static Button switchButton;
    private static Checkbox enabled;
    private static ColorProfileButton colorProfileButton;
    private static TCSlider scale;
    private static TCSlider velocityMultiplier;
    private static TCSlider lifetime;
    private static TCSlider gravity;
    private static Checkbox randomColor;
    private static TCSlider red;
    private static TCSlider green;
    private static TCSlider blue;
    private static Button done;


    public TotemChangerParticlesScreen(Screen parent) {
        super(Component.translatable("totemchanger.particles.title"));
        this.parent = parent;
    }

    private void updateGuiElements() {
        switchButton = Button.builder(Component.translatable("totemchanger.animation"), button -> minecraft.setScreen(new TotemChangerAnimationScreen(parent))).bounds(5, 5, 120, 20).build();
        enabled = new Checkbox(width / 2 - font.width(Component.translatable("totemchanger.particles.option.enabled")), 40, 24 + font.width(Component.translatable("totemchanger.particles.option.enabled")), 20, Component.translatable("totemchanger.particles.option.enabled"), TotemChangerParticlesConfig.enabled);
        scale = new TCSlider(width / 2 - 75, 64, 150, 20, Component.empty(), TotemChangerParticlesConfig.scale / 2D, value -> CommonComponents.optionNameValue(Component.translatable("totemchanger.particles.option.scale"), Component.literal(FORMAT1.format(value * 2D))), value -> TotemChangerParticlesConfig.scale = (float) (value * 2D));
        velocityMultiplier = new TCSlider(width / 2 -  75, 88, 150, 20, Component.empty(), TotemChangerParticlesConfig.velocityMultiplier / 2D, value -> CommonComponents.optionNameValue(Component.translatable("totemchanger.particles.option.velocityMultiplier"), Component.literal(FORMAT1.format(value * 2D))), value -> TotemChangerParticlesConfig.velocityMultiplier = (float) (value * 2D));
        gravity = new TCSlider(width / 2 -  75, 112, 150, 20, Component.empty(), TotemChangerParticlesConfig.gravity / 2D, value -> CommonComponents.optionNameValue(Component.translatable("totemchanger.particles.option.gravity"), Component.literal(FORMAT2.format(value * 2D))), value -> TotemChangerParticlesConfig.gravity = (float) (value * 2D));
        lifetime = new TCSlider(width / 2 -  75, 136, 150, 20, Component.empty(), TotemChangerParticlesConfig.lifetime / 200D, value -> CommonComponents.optionNameValue(Component.translatable("totemchanger.particles.option.lifetime"), Component.literal(Integer.toString((int) (value * 200D)))), value -> TotemChangerParticlesConfig.lifetime = (int) (value * 200D));
        randomColor = new Checkbox(width  / 2 - font.width(Component.translatable("totemchanger.particles.option.randomColor")) + 15, 160, 24 + font.width(Component.translatable("totemchanger.particles.option.randomColor")), 20, Component.translatable("totemchanger.particles.option.randomColor"), TotemChangerParticlesConfig.randomColor);
        colorProfileButton = new ColorProfileButton(width / 2 - 75, 184, 150, 20);
        red = new TCSlider(width / 2 + 80, 208, 150, 20, Component.empty(), TotemChangerParticlesConfig.red / 255D, value -> CommonComponents.optionNameValue(Component.translatable("totemchanger.particles.option.red"), Component.literal(Integer.toString((int) (value * 255D)))), value -> TotemChangerParticlesConfig.red = (int) (value * 255D));
        green = new TCSlider(width / 2 - 75, 208, 150, 20, Component.empty(), TotemChangerParticlesConfig.green / 255D, value -> CommonComponents.optionNameValue(Component.translatable("totemchanger.particles.option.green"), Component.literal(Integer.toString((int) (value * 255D)))), value -> TotemChangerParticlesConfig.green = (int) (value * 255D));
        blue = new TCSlider(width / 2 - 230, 208, 150, 20, Component.empty(), TotemChangerParticlesConfig.blue / 255D, value -> CommonComponents.optionNameValue(Component.translatable("totemchanger.particles.option.blue"), Component.literal(Integer.toString((int) (value * 255D)))), value -> TotemChangerParticlesConfig.blue = (int) (value * 255D));
        done = Button.builder(CommonComponents.GUI_DONE, button -> minecraft.setScreen(parent)).bounds(width / 2 - 75, height - 24, 150, 20).build();
    }

    @Override
    protected void init() {
        updateGuiElements();
        addRenderableWidget(switchButton);
        addRenderableWidget(enabled);
        addRenderableWidget(scale);
        addRenderableWidget(velocityMultiplier);
        addRenderableWidget(gravity);
        addRenderableWidget(lifetime);
        addRenderableWidget(randomColor);
        addRenderableWidget(colorProfileButton);
        addRenderableWidget(red);
        addRenderableWidget(green);
        addRenderableWidget(blue);
        addRenderableWidget(done);
    }

    public static void updateColorSliders() {
        red.setValue(TotemChangerParticlesConfig.red / 255D);
        green.setValue(TotemChangerParticlesConfig.green / 255D);
        blue.setValue(TotemChangerParticlesConfig.blue / 255D);
    }

    @Override
    public void render(PoseStack stack, int mouseX, int mouseY, float delta) {
        renderBackground(stack);
        super.render(stack, mouseX, mouseY, delta);
        drawCenteredString(stack, font, title, width / 2, 10, -1);

        if (!randomColor.selected()) {
            red.visible = true;
            green.visible = true;
            blue.visible = true;
            RenderSystem.setShaderTexture(0, CHECKBOX_TEXTURE);
            RenderSystem.enableDepthTest();
            RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
            RenderSystem.enableBlend();
            RenderSystem.defaultBlendFunc();
            RenderSystem.blendFunc(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA);
            blit(stack, (width / 2 - 252), 208, 0.0F, 0.0F, 20, 20, 64, 64);
            fill(stack, (width / 2 - 250), 210, (width / 2 - 234), 226, new Color(TotemChangerParticlesConfig.red, TotemChangerParticlesConfig.green, TotemChangerParticlesConfig.blue).getRGB());
        } else {
            red.visible = false;
            green.visible = false;
            blue.visible = false;
        }
    }

    @Override
    public void tick() {
        TotemChangerParticlesConfig.enabled = enabled.selected();
        TotemChangerParticlesConfig.randomColor = randomColor.selected();
    }

    @Override
    public void removed() {
        TotemChangerParticlesConfig.enabled = enabled.selected();
        TotemChangerParticlesConfig.randomColor = randomColor.selected();
        TotemChangerParticlesConfig.saveConfig(FabricLoader.getInstance().getConfigDir());
    }

    public static class ColorProfileButton extends AbstractButton {

        public ColorProfileButton(int x, int y, int width, int height) {
            super(x, y, width, height, Component.empty());
            updateMessage();
        }

        @Override
        public void onPress() {

        }

        @Override
        public boolean mouseClicked(double mouseX, double mouseY, int keyCode) {
            if (this.clicked(mouseX, mouseY)) {
                switch (keyCode) {
                    case GLFW.GLFW_MOUSE_BUTTON_1 -> {
                        TotemChangerParticlesConfig.setColorProfile(FabricLoader.getInstance().getConfigDir(), TotemChangerParticlesConfig.colorProfile + 1);
                        updateMessage();
                        TotemChangerParticlesScreen.updateColorSliders();
                        this.playDownSound(Minecraft.getInstance().getSoundManager());
                    }
                    case GLFW.GLFW_MOUSE_BUTTON_2 -> {
                        if (TotemChangerParticlesConfig.colorProfile > 1) {
                            TotemChangerParticlesConfig.setColorProfile(FabricLoader.getInstance().getConfigDir(), TotemChangerParticlesConfig.colorProfile - 1);
                            updateMessage();
                            TotemChangerParticlesScreen.updateColorSliders();
                            this.playDownSound(Minecraft.getInstance().getSoundManager());
                        }
                    }
                }
            }

            return false;
        }

        protected void updateMessage() {
            setMessage(Component.literal(Component.translatable("totemchanger.particles.option.colorProfile").getString()+": "+TotemChangerParticlesConfig.colorProfile));
        }

        @Override
        protected void updateWidgetNarration(NarrationElementOutput narrationElementOutput) {
            this.defaultButtonNarrationText(narrationElementOutput);
        }
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

        protected void setValue(double value) {
            this.value = value;
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
