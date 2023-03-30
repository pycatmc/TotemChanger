package ru.pycat.totemchanger.config;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;

public class TotemChangerAnimationConfig {
    private static final Logger LOG = LogManager.getLogger("TotemChanger");
    private static final Gson GSON = new Gson();

    public static boolean enabled = true;
    public static float scale = 1F;
    public static float posX = 0F;
    public static float posY = 0F;

    public static void loadConfig(Path directory) {
        try {
            Path pathConfigFolder = directory.resolve("totemchanger");
            Path pathConfig = pathConfigFolder.resolve("totemchanger-animation.json");
            if (!Files.isRegularFile(pathConfig)) return;
            JsonObject json = GSON.fromJson(new String(Files.readAllBytes(pathConfig), StandardCharsets.UTF_8), JsonObject.class);
            enabled = json.get("enabled").getAsBoolean();
            scale = json.get("scale").getAsFloat();
            posX = json.get("posX").getAsFloat();
            posY = json.get("posY").getAsFloat();
            LOG.info("TotemChanger config loaded.");
        } catch (Exception e) {
            LOG.warn("Unable to load TotemChanger config.", e);
        }
    }

    public static void saveConfig(Path directory) {
        try {
            Path pathConfigFolder = directory.resolve("totemchanger");
            Path pathConfig = pathConfigFolder.resolve("totemchanger-animation.json");
            Files.createDirectories(pathConfigFolder);
            JsonObject json = new JsonObject();
            json.addProperty("enabled", enabled);
            json.addProperty("scale", scale);
            json.addProperty("posX", posX);
            json.addProperty("posY", posY);
            Files.write(pathConfig, GSON.toJson(json).getBytes(StandardCharsets.UTF_8));
            LOG.info("TotemChanger config saved.");
        } catch (Exception e) {
            LOG.warn("Unable to save TotemChanger config.", e);
        }
    }
}
