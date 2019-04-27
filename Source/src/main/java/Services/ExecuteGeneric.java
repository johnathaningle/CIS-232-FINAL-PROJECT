package Services;

import Configuration.Config;

import java.sql.*;

public class ExecuteGeneric {
    private Config _config = new Config();

    public void execute(String query) {
        try {
            DriverManager.registerDriver(new org.apache.derby.jdbc.EmbeddedDriver());
        } catch (Exception e) {
            System.out.println(e);
        }
        final String DB_URL = _config.DB_URL;

        try{
            Connection con = DriverManager.getConnection(DB_URL);
            Statement statement = con.createStatement();
            statement.execute(query);
            statement.close();
            con.close();
            System.out.println("QUERY SUCCESSFUL...");

        } catch (Exception e) {
            if (query.contains("DROP")) {
                System.out.println("Cannot Drop Table");
            } else {
                System.out.println(e);
            }

        }
    }

}
