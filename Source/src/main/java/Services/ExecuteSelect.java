package Services;

import Configuration.Config;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;

public class ExecuteSelect {
    private Config _config = new Config();

    public ArrayList execute(String query) {
        ArrayList<HashMap<String, String>> result = new ArrayList<>();
        try {
            DriverManager.registerDriver(new org.apache.derby.jdbc.EmbeddedDriver());
        } catch (Exception e) {
            System.out.println(e);
        }

        try {
            Connection con = DriverManager.getConnection(_config.DB_URL);
            Statement statement = con.createStatement();
            ResultSet resultSet = statement.executeQuery(query);
            ResultSetMetaData metaData = resultSet.getMetaData();
            int numColumns = metaData.getColumnCount();
            while(resultSet.next()) {
                HashMap part = new HashMap();
                for(int i = 1; i <= numColumns; i++) {
                    String key = metaData.getColumnName(i);
                    String value = resultSet.getString(i);
                    part.put(key, value);
                }
                result.add(part);
            }
            statement.close();
            con.close();

        } catch (Exception e) {
            System.out.println(e);
            result = null;
        }
        return result;
    }

}
