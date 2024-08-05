package ru.pycat.totemchanger.mixin;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import org.objectweb.asm.Opcodes;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;
import ru.pycat.totemchanger.config.TotemChangerAnimationConfig;

@Mixin(GameRenderer.class)
public class GameRendererMixin {
    @Shadow private ItemStack itemActivationItem;

    @Inject(method = "renderItemActivationAnimation",
            at = @At(value = "INVOKE",
                    target = "Lnet/minecraft/client/gui/GuiGraphics;drawManaged(Ljava/lang/Runnable;)V",
                    opcode = Opcodes.INVOKEVIRTUAL,
                    ordinal = 0),
            locals = LocalCapture.CAPTURE_FAILSOFT)
    private void onFloatingItemRender(GuiGraphics guiGraphics, float f, CallbackInfo ci, int i, float g, float h, float j, float k, float l, float m, float n, PoseStack poseStack, float o) {
        if (TotemChangerAnimationConfig.enabled && this.itemActivationItem.is(Items.TOTEM_OF_UNDYING)) {
            poseStack.translate(TotemChangerAnimationConfig.posX, TotemChangerAnimationConfig.posY, TotemChangerAnimationConfig.posX);
            poseStack.scale(TotemChangerAnimationConfig.scale, TotemChangerAnimationConfig.scale, TotemChangerAnimationConfig.scale);
        }
    }
}
