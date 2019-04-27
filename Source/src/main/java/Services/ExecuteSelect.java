package Services;

import java.sql.*;

public class ExecuteSelect {
    private ResultSet _resultSet;
    private ResultSetMetaData _resultSetMetaData;

    public ExecuteSelect(String query) {
        _resultSet = null;
        _resultSetMetaData = null;
        try {
            DriverManager.registerDriver(new org.apache.derby.jdbc.EmbeddedDriver());
        } catch (Exception e) {
            System.out.println(e);
        }
        final String DB_URL = "jdbc:derby:TestDB";

        try{
            Connection con = DriverManager.getConnection(DB_URL);
            Statement statement = con.createStatement();
            _resultSet = statement.executeQuery(query);
            try {
                _resultSetMetaData = _resultSet.getMetaData();
            } catch (Exception e) {
                System.out.println(e);
            }
            statement.close();
            con.close();
            System.out.println("Done");

        } catch (Exception e) {
            System.out.println(e);
        }
    }

    public ResultSet get_resultSet() {
        return _resultSet;
    }

    public ResultSetMetaData get_resultSetMetaData() {
        return _resultSetMetaData;
    }
}
