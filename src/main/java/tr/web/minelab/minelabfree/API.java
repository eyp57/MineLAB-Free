package tr.web.minelab.minelabfree;

import org.bukkit.entity.Player;
import tr.web.minelab.minelabfree.utils.DataSource;

public class API {
    public static String getTwitter(Player player) {
        return MineLABFree.getFetchData().getTwitter(player.getUniqueId());
    }
    public static String getDiscord(Player player) {
        return MineLABFree.getFetchData().getDiscord(player.getUniqueId());
    }
    public static String getSkype(Player player) {
        return MineLABFree.getFetchData().getSkype(player.getUniqueId());
    }
    public static String getYoutube(Player player) {
        return MineLABFree.getFetchData().getYouTube(player.getUniqueId());
    }
    public static DataSource getDataSource() {
        return MineLABFree.getDataSource();
    }
}
