package io.github.henrymiles3.customMusic.commands;

import java.io.IOException;
import java.text.ParseException;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import io.github.henrymiles3.customMusic.economy.EconomyManager;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;

public class PayCommand implements CommandExecutor {

    private final EconomyManager economyManager;

    public PayCommand(EconomyManager economyManager){
        this.economyManager = economyManager;
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String @NotNull [] args) {
        if(!sender.hasPermission("command.pay")){
            sender.sendMessage(Component.text("You do not have the required permissions to use this command!", NamedTextColor.RED));
            return true;
        }

        if(!(args.length == 2)){
            return false;
        }

        Player target = Bukkit.getPlayer(args[0].toString());
        Float amount = (Float.parseFloat(args[1]));

        if(target == null || amount == null){
            return false;
        }

        if(economyManager.canAfford((Player) sender, amount)){
            economyManager.removeBalance((Player) sender, amount);
            economyManager.addBalance(target, amount);

            sender.sendMessage(Component.text(("Gave "+amount+" to "+target.getName()), NamedTextColor.GREEN));

            return true;
        }


        return false;
    }
    
}
