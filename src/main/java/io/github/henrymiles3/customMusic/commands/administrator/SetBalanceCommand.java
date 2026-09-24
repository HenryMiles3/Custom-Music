package io.github.henrymiles3.customMusic.commands.administrator;

import org.bukkit.command.Command;
import org.bukkit.entity.Player;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;

import economy.EconomyManager;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;

public class SetBalanceCommand implements CommandExecutor {

    private final EconomyManager economyManager;

    public SetBalanceCommand(EconomyManager economyManager){
        this.economyManager = economyManager;
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String @NotNull [] args) {
        if(!sender.hasPermission("admin")){
            sender.sendMessage(Component.text("You do not have permission to use this command.", NamedTextColor.RED));
            return true;
        }

        if(!(args.length == 2)){
            return false;
        }

        Player target = Bukkit.getPlayer(args[0]);
        Float newAmmount = Float.parseFloat(args[1]);

        economyManager.setBalance(target, newAmmount);
        return true;
    }
    
}
