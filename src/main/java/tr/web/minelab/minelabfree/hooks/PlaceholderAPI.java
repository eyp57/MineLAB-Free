package tr.web.minelab.minelabfree.hooks;

import me.clip.placeholderapi.expansion.PlaceholderExpansion;
import org.bukkit.OfflinePlayer;
import org.jetbrains.annotations.NotNull;
import tr.web.minelab.minelabfree.MineLABFree;

public class PlaceholderAPI extends PlaceholderExpansion {

    @Override
    public @NotNull String getIdentifier() {
        return "minelab";
    }

    @Override
    public @NotNull String getAuthor() {
        return "zRooter";
    }

    @Override
    public @NotNull String getVersion() {
        return "1.0.0";
    }
    @Override
    public String onRequest(OfflinePlayer player, String params) {

        if(params.equalsIgnoreCase("son_bagisci")) {
            return MineLABFree.getFetchData().lastSupporter;
        }

        if(params.equalsIgnoreCase("son_bagisci_kredi")) {
            return MineLABFree.getFetchData().lastSupporterCredit;
        }
        if(params.equalsIgnoreCase("kredi")) {
            return MineLABFree.getFetchData().getCredit(player.getUniqueId());
        }

        return null;
    }
}
