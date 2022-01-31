package tr.web.minelab.minelabfree.utils;

import org.bukkit.entity.Player;
import tr.web.minelab.minelabfree.MineLABFree;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class FetchData {

    // SQL den verileri çek.

    public String lastSupporter = "Yok";
    public String lastSupporterCredit = "0";

    public Map<UUID, String> credits = new HashMap<>();

    public String getLastSupporter() {
        try {
            Statement statement = MineLABFree.getDataSource().getConnection().createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT * FROM `creditHistory` ORDER BY `id` DESC LIMIT 1");
            if(resultSet.next())
                lastSupporter = resultSet.getString("username");


            return lastSupporter;
        } catch (SQLException ex) {
            return "dbErr";
        }
    }
    public String getLastSupporterCredit() {
        try {
            Statement statement = MineLABFree.getDataSource().getConnection().createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT * FROM `creditHistory` ORDER BY `id` DESC LIMIT 1");
            if(resultSet.next())
                lastSupporterCredit = resultSet.getString("amount");
            return lastSupporterCredit;
        } catch (SQLException ex) {
            return "dbErr";
        }
    }

    public void updateCredit(Player player) {
        String credit = "0";
        try {
            Statement statement = MineLABFree.getDataSource().getConnection().createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT * FROM `accounts` WHERE `username` = '" + player.getName() + "'");
            if (resultSet.next())
                credit = resultSet.getString("credit");
            credits.put(player.getUniqueId(), credit);
        } catch (SQLException ex) {
             credits.put(player.getUniqueId(), "0");
        }
    }

    public String getCredit(UUID uuid) {
        String credit = credits.get(uuid);
        return credit;
    }
    public void deleteCreditFromMap(UUID uuid) {
        credits.remove(uuid);
    }

}
