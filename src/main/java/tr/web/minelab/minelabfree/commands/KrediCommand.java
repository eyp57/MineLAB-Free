package tr.web.minelab.minelabfree.commands;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import tr.web.minelab.minelabfree.MineLABFree;

public class KrediCommand implements CommandExecutor {
    @Override
    public boolean onCommand(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String[] args) {

        if(commandSender.hasPermission("minelab.admin")) {
            if(args[0].equalsIgnoreCase("ver")) {
                Player player = Bukkit.getPlayer(args[1]);
                int amount = Integer.parseInt(args[2]);
                MineLABFree.getFetchData().addCredit(player, amount);
                commandSender.sendMessage(ChatColor.translateAlternateColorCodes('&', MineLABFree.getLanguageConfiguration().getString("creditGived")
                        .replaceAll("%player%", player.getName())
                        .replaceAll("%amount%", String.valueOf(amount))
                ));
            }
            else if(args[0].equalsIgnoreCase("sil")) {
                Player player = Bukkit.getPlayer(args[1]);
                int amount = Integer.parseInt(args[2]);
                MineLABFree.getFetchData().deleteCredit(player, amount);
                commandSender.sendMessage(ChatColor.translateAlternateColorCodes('&', MineLABFree.getLanguageConfiguration().getString("creditRemoved")
                        .replaceAll("%player%", player.getName())
                        .replaceAll("%amount%", String.valueOf(amount))
                ));
            } else if(args[0].equalsIgnoreCase("bak")) {
                Player player = Bukkit.getPlayer(args[1]);
                MineLABFree.getFetchData().updateCredit(player);
                commandSender.sendMessage(ChatColor.translateAlternateColorCodes('&', MineLABFree.getLanguageConfiguration().getString("creditRemoved")
                        .replaceAll("%player%", player.getName())
                        .replaceAll("%amount%", MineLABFree.getFetchData().getCredit(player.getUniqueId()))
                ));
            } else {
                commandSender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&cLütfen geçerli argüman giriniz: &7/kredi [ver/bak/sil] [oyuncu]"));
            }
        } else {
            commandSender.sendMessage(ChatColor.translateAlternateColorCodes('&', MineLABFree.getLanguageConfiguration().getString("noPerm")));
        }

        return false;
    }
}
