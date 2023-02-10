package ru.pycat.totemchanger.config;

import me.shedaniel.autoconfig.ConfigData;
import me.shedaniel.autoconfig.annotation.Config;
import me.shedaniel.autoconfig.annotation.ConfigEntry;

@Config(name = "totemchanger")
@Config.Gui.Background(Config.Gui.Background.TRANSPARENT)
public class TotemChangerConfig implements ConfigData {

    public boolean enabled = true;

    public float scale = 1.0f;

    @ConfigEntry.BoundedDiscrete(max = 10, min = -10)
    public int posX = 0;

    @ConfigEntry.BoundedDiscrete(max = 10, min = -10)
    public int posY = 0;

}
