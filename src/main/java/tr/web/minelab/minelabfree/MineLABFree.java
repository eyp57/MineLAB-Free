package tr.web.minelab.minelabfree;

import org.bukkit.plugin.java.JavaPlugin;
import tr.web.minelab.minelabfree.hooks.PlaceholderAPI;
import tr.web.minelab.minelabfree.utils.DataSource;

import java.sql.SQLException;

public final class MineLABFree extends JavaPlugin {

    private static MineLABFree instance;
    private static DataSource dataSource;
    @Override
    public void onEnable() {
        // Plugin startup logic
        instance = this;
        saveDefaultConfig();
        try {
            dataSource = new DataSource();
            System.out.println("Database bağlantısı kuruldu.");
        } catch (SQLException ex) {
            ex.printStackTrace();
            System.out.println("Database bağlantısı kurulamadı eklenti kapatılıyor...");
            getServer().getPluginManager().disablePlugin(this);
        }

        if(getServer().getPluginManager().getPlugin("PlaceholderAPI") != null) {
            System.out.println("PlaceholderAPI bulundu entegre edildi.");
            new PlaceholderAPI().register();

        } else {
            System.out.println("PlaceholderAPI bulunamadı placeholderlar kullanılamayacak.");
        }
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
}
