package tr.web.minelab.minelabfree.utils;

import org.bukkit.entity.Player;
import tr.web.minelab.minelabfree.MineLABFree;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.UUID;

public class FetchData {

    public String lastSupporter = "Yok";
    public String lastSupporterCredit = "0";

    public Map<UUID, String> credits = new HashMap<>();
    public Map<UUID, String> instagrams = new HashMap<>();
    public Map<UUID, String> twitters = new HashMap<>();
    public Map<UUID, String> youtubes = new HashMap<>();
    public Map<UUID, String> discords = new HashMap<>();
    public Map<UUID, String> skypes = new HashMap<>();
    public Map<String, Integer> top10 = new LinkedHashMap<>();

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
    public void updateSocialAccounts(Player player) {
        String instagram = "-";
        String discord = "-";
        String twitter = "-";
        String skype = "-";
        String youtube = "-";
        try {
            Statement statement = MineLABFree.getDataSource().getConnection().createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT * FROM `accounts` WHERE `username` = '" + player.getName() + "'");
            if(resultSet.next()) {
                instagram = resultSet.getString("instagram");
                discord = resultSet.getString("discord");
                twitter = resultSet.getString("twitter");
                skype = resultSet.getString("skype");
                youtube = resultSet.getString("youtube");
            }
            instagrams.put(player.getUniqueId(), instagram);
            discords.put(player.getUniqueId(), discord);
            twitters.put(player.getUniqueId(), twitter);
            youtubes.put(player.getUniqueId(), youtube);
            skypes.put(player.getUniqueId(), skype);
        } catch (SQLException e) {
            instagrams.put(player.getUniqueId(), "-");
            discords.put(player.getUniqueId(), "-");
            skypes.put(player.getUniqueId(), "-");
            youtubes.put(player.getUniqueId(), "-");
            twitters.put(player.getUniqueId(), "-");
        }
    }
    public void updateTop10() {
        int limit = 26;
        if(MineLABFree.getInstance().getConfig().getInt("TopGui.Players") <= 26)
            limit = MineLABFree.getInstance().getConfig().getInt("TopGui.Players");
        try {
            Statement statement = MineLABFree.getDataSource().getConnection().createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT * FROM `accounts` ORDER BY `credit` DESC LIMIT " + limit);
            while(resultSet.next()) {
                String name = resultSet.getString("username");
                int credit = resultSet.getInt("credit");
                this.top10.put(name, credit);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    public String getCredit(UUID uuid) {
        return credits.get(uuid);
    }
    public String getInstagram(UUID uuid) {
        return instagrams.get(uuid);
    }
    public String getYouTube(UUID uuid) {
        return youtubes.get(uuid);
    }
    public String getSkype(UUID uuid) {
        return skypes.get(uuid);
    }
    public String getTwitter(UUID uuid) {
        return twitters.get(uuid);
    }
    public String getDiscord(UUID uuid) {
        return discords.get(uuid);
    }
    public void deleteCreditFromMap(UUID uuid) {
        credits.remove(uuid);
    }

}
