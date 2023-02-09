package ru.pycat.totemchanger;

import com.google.gson.JsonObject;
import me.shedaniel.autoconfig.AutoConfig;
import me.shedaniel.autoconfig.serializer.GsonConfigSerializer;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.loader.api.FabricLoader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;

import static com.terraformersmc.modmenu.ModMenu.GSON;

public class TotemChanger implements ModInitializer {

    public static final Logger LOGGER = LoggerFactory.getLogger("totemchanger");

    public static TotemChangerConfig config;

    @Override
    public void onInitialize() {
        AutoConfig.register(TotemChangerConfig.class, GsonConfigSerializer::new);
        config = AutoConfig.getConfigHolder(TotemChangerConfig.class).getConfig();
        LOGGER.info("Loaded!");
    }

}
