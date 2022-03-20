package tr.web.minelab.minelabfree;

import org.bukkit.Bukkit;
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
import tr.web.minelab.minelabfree.utils.Metrics;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.Callable;

public final class MineLABFree extends JavaPlugin implements Listener {

    private static MineLABFree instance;
    private static DataSource dataSource;
    private static FetchData fetchData;

    @Override
    public void onEnable() {
        // Plugin startup logic
        instance = this;
        int metricsId = 14696;
        Metrics metrics = new Metrics(this, metricsId);
        String javaVersion = System.getProperty("java.version");
        metrics.addCustomChart(new Metrics.DrilldownPie("javaVersion", () -> {
            Map<String, Map<String, Integer>> map = new HashMap<>();
            Map<String, Integer> entry = new HashMap<>();
            entry.put(javaVersion, 1);
            if (javaVersion.startsWith("1.8")) {
                map.put("Java 1.8", entry);
            } else if (javaVersion.startsWith("9")) {
                map.put("Java 9", entry);
            } else if (javaVersion.startsWith("11")) {
                map.put("Java 11", entry);
            } else if (javaVersion.startsWith("12")) {
                map.put("Java 12", entry);
            } else if (javaVersion.startsWith("16")) {
                map.put("Java 16", entry);
            } else if (javaVersion.startsWith("17")) {
                map.put("Java 17", entry);
            } else {
                map.put("Other", entry);
            }
            return map;
        }));

        System.out.println("Kullanılan java sürümü: " + javaVersion);

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
