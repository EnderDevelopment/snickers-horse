package com.enderdevelopment.plugin;

import org.bukkit.plugin.java.JavaPlugin;

public final class Main extends JavaPlugin {

    @Override
    public void onEnable() {
        getCommand("maxhorse").setExecutor(new MaxHorseCommand(this));
        getLogger().info("MaxHorse enabled.");
    }

    @Override
    public void onDisable() {
        getLogger().info("MaxHorse disabled.");
    }
}
