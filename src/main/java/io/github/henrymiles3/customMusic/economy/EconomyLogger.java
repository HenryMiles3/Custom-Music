package io.github.henrymiles3.customMusic.economy;

import java.io.File;
import java.io.IOException;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

import io.github.henrymiles3.customMusic.CustomMusic;

public class EconomyLogger {
    
    private final CustomMusic customMusic;

    private File logFile;
    private FileConfiguration config;
    private int count;

    public EconomyLogger(CustomMusic customMusic){
        this.customMusic = customMusic;

        this.logFile = new File(customMusic.getDataFolder(), "logs.yml");

        if(!(logFile.exists())){
            try{
                customMusic.getDataFolder().mkdirs();
                logFile.createNewFile();
                this.config = YamlConfiguration.loadConfiguration(logFile);

                config.set("metadata.count", 0);
            } catch(IOException e){
                e.printStackTrace();
            }

            this.config = YamlConfiguration.loadConfiguration(logFile);

            this.count = config.getInt("metadata.count", 0);
        }
    }

    public void logSet(Player player, Float value){
        config.set(("logs."+count), ("[SET] - ["+player.getName()+"] - "+value));
        save();
    }

    public void logReset(Player player, Float value){
        config.set(("logs."+count), ("[RESET] - ["+player.getName()+"] - "+value));
        save();
    }

    public void logAdd(Player player, Float value){
        config.set(("logs."+count), ("[ADD] - ["+player.getName()+"] - "+value));
        save();
    }
    
    public void logRemove(Player player, Float value){
        config.set(("logs."+count), ("[REMOVE] - ["+player.getName()+"] - "+value));
        save();
    }

    private void save(){
        count++;
        config.set("metadata.count", count);
        try {
            config.save(logFile);
        }catch(IOException e){
            e.printStackTrace();
        }
    }
}
