package ru.pycat.totemchanger;

import me.shedaniel.autoconfig.AutoConfig;
import me.shedaniel.autoconfig.serializer.JanksonConfigSerializer;
import net.fabricmc.api.ModInitializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.pycat.totemchanger.config.TotemChangerConfig;

public class TotemChanger implements ModInitializer {

    public static final Logger LOGGER = LoggerFactory.getLogger("totemchanger");
    public static TotemChangerConfig config;

    @Override
    public void onInitialize() {
        AutoConfig.register(TotemChangerConfig.class, JanksonConfigSerializer::new);
        config = AutoConfig.getConfigHolder(TotemChangerConfig.class).getConfig();
        LOGGER.info("Loaded!");
    }

    public boolean isEnabled() {
        return config.enabled;
    }

    public float getScale() {
        return config.scale;
    }

    public float getPosX() {
        return config.posX * 0.1f;
    }

    public float getPosY() {
        return config.posY * 0.1f;
    }

}
