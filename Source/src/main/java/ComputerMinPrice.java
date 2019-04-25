import java.sql.*;

/**
 * This program demos some of the SQL Math functions
 */
public class ComputerMinPrice {

    public static void main(String[] args) {

        //Variables to hold the lowest, highest and
        //average price of coffee
        double lowest = 0.0;
        double highest = 0.0;
        double average = 0.0;
        double count = 0.0;
        double sum = 0.0;


        //Create named constant for the URL
        final String DB_URL = "jdbc:derby:CoffeeDB";

        try {
            //Create a connection to the database
            Connection conn = DriverManager.getConnection(DB_URL);


            //create the Statement object
            Statement stmt = conn.createStatement();

            //Create select statements to get the lowest, highest and average price from Coffee table
            String minStatement = "SELECT MIN(Price) FROM Coffee";
            String maxStatement = "SELECT MAX(Price) FROM Coffee";
            String avgStatement = "SELECT AVG(Price) FROM Coffee";
            String countStatement = "SELECT COUNT(*) FROM COffee";
            String sumStatement = "SELECT SUM(Price) FROM Coffee";


            //Get the lowest price
            ResultSet minResult = stmt.executeQuery(minStatement);
            if (minResult.next())
                lowest = minResult.getDouble(1);

            //Get the highest price
            ResultSet maxResult = stmt.executeQuery(maxStatement);
            if (maxResult.next())
                highest = maxResult.getDouble(1);

            //Get the average price
            ResultSet avgResult = stmt.executeQuery(avgStatement);
            if (avgResult.next())
                average = avgResult.getDouble(1);

            //Get the count of rows
            ResultSet countResult = stmt.executeQuery(countStatement);
            if (countResult.next())
                count = countResult.getDouble(1);

            //Get the sum of the coffee prices
            ResultSet sumResult = stmt.executeQuery(sumStatement);
            if (sumResult.next())
                sum = sumResult.getDouble(1);

            //display results
            System.out.printf("Lowest price: $%.2f\n", lowest);
            System.out.printf("Highest price: $%.2f\n", highest);
            System.out.printf("Average price: $%.2f\n", average);
            System.out.printf("The number of rows: %.0f\n", count);
            System.out.printf("The sum of the prices: $%.2f\n", sum);

            

            //close the connection
            conn.close();

        } catch (Exception ex) {
            System.out.println("ERROR: " + ex.getMessage());
        }
    }
}