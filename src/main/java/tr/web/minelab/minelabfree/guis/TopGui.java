package tr.web.minelab.minelabfree.guis;

import com.cryptomorin.xseries.XMaterial;
import dev.triumphteam.gui.builder.item.ItemBuilder;
import dev.triumphteam.gui.guis.Gui;
import dev.triumphteam.gui.guis.GuiItem;
import net.kyori.adventure.text.Component;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.SkullMeta;
import tr.web.minelab.minelabfree.MineLABFree;

import java.util.*;
import java.util.stream.Collectors;

public class TopGui {

    Gui gui;
    public TopGui(Player player) {
        this.gui = Gui.gui()
                .title(Component.text(ChatColor.translateAlternateColorCodes('&', MineLABFree.getInstance().getConfig().getString("TopGui.Title"))))
                .rows(3)
                .disableItemSwap()
                .disableItemDrop()
                .disableItemPlace()
                .disableItemTake()
                .create();
        Map<String, Integer> top10 = MineLABFree.getFetchData().top10;
        for(Map.Entry<String, Integer> x : top10.entrySet()) {

            ItemStack itemSkull = XMaterial.PLAYER_HEAD.parseItem();
            SkullMeta skullMeta = (SkullMeta) itemSkull.getItemMeta();
            skullMeta.setOwner(x.getKey());
            skullMeta.setLore(
                    MineLABFree.getInstance().getConfig().getStringList("TopGui.ItemLore")
                            .stream()
                            .map(line -> ChatColor.translateAlternateColorCodes('&', line
                                    .replaceAll("%oyuncu%", x.getKey())
                                    .replaceAll("%kredi%", String.valueOf(x.getValue()))))
                            .collect(Collectors.toList()));
            skullMeta.setDisplayName(ChatColor.translateAlternateColorCodes('&', "&e" + x.getKey()));
            itemSkull.setItemMeta(skullMeta);
            GuiItem guiItem = ItemBuilder.from(itemSkull).asGuiItem(event -> {
                event.setCancelled(true);
            });
            this.gui.addItem(guiItem);
        }
        this.gui.open(player);
    }
}
