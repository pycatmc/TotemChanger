package ru.pycat.totemchanger;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.loader.api.FabricLoader;
import ru.pycat.totemchanger.config.TotemChangerAnimationConfig;
import ru.pycat.totemchanger.config.TotemChangerParticlesConfig;

public class TotemChanger implements ClientModInitializer {

    @Override
    public void onInitializeClient() {
        TotemChangerAnimationConfig.loadConfig(FabricLoader.getInstance().getConfigDir());
        TotemChangerParticlesConfig.loadConfigs(FabricLoader.getInstance().getConfigDir());
    }
}
