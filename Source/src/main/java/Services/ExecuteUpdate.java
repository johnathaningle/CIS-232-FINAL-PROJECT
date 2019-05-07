package Services;

import Configuration.Config;

import java.sql.*;
import java.util.HashMap;

public class ExecuteUpdate {
    private Config _config;

    public ExecuteUpdate() {
        _config = new Config();
    }

    public void execute(String query) {
        try {
            DriverManager.registerDriver(new org.apache.derby.jdbc.EmbeddedDriver());
        } catch (Exception e) {
            System.out.println(e);
        }

        try {
            Connection con = DriverManager.getConnection(_config.DB_URL);
            Statement statement = con.createStatement();
            statement.executeUpdate(query);
            statement.close();
            con.close();
        } catch (Exception e) {
            System.out.println(e);
        }
    }
}
