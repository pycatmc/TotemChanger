package ru.pycat.totemchanger.mixin;

import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.SimpleAnimatedParticle;
import net.minecraft.client.particle.SpriteSet;
import net.minecraft.client.particle.TotemParticle;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import ru.pycat.totemchanger.config.TotemChangerParticlesConfig;

import java.awt.*;

@Mixin(TotemParticle.class)
public class TotemParticleMixin extends SimpleAnimatedParticle {

    private TotemParticleMixin() {
        super(null, 0d, 0d, 0d, null, 0f);
    }

    @Inject(method = "<init>",
            at = @At(value = "RETURN"))
    private void onInit(ClientLevel clientLevel, double d, double e, double f, double g, double h, double i, SpriteSet spriteSet, CallbackInfo ci) {
        if (TotemChangerParticlesConfig.enabled) {
            this.scale(TotemChangerParticlesConfig.scale);
            this.friction = TotemChangerParticlesConfig.velocityMultiplier;
            this.setLifetime(TotemChangerParticlesConfig.lifetime);
            this.gravity = TotemChangerParticlesConfig.gravity;

            int color;

            if (TotemChangerParticlesConfig.randomColor) {
                color = Color.HSBtoRGB(this.random.nextFloat(), 1.0f, 1.0f);
            } else {
                color = TotemChangerParticlesConfig.getColor();
            }

            setColor(color);
        }
    }

}
