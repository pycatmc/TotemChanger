package ru.pycat.totemchanger.mixin;

import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.*;
import net.minecraft.client.render.model.json.ModelTransformation;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3f;
import org.jetbrains.annotations.Nullable;
import org.objectweb.asm.Opcodes;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;
import ru.pycat.totemchanger.TotemChanger;

@Mixin(GameRenderer.class)
public class GameRendererMixin {

    @Shadow @Nullable private ItemStack floatingItem;

    @Inject(method = "renderFloatingItem",
            at = @At(value = "INVOKE",
                    target = "Lnet/minecraft/client/render/item/ItemRenderer;renderItem(Lnet/minecraft/item/ItemStack;Lnet/minecraft/client/render/model/json/ModelTransformation$Mode;IILnet/minecraft/client/util/math/MatrixStack;Lnet/minecraft/client/render/VertexConsumerProvider;I)V",
                    opcode = Opcodes.INVOKEVIRTUAL,
                    ordinal = 0),
            locals = LocalCapture.CAPTURE_FAILSOFT)
    private void onItemRender(int scaledWidth, int scaledHeight, float tickDelta, CallbackInfo ci, int i, float f, float g, float h, float j, float k, float l, float m, MatrixStack matrixStack, float n, VertexConsumerProvider.Immediate immediate) {
        if (TotemChanger.config.enabled && this.floatingItem.isOf(Items.TOTEM_OF_UNDYING)) {
            matrixStack.scale(TotemChanger.config.scale, TotemChanger.config.scale, TotemChanger.config.scale);
            matrixStack.translate(TotemChanger.config.posX, TotemChanger.config.posY, 0);
        }
    }

}
