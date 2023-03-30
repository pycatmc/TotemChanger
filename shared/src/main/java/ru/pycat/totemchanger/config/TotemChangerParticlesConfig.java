package ru.pycat.totemchanger.config;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Random;

public class TotemChangerParticlesConfig {
    private static final Logger LOG = LogManager.getLogger("TotemChanger");
    private static final Gson GSON = new Gson();
    private static final Random random = new Random();

    public static boolean enabled = true;
    public static int colorProfile = 1;
    public static float scale = 1F;
    public static float velocityMultiplier = 0.6F;
    public static float gravity = 1.25f;
    public static int lifetime = 60;

    public static boolean randomColor = false;
    public static int red = 0;
    public static int green = 0;
    public static int blue = 0;

    public static void setColorProfile(Path directory, int newColorProfile) {
        try {
            saveConfig(directory);

            Path pathConfigFolder = directory.resolve("totemchanger");
            Path pathConfig = pathConfigFolder.resolve("totemchanger-particles.json");
            Files.createDirectories(pathConfigFolder);
            JsonObject jsonConfig = new JsonObject();
            jsonConfig.addProperty("enabled", enabled);
            jsonConfig.addProperty("scale", scale);
            jsonConfig.addProperty("velocityMultiplier", velocityMultiplier);
            jsonConfig.addProperty("gravity", gravity);
            jsonConfig.addProperty("lifetime", lifetime);
            jsonConfig.addProperty("randomColor", randomColor);
            jsonConfig.addProperty("colorProfile", colorProfile = newColorProfile);
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
            scale = jsonConfig.get("scale").getAsFloat();
            velocityMultiplier = jsonConfig.get("velocityMultiplier").getAsFloat();
            gravity = jsonConfig.get("gravity").getAsFloat();
            lifetime = jsonConfig.get("lifetime").getAsInt();
            randomColor = jsonConfig.get("randomColor").getAsBoolean();
            colorProfile = jsonConfig.get("colorProfile").getAsInt();

            Path pathProfile = pathConfigFolder.resolve("colorprofile"+ colorProfile +".json");
            if (!Files.isRegularFile(pathProfile)) return;
            JsonObject jsonProfile = GSON.fromJson(new String(Files.readAllBytes(pathProfile), StandardCharsets.UTF_8), JsonObject.class);
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
            jsonConfig.addProperty("scale", scale);
            jsonConfig.addProperty("velocityMultiplier", velocityMultiplier);
            jsonConfig.addProperty("gravity", gravity);
            jsonConfig.addProperty("lifetime", lifetime);
            jsonConfig.addProperty("randomColor", randomColor);
            jsonConfig.addProperty("colorProfile", colorProfile);
            Files.write(pathConfig, GSON.toJson(jsonConfig).getBytes(StandardCharsets.UTF_8));

            Path pathProfile = pathConfigFolder.resolve("colorprofile"+colorProfile+".json");
            JsonObject jsonProfile = new JsonObject();
            jsonProfile.addProperty("red", red);
            jsonProfile.addProperty("green", green);
            jsonProfile.addProperty("blue", blue);
            Files.write(pathProfile, GSON.toJson(jsonProfile).getBytes(StandardCharsets.UTF_8));

            LOG.info("TotemParticlesChanger config saved.");
        } catch (Exception e) {
            LOG.warn("Unable to save TotemParticlesChanger config.", e);
        }
    }

    public static float getRed() {
        float tempRed = red;
        if (tempRed > 15)
            tempRed -= random.nextInt(15);
        return 0.003921568627451F * tempRed;
    }

    public static float getGreen() {
        float tempGreen = green;
        if (tempGreen > 15)
            tempGreen -= random.nextInt(15);
        return 0.003921568627451F * tempGreen;
    }

    public static float getBlue() {
        float tempBlue = blue;
        if (tempBlue > 15)
            tempBlue -= random.nextInt(15);
        return 0.003921568627451F * tempBlue;
    }

}
