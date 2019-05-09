import java.sql.*;
import java.util.Scanner;

public class ComputerUpdater {
    public static void main(String[] args) {
        String description;
        Double price;

        //Create named constant for the URL
        final String DB_URL = "jdbc:derby:ComputerDB";

        //Create a Scanner object for the keyboard input
        Scanner keyboard = new Scanner(System.in);

        try
        {
            //Create a connection to the database
            Connection conn = DriverManager.getConnection(DB_URL);

            //Get the data for the new Computer price
            System.out.println("Enter the Computer description: ");
            description = keyboard.nextLine();
            System.out.println("Enter the new price: ");
            price = keyboard.nextDouble();

            //create the Statement object
            Statement stmt = conn.createStatement();

            //Print the list of Computers
            String ComputerListStatement = "SELECT * FROM Computer";
            ResultSet allComputers = stmt.executeQuery(ComputerListStatement);

            while(allComputers.next())
            {
                System.out.printf("Description %s \t ProdNum %s \t Price $%.2f\n",
                        allComputers.getString("Description"),
                        allComputers.getString("ProdNum"),
                        allComputers.getDouble("Price"));
            }

            //Create a string with an INSERT statement
            String sqlStatement = "UPDATE Computer " +
                    "SET Price = " + price +
                    "WHERE Description LIKE '%" +
                    description + "%'";


            //Send the statement to the DBMS
            int rows = stmt.executeUpdate(sqlStatement);

            //display results
            System.out.println(rows + " row(s) updated in the table.");

            //Print the list of Computers
            //String ComputerListStatement = "SELECT * FROM Computer";
            ResultSet allComputersUpdate = stmt.executeQuery(ComputerListStatement);

            while(allComputersUpdate.next())
            {
                System.out.printf("Description %s \t ProdNum %s \t Price $%.2f\n",
                        allComputersUpdate.getString("Description"),
                        allComputersUpdate.getString("ProdNum"),
                        allComputersUpdate.getDouble("Price"));
            }

            //close the connection
            conn.close();



        }
        catch(Exception ex)
        {
            System.out.println("ERROR: " + ex.getMessage());
        }

    }
}
