package io.github.henrymiles3.customMusic.economy;

import java.io.File;
import java.io.IOException;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

import io.github.henrymiles3.customMusic.CustomMusic;

public class EconomyManager {
    
    private final CustomMusic customMusic;
    private final EconomyLogger economyLogger;

    private File dataFile;
    private YamlConfiguration config;

    //Cache
    private final Map<UUID, Float> balanceCache = new ConcurrentHashMap<>();

    public EconomyManager(CustomMusic customMusic, EconomyLogger economyLogger){
        this.customMusic = customMusic;
        this.economyLogger = economyLogger;

        //Default playerdata file setup
        this.dataFile = new File(customMusic.getDataFolder(), "data.yml");
        if(!(dataFile.exists())){
            try{
                customMusic.getDataFolder().mkdirs();
                dataFile.createNewFile();
            }catch(IOException e){
                e.printStackTrace();
            }

            this.config = YamlConfiguration.loadConfiguration(dataFile);
        }
    }

    //Get
    public Float getBalance(Player player){
        if(player == null){
            return 0.0f;
        }
        
        return balanceCache.get(player.getUniqueId());
    }

    public boolean canAfford(Player player, Float price){
        if(player == null){
            return false;
        }
        Float currentBalance = balanceCache.get(player.getUniqueId());
        if(currentBalance >= price){
            return true;
        }else{
            return false;
        }
    }

    //Set
    public void setBalance(Player player, Float newBalance){
        if(player == null){
            return;
        }

        balanceCache.put(player.getUniqueId(), newBalance);

        economyLogger.logSet(player, newBalance);
    }

    public void resetBalance(Player player){
        if(player == null){
            return;
        }

        balanceCache.put(player.getUniqueId(), 0.0f);

        economyLogger.logReset(player, 0.0f);
    }

    public void addBalance(Player player, Float value){
        if(player == null){
            return;
        }
        Float current = balanceCache.get(player.getUniqueId());
        Float newBalance = current + value;

        balanceCache.put(player.getUniqueId(), newBalance);
        
        economyLogger.logAdd(player, value);
    }

    public void removeBalance(Player player, Float value){
        if(player == null){
            return;
        }

        Float current = balanceCache.get(player.getUniqueId());
        Float newBalance = current - value;

        balanceCache.put(player.getUniqueId(), newBalance);

        economyLogger.logRemove(player, value);
    }

    //Saving
    public void saveCache(){
        for(UUID key : balanceCache.keySet()){
            config.set("data.balance."+key, balanceCache.get(key));
        }

        try{
            config.save(dataFile);
        }catch(IOException e){
            e.printStackTrace();
        }
    }
    

}
