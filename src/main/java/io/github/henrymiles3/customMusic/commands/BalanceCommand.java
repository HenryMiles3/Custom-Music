package io.github.henrymiles3.customMusic.commands;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import io.github.henrymiles3.customMusic.economy.EconomyManager;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;

public class BalanceCommand implements CommandExecutor {

    private final EconomyManager economyManager;

    public BalanceCommand(EconomyManager economyManager){
        this.economyManager = economyManager;
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String @NotNull [] args) {
        if(!sender.hasPermission("command.balance")){
            sender.sendMessage(Component.text("You do not have permission to use this command.", NamedTextColor.RED));
            return true;
        }

        if(!(args.length == 1) || !(args.length == 0)){
            return false;
        }

        Player target = (Player) sender;

        if(args.length == 1){
            target = Bukkit.getPlayer(args[0]);
        }

        sender.sendMessage(Component.text(("The player "+target.getName()+" has £"+economyManager.getBalance(target).toString()), NamedTextColor.GREEN));
        return true;
        

    }
    
}
