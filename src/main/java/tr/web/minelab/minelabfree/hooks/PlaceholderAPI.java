package tr.web.minelab.minelabfree.hooks;

import me.clip.placeholderapi.expansion.PlaceholderExpansion;
import org.bukkit.OfflinePlayer;
import org.jetbrains.annotations.NotNull;
import tr.web.minelab.minelabfree.MineLABFree;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;

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
            String lastHelper = null;
            try {
                Statement statement = MineLABFree.getDataSource().getConnection().createStatement();
                ResultSet resultSet = statement.executeQuery("SELECT * FROM `creditHistory` ORDER BY `id` DESC LIMIT 1");
                while (resultSet.next()) {
                    lastHelper = resultSet.getString(2);
                }

                return (lastHelper != null ? lastHelper : "Yok");
            } catch (SQLException ex) {
                return "Bilinmiyor.";
            }
        }

        if(params.equalsIgnoreCase("son_bagisci_kredi")) {
            String lastHelperCredit = null;
            try {
                Statement statement = MineLABFree.getDataSource().getConnection().createStatement();
                ResultSet resultSet = statement.executeQuery("SELECT * FROM `creditHistory` ORDER BY `id` DESC LIMIT 1");
                while (resultSet.next()) {
                    lastHelperCredit = resultSet.getString(7);
                }
                return (lastHelperCredit != null ? lastHelperCredit : "0");
            } catch (SQLException ex) {
                return "Bilinmiyor.";
            }
        }
        if(params.equalsIgnoreCase("kredi")) {
            String credit = null;
            try {
                Statement statement = MineLABFree.getDataSource().getConnection().createStatement();
                ResultSet resultSet = statement.executeQuery("SELECT * FROM `accounts` WHERE `username` = '" + player.getName() + "'");
                ResultSetMetaData rsmd = resultSet.getMetaData();
                int columnCount = rsmd.getColumnCount();
                for (int i = 1; i <= columnCount; i++ ) {
                    String name = rsmd.getColumnName(i);
                    if(name.equals("credit")) {
                        credit = resultSet.getString(i);
                    }
                }
                return credit;
            } catch (SQLException ex) {
                return "0";
            }
        }

        return null;
    }
}
