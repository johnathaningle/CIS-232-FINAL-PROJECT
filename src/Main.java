import java.sql.*;

public class Main {
    public static void main(String[] args) {
        try {
            Connection conn = DriverManager.getConnection("jdbc:mysql://root@localhost:3306/largeco" );
            Statement statement = conn.createStatement();
            ResultSet res = statement.executeQuery("SELECT * FROM LGEMPLOYEE");
            ResultSetMetaData metaData = res.getMetaData();
            int colCount = metaData.getColumnCount();
            while(res.next()) {
                for (int i = 1; i <= colCount; i++) {
                    System.out.print(res.getString(i) + " ");
                }
                System.out.println();
            }
            conn.close();
        } catch (Exception e) {
            System.out.println(e);
        }
    }
}
