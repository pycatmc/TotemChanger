package ru.pycat.totemchanger;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.loader.api.FabricLoader;
import ru.pycat.totemchanger.config.TotemChangerConfig;

public class TotemChanger implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        TotemChangerConfig.loadConfig(FabricLoader.getInstance().getConfigDir());
    }
}
