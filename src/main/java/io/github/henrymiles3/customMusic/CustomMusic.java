package io.github.henrymiles3.customMusic;

import org.bukkit.plugin.java.JavaPlugin;

import io.github.henrymiles3.customMusic.economy.EconomyLogger;
import io.github.henrymiles3.customMusic.economy.EconomyManager;

public final class CustomMusic extends JavaPlugin {
    
    private final EconomyLogger economyLogger = new EconomyLogger(this);
    private final EconomyManager economnyManager = new EconomyManager(this, economyLogger);
    

    @Override
    public void onEnable() {
        // Plugin startup logic
        getLogger().info("Starting plugin...");
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic

        //Saves the data file on shutdown.
        getLogger().info("Saving economy data file...");
        economnyManager.saveCache();
    }
}
