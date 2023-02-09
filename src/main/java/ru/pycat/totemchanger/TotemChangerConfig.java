package ru.pycat.totemchanger;

import me.shedaniel.autoconfig.ConfigData;
import me.shedaniel.autoconfig.annotation.Config;
import me.shedaniel.autoconfig.annotation.ConfigEntry;

@Config(name = "totemchanger")
public class TotemChangerConfig implements ConfigData {

    public boolean enabled = true;
    public float scale = 1.0f;
    public float posX = 0.0f;
    public float posY = 0.0f;

}
