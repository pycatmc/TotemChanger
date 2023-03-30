package ru.pycat.totemchanger.mixin;

import com.mojang.blaze3d.vertex.PoseStack;
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
                    target = "Lnet/minecraft/client/renderer/entity/ItemRenderer;renderStatic(Lnet/minecraft/world/item/ItemStack;Lnet/minecraft/client/renderer/block/model/ItemTransforms$TransformType;IILcom/mojang/blaze3d/vertex/PoseStack;Lnet/minecraft/client/renderer/MultiBufferSource;I)V",
                    opcode = Opcodes.INVOKEVIRTUAL,
                    ordinal = 0),
            locals = LocalCapture.CAPTURE_FAILSOFT)
    private void onFloatingItemRender(int i, int j, float f, CallbackInfo ci, int k, float g, float h, float l, float m, float n, float o, float p, PoseStack poseStack, float q, MultiBufferSource.BufferSource bufferSource) {
        if (TotemChangerAnimationConfig.enabled && this.itemActivationItem.is(Items.TOTEM_OF_UNDYING)) {
            poseStack.translate(TotemChangerAnimationConfig.posX, TotemChangerAnimationConfig.posY, TotemChangerAnimationConfig.posX);
            poseStack.scale(TotemChangerAnimationConfig.scale, TotemChangerAnimationConfig.scale, TotemChangerAnimationConfig.scale);
        }
    }
}
