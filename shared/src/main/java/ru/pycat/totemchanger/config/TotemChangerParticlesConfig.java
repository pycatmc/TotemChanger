package ru.pycat.totemchanger.config;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.awt.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;

public class TotemChangerParticlesConfig {
    private static final Logger LOG = LogManager.getLogger("TotemChanger");
    private static final Gson GSON = new Gson();

    public static boolean enabled = true;
    public static int profile = 1;
    public static float scale = 1F;
    public static float velocityMultiplier = 0.6F;
    public static float gravity = 1.25f;
    public static int lifetime = 60;

    public static boolean randomColor = false;
    public static int red = 0;
    public static int green = 0;
    public static int blue = 0;



    public static void setProfile(Path directory, int newProfile) {
        try {
            saveConfig(directory);

            Path pathConfigFolder = directory.resolve("totemchanger");
            Path pathConfig = pathConfigFolder.resolve("totemchanger-particles.json");
            Files.createDirectories(pathConfigFolder);
            JsonObject jsonConfig = new JsonObject();
            jsonConfig.addProperty("enabled", enabled);
            jsonConfig.addProperty("profile", profile = newProfile);
            Files.write(pathConfig, GSON.toJson(jsonConfig).getBytes(StandardCharsets.UTF_8));

            loadConfig(directory);
        } catch (Exception e) {
            LOG.warn("Unable to save TotemParticlesChanger config.", e);
        }
    }

    public static void loadConfig(Path directory) {
        try {
            Path pathConfigFolder = directory.resolve("totemchanger");
            Path pathConfig = pathConfigFolder.resolve("totemchanger-particles.json");
            if (!Files.isRegularFile(pathConfig)) return;
            JsonObject jsonConfig = GSON.fromJson(new String(Files.readAllBytes(pathConfig), StandardCharsets.UTF_8), JsonObject.class);
            enabled = jsonConfig.get("enabled").getAsBoolean();
            profile = jsonConfig.get("profile").getAsInt();

            Path pathProfile = pathConfigFolder.resolve("profile"+ profile +".json");
            if (!Files.isRegularFile(pathProfile)) return;
            JsonObject jsonProfile = GSON.fromJson(new String(Files.readAllBytes(pathProfile), StandardCharsets.UTF_8), JsonObject.class);
            scale = jsonProfile.get("scale").getAsFloat();
            velocityMultiplier = jsonProfile.get("velocityMultiplier").getAsFloat();
            gravity = jsonProfile.get("gravity").getAsFloat();
            lifetime = jsonProfile.get("lifetime").getAsInt();
            randomColor = jsonProfile.get("randomColor").getAsBoolean();
            red = jsonProfile.get("red").getAsInt();
            green = jsonProfile.get("green").getAsInt();
            blue = jsonProfile.get("blue").getAsInt();

            LOG.info("TotemParticlesChanger config loaded.");
        } catch (Exception e) {
            LOG.warn("Unable to load TotemParticlesChanger config.", e);
        }
    }

    public static void saveConfig(Path directory) {
        try {
            Path pathConfigFolder = directory.resolve("totemchanger");
            Path pathConfig = pathConfigFolder.resolve("totemchanger-particles.json");
            Files.createDirectories(pathConfigFolder);
            JsonObject jsonConfig = new JsonObject();
            jsonConfig.addProperty("enabled", enabled);
            jsonConfig.addProperty("profile", profile);
            Files.write(pathConfig, GSON.toJson(jsonConfig).getBytes(StandardCharsets.UTF_8));

            Path pathProfile = pathConfigFolder.resolve("profile"+ profile +".json");
            JsonObject jsonProfile = new JsonObject();
            jsonProfile.addProperty("scale", scale);
            jsonProfile.addProperty("velocityMultiplier", velocityMultiplier);
            jsonProfile.addProperty("gravity", gravity);
            jsonProfile.addProperty("lifetime", lifetime);
            jsonProfile.addProperty("randomColor", randomColor);
            jsonProfile.addProperty("red", red);
            jsonProfile.addProperty("green", green);
            jsonProfile.addProperty("blue", blue);
            Files.write(pathProfile, GSON.toJson(jsonProfile).getBytes(StandardCharsets.UTF_8));

            LOG.info("TotemParticlesChanger config saved.");
        } catch (Exception e) {
            LOG.warn("Unable to save TotemParticlesChanger config.", e);
        }
    }

    public static int getColor() {
        return new Color(red, green, blue).getRGB();
    }

}
