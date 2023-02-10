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
        return this.config.enabled;
    }

    public float getScale() {
        return this.config.scale;
    }

    public float getPosX() {
        return this.config.posX;
    }

    public float getPosY() {
        return this.config.posY;
    }

}
