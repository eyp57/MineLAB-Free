package tr.web.minelab.minelabfree;

import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.entities.Activity;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;
import tr.web.minelab.minelabfree.commands.ProfileCommand;
import tr.web.minelab.minelabfree.commands.TopCommand;
import tr.web.minelab.minelabfree.hooks.PlaceholderAPI;
import tr.web.minelab.minelabfree.utils.Bot;
import tr.web.minelab.minelabfree.utils.DataSource;
import tr.web.minelab.minelabfree.utils.FetchData;

import java.sql.SQLException;
import java.util.Random;
import java.util.Set;

public final class MineLABFree extends JavaPlugin implements Listener {

    private static MineLABFree instance;
    private static DataSource dataSource;
    private static FetchData fetchData;

    @Override
    public void onEnable() {
        // Plugin startup logic
        instance = this;
        if(getConfig().getBoolean("Discord.Bot.Durum")) {
            System.out.println("Bot aktifleştiriliyor.");
            getServer().getPluginManager().registerEvents(new Bot(), this);

        } else {
            System.out.println("Bot configden kapatılmış.");
        }
        saveDefaultConfig();
        try {
            dataSource = new DataSource();
            fetchData = new FetchData();
            System.out.println("Database bağlantısı kuruldu.");
        } catch (SQLException ex) {
            ex.printStackTrace();
            System.out.println("Database bağlantısı kurulamadı eklenti kapatılıyor...");
            this.setEnabled(false);
            return;
        }

        if(getServer().getPluginManager().getPlugin("PlaceholderAPI") != null) {
            System.out.println("PlaceholderAPI bulundu entegre edildi.");
            new PlaceholderAPI().register();
            Bukkit.getPluginManager().registerEvents(this, this);
            new BukkitRunnable() {
                @Override
                public void run() {
                    getLogger().info("Son Bağışcı & TopList yenilendi & ve Diğer datalar");
                    fetchData.getLastSupporterCredit();
                    fetchData.getLastSupporter();
                    fetchData.updateTop10();
                    for (Player player : Bukkit.getOnlinePlayers()) {
                        fetchData.updateCredit(player);
                        fetchData.updateSocialAccounts(player);
                    }
                }
            }.runTaskTimerAsynchronously(this, 0L, 5*1200L);

        } else {
            this.setEnabled(false);
            System.out.println("PlaceholderAPI bulunamadı plugin deaktifleşiyor.");
        }

        getServer().getPluginCommand("topkredi").setExecutor(new TopCommand());
        getServer().getPluginCommand("profile").setExecutor(new ProfileCommand());
    }
    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }

    public static MineLABFree getInstance() {
        return instance;
    }
    public static DataSource getDataSource() {
        return dataSource;
    }
    public static FetchData getFetchData() {
        return fetchData;
    }


    @EventHandler
    public void onPlayerJoinEvent(PlayerJoinEvent event) {
        this.getServer().getScheduler().runTaskAsynchronously(this, () -> {
            fetchData.updateCredit(event.getPlayer());
            fetchData.updateSocialAccounts(event.getPlayer());
        });
    }
}
