package tr.web.minelab.minelabfree.guis;

import com.cryptomorin.xseries.XMaterial;
import dev.triumphteam.gui.builder.item.ItemBuilder;
import dev.triumphteam.gui.guis.Gui;
import dev.triumphteam.gui.guis.GuiItem;
import me.clip.placeholderapi.PlaceholderAPI;
import net.kyori.adventure.text.Component;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.SkullMeta;
import tr.web.minelab.minelabfree.MineLABFree;

import java.util.stream.Collectors;

public class ProfileGui {

    Gui gui;
    public ProfileGui(Player player) {
        this.gui = Gui.gui()
                .rows(MineLABFree.getInstance().getConfig().getInt("ProfileGui.Rows"))
                .title(Component.text(ChatColor.translateAlternateColorCodes('&', MineLABFree.getInstance().getConfig().getString("ProfileGui.Title"))))
                .disableItemSwap()
                .disableItemDrop()
                .disableItemPlace()
                .disableItemTake()
                .create();
        for(String slot : MineLABFree.getInstance().getConfig().getConfigurationSection("ProfileGui.Slots").getKeys(false)) {
            ItemStack itemStack;
            if(MineLABFree.getInstance().getConfig().getString("ProfileGui.Slots." + slot + ".Material").equals("PLAYER_HEAD")) {
                itemStack = XMaterial.PLAYER_HEAD.parseItem();
                SkullMeta skullMeta = (SkullMeta) itemStack.getItemMeta();
                skullMeta.setOwner(player.getName());
                skullMeta.setDisplayName(ChatColor.translateAlternateColorCodes('&', MineLABFree.getInstance().getConfig().getString("ProfileGui.Slots." + slot + ".Name")));

                skullMeta.setLore(MineLABFree.getInstance().getConfig().getStringList("ProfileGui.Slots." + slot + ".Lore")
                        .stream()
                        .map(line ->
                                ChatColor.translateAlternateColorCodes('&', PlaceholderAPI.setPlaceholders(player, line))
                                        .replaceAll("%instagram%", MineLABFree.getFetchData().getInstagram(player.getUniqueId()))
                                        .replaceAll("%youtube%", MineLABFree.getFetchData().getYouTube(player.getUniqueId()))
                                        .replaceAll("%skype%", MineLABFree.getFetchData().getSkype(player.getUniqueId()))
                                        .replaceAll("%discord%", MineLABFree.getFetchData().getDiscord(player.getUniqueId()))
                                        .replaceAll("%twitter%", MineLABFree.getFetchData().getTwitter(player.getUniqueId()))
                        )
                        .collect(Collectors.toList()));

                itemStack.setItemMeta(skullMeta);
            } else {
                itemStack = XMaterial.valueOf(MineLABFree.getInstance().getConfig().getString("ProfileGui.Slots." + slot + ".Material")).parseItem();
                ItemMeta itemMeta = itemStack.getItemMeta();
                itemMeta.setDisplayName(ChatColor.translateAlternateColorCodes('&', MineLABFree.getInstance().getConfig().getString("ProfileGui.Slots." + slot + ".Name")));
                itemMeta.setLore(MineLABFree.getInstance().getConfig().getStringList("ProfileGui.Slots." + slot + ".Lore")
                        .stream()
                        .map(line ->
                                ChatColor.translateAlternateColorCodes('&', PlaceholderAPI.setPlaceholders(player, line))
                                        .replaceAll("%instagram%", MineLABFree.getFetchData().getInstagram(player.getUniqueId()))
                                        .replaceAll("%youtube%", MineLABFree.getFetchData().getYouTube(player.getUniqueId()))
                                        .replaceAll("%skype%", MineLABFree.getFetchData().getSkype(player.getUniqueId()))
                                        .replaceAll("%discord%", MineLABFree.getFetchData().getDiscord(player.getUniqueId()))
                                        .replaceAll("%twitter%", MineLABFree.getFetchData().getTwitter(player.getUniqueId()))
                        )
                        .collect(Collectors.toList()));
                itemStack.setItemMeta(itemMeta);
            }
            GuiItem guiItem = ItemBuilder.from(itemStack).asGuiItem(event -> {
                event.setCancelled(true);
            });
            this.gui.setItem(MineLABFree.getInstance().getConfig().getInt("ProfileGui.Slots." + slot + ".Slot"), guiItem);
        }
        this.gui.open(player);
    }

}
