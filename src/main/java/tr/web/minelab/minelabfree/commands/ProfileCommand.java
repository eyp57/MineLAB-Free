package tr.web.minelab.minelabfree.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import tr.web.minelab.minelabfree.guis.ProfileGui;

public class ProfileCommand implements CommandExecutor {
    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {

        if(sender instanceof Player) {
            Player p = ((Player) sender).getPlayer();
            new ProfileGui(p);
        } else if(sender instanceof ConsoleCommandSender) {
            sender.sendMessage("Bu komutu sadece oyunda kullanabilirsin.");
        }
        return false;
    }
}
