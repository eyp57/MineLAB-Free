package tr.web.minelab.minelabfree.utils;

import tr.web.minelab.minelabfree.MineLABFree;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DataSource {

    Connection con = null;

    public DataSource() throws SQLException {
        String username = MineLABFree.getInstance().getConfig().getString("Database.username");
        String host = MineLABFree.getInstance().getConfig().getString("Database.host");
        String password = MineLABFree.getInstance().getConfig().getString("Database.password");
        String database = MineLABFree.getInstance().getConfig().getString("Database.database");

        con = DriverManager.getConnection("jdbc:mysql://"+host+"/" + database,username,password);
    }

    public Connection getConnection() {
        return con;
    }
}
