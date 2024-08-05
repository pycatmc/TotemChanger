package ru.pycat.totemchanger.mixin;

import net.minecraft.client.gui.components.Checkbox;
import net.minecraft.resources.ResourceLocation;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(Checkbox.class)
public interface CheckboxAccessor {

    @Accessor("selected")
    public void setSelected(boolean selected);

}
