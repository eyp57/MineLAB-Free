package tr.web.minelab.minelabfree.guis;

import com.cryptomorin.xseries.XMaterial;
import dev.triumphteam.gui.builder.item.ItemBuilder;
import dev.triumphteam.gui.guis.Gui;
import dev.triumphteam.gui.guis.GuiItem;
import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import tr.web.minelab.minelabfree.MineLABFree;

import java.util.stream.Collectors;

public class ShopGui {

    Gui gui;
    public ShopGui(Player player) {
        this.gui = Gui.gui()
                .title(Component.text(ChatColor.translateAlternateColorCodes('&', MineLABFree.getInstance().getConfig().getString("ShopGui.Title"))))
                .rows(MineLABFree.getInstance().getConfig().getInt("ShopGui.Rows"))
                .disableItemSwap()
                .disableItemDrop()
                .disableItemPlace()
                .disableItemTake()
                .create();
        for(String slot : MineLABFree.getInstance().getConfig().getConfigurationSection("ShopGui.Products").getKeys(false)) {
            ItemStack itemStack = XMaterial.valueOf(MineLABFree.getInstance().getConfig().getString("ShopGui.Products." + slot + ".Material")).parseItem();
            ItemMeta itemMeta = itemStack.getItemMeta();

            itemMeta.setLore(MineLABFree.getInstance().getConfig().getStringList("ShopGui.Products." + slot + ".Lore")
                    .stream()
                    .map(line -> ChatColor.translateAlternateColorCodes('&', line
                            .replaceAll("%player%", player.getName())
                            .replaceAll("%oyuncu%", player.getName())
                            .replaceAll("%kredi%", MineLABFree.getFetchData().getCredit(player.getUniqueId()))))
                    .collect(Collectors.toList()));
            itemMeta.setDisplayName(ChatColor.translateAlternateColorCodes('&', MineLABFree.getInstance().getConfig().getString("ShopGui.Title")));
            itemStack.setItemMeta(itemMeta);

            GuiItem guiItem = ItemBuilder.from(itemStack).asGuiItem(event -> {
                Player p = (Player) event.getWhoClicked();
                event.setCancelled(true);
                if (Integer.parseInt(MineLABFree.getFetchData().getCredit(p.getUniqueId())) >= MineLABFree.getInstance().getConfig().getInt("ShopGui.Products." + slot + ".Cost")) {
                    MineLABFree.getFetchData().deleteCredit(player, MineLABFree.getInstance().getConfig().getInt("ShopGui.Products." + slot + ".Cost"));
                    MineLABFree.getInstance().getConfig().getStringList("ShopGui.Products." + slot + ".Commands").stream()
                            .map(command -> Bukkit.dispatchCommand(Bukkit.getConsoleSender(), command
                                    .replaceAll("%player%", p.getName())
                                    .replaceAll("%oyuncu%", p.getName())
                            )).collect(Collectors.toList());
                    p.sendMessage(ChatColor.translateAlternateColorCodes('&', MineLABFree.getLanguageConfiguration().getString("Shop.success")
                            .replaceAll("%credit%", MineLABFree.getFetchData().getCredit(p.getUniqueId()))
                            .replaceAll("%product%", slot)
                    ));
                } else
                    p.sendMessage(ChatColor.translateAlternateColorCodes('&', MineLABFree.getLanguageConfiguration().getString("Shop.error")
                            .replaceAll("%credit%", MineLABFree.getFetchData().getCredit(p.getUniqueId()))
                            .replaceAll("%required_credit%", String.valueOf(MineLABFree.getInstance().getConfig().getInt("ShopGui.Products." + slot + ".Cost")))
                            .replaceAll("%product%", slot)
                    ));
            });

            this.gui.setItem(MineLABFree.getInstance().getConfig().getInt("ShopGui.Products." + slot + ".Slot"), guiItem);
        }
        this.gui.open(player);
    }

}
