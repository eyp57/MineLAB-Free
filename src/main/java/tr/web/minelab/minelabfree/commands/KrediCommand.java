package tr.web.minelab.minelabfree.commands;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import tr.web.minelab.minelabfree.MineLABFree;

public class KrediCommand implements CommandExecutor {
    @Override
    public boolean onCommand(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String[] args) {

        if(args.length < 1) {
            commandSender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&cLütfen geçerli argüman giriniz: &7/kredi [ver/bak/sil] [oyuncu]"));
            return true;
        }
        if(commandSender.hasPermission("minelab.admin")) {
            if(args[0].equalsIgnoreCase("ver")) {

                if(args.length != 2) {
                    commandSender.sendMessage(ChatColor.translateAlternateColorCodes('&', MineLABFree.getLanguageConfiguration().getString("Arguments.noPlayer")));
                    return true;
                }
                Player player = Bukkit.getPlayer(args[1]);
                if(player == null) {
                    commandSender.sendMessage(ChatColor.translateAlternateColorCodes('&', MineLABFree.getLanguageConfiguration().getString("Arguments.noPlayer")));
                    return true;
                }

                String amount = args[2];
                if(args.length != 3 || Integer.parseInt(amount) < 1){
                    commandSender.sendMessage(ChatColor.translateAlternateColorCodes('&', MineLABFree.getLanguageConfiguration().getString("Arguments.noAmount")));
                    return true;
                }
                MineLABFree.getFetchData().addCredit(player, Integer.parseInt(amount));
                commandSender.sendMessage(ChatColor.translateAlternateColorCodes('&', MineLABFree.getLanguageConfiguration().getString("creditGived")
                        .replaceAll("%player%", player.getName())
                        .replaceAll("%amount%", amount)
                ));
            }
            else if(args[0].equalsIgnoreCase("sil")) {
                if(args.length != 2) {
                    commandSender.sendMessage(ChatColor.translateAlternateColorCodes('&', MineLABFree.getLanguageConfiguration().getString("Arguments.noPlayer")));
                    return true;
                }
                Player player = Bukkit.getPlayer(args[1]);
                if(player == null) {
                    commandSender.sendMessage(ChatColor.translateAlternateColorCodes('&', MineLABFree.getLanguageConfiguration().getString("Arguments.noPlayer")));
                    return true;
                }

                String amount = args[2];
                if(args.length != 3 || Integer.parseInt(amount) < 1){
                    commandSender.sendMessage(ChatColor.translateAlternateColorCodes('&', MineLABFree.getLanguageConfiguration().getString("Arguments.noAmount")));
                    return true;
                }
                MineLABFree.getFetchData().deleteCredit(player, Integer.parseInt(amount));
                commandSender.sendMessage(ChatColor.translateAlternateColorCodes('&', MineLABFree.getLanguageConfiguration().getString("creditRemoved")
                        .replaceAll("%player%", player.getName())
                        .replaceAll("%amount%", amount)
                ));
            } else if(args[0].equalsIgnoreCase("bak")) {
                if(args.length != 2){
                    commandSender.sendMessage(ChatColor.translateAlternateColorCodes('&', MineLABFree.getLanguageConfiguration().getString("Arguments.noPlayer")));
                    return true;
                }
                Player player = Bukkit.getPlayer(args[1]);
                if(player == null) {
                    commandSender.sendMessage(ChatColor.translateAlternateColorCodes('&', MineLABFree.getLanguageConfiguration().getString("Arguments.noPlayer")));
                    return true;
                }
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
