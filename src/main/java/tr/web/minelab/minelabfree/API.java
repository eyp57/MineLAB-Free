package tr.web.minelab.minelabfree;

import net.dv8tion.jda.api.JDA;
import org.bukkit.entity.Player;
import tr.web.minelab.minelabfree.utils.Bot;
import tr.web.minelab.minelabfree.utils.DataSource;

public class API {
    private static JDA jda = Bot.jda;

    public static JDA getJDA() {
        return jda;
    }
    public static String getInstagram(Player player) {
        return MineLABFree.getFetchData().getInstagram(player.getUniqueId());
    }
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
