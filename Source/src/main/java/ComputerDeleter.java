import java.util.Scanner;
import java.sql.*;
/**
 * This program lets the user delete a coffee
 * from the CoffeeDB database's Coffee table
 */
public class ComputerDeleter
{
    public static void main(String[] args) {
        String sure;
        String prodNum;

        //Create named constant for the URL
        final String DB_URL = "jdbc:derby:CoffeeDB";

        //Create a Scanner object for the keyboard input
        Scanner keyboard = new Scanner(System.in);

        try
        {
            //Create a connection to the database
            Connection conn = DriverManager.getConnection(DB_URL);

            //create the Statement object
            Statement stmt = conn.createStatement();

            //Get the product number to delete
            System.out.println("Enter the product number: ");
            prodNum  = keyboard.nextLine();

            //Display the coffee's current data
            if (findAndDisplayProduct(stmt, prodNum))
            {
                //Make sure user wants to delete
                System.out.print("Are you sure you want to delete this item(Y/N)? ");
                sure = keyboard.nextLine();
                if(Character.toUpperCase(sure.charAt(0)) == 'Y')
                {
                    //Delete the specified coffee
                    deleteCoffee(stmt, prodNum);
                }
                else
                {
                    System.out.println("The item was not deleted.");
                }

            }
            else
            {
                //The specified product number was not found
                System.out.println("That product is not found.");
            }

            //close the connection
            conn.close();

        }
        catch(Exception ex)
        {
            System.out.println("ERROR: " + ex.getMessage());
        }

    }

    /**findAndDisplayProduct method
     *
     */
    public static boolean findAndDisplayProduct(Statement stmt,
                                                String prodNum
                                                ) throws SQLException
    {
        boolean productFound;

        //Create SELECT stmt
        String sqlStatement =
                "SELECT * FROM Coffee WHERE ProdNum = '" +
                        prodNum + "'";

        //Send that SQL stmt DBMS
        ResultSet result = stmt.executeQuery(sqlStatement);

        //display contents of resultset
        if(result.next())
        {
            //display the product
            System.out.println("Description: " + result.getString(1));
            productFound = true;
        }
        else
        {
            productFound = false;
        }
        return productFound;
    }

    /** deleteCoffee method
     *
     */

    public static void deleteCoffee(Statement stmt, String prodNum) throws SQLException
    {
        //Create the DELETE stmt
        String sqlStatement = "DELETE FROM Coffee " +
                "WHERE ProdNum = '" + prodNum + "'";

        //Send the DELETE statement
        int rows = stmt.executeUpdate(sqlStatement);

        //Display results
        System.out.println(rows + " row(s) deleted.");

    }

}
