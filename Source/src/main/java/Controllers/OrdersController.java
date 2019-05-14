package Controllers;

import Services.ExecuteUpdate;
import Services.ExecuteSelect;

import java.util.ArrayList;
import java.util.HashMap;

public class OrdersController {
    private ExecuteSelect _ec;
    private ExecuteUpdate _eu;

    public OrdersController() {
        _ec = new ExecuteSelect();
        _eu = new ExecuteUpdate();
    }

    //get all customer orders (for admin view)
    public HashMap<Integer, ArrayList<ArrayList>> getAllCustomerOrders() {
        //integer represents orderId arraylist of arraylist represents parts
        HashMap<Integer, ArrayList<ArrayList>> orders = new HashMap<>();
        //get the query from the database
        @SuppressWarnings("unchecked")
        ArrayList<HashMap<String, String>> orderQuery =
                _ec.execute("SELECT O.ORDER_ID, C.CUSTOMER_USERNAME, AG1.ORDERTOTAL, AG2.PART_NAME, AG2.PART_PRICE\n" +
                        "FROM \"ORDER\" O\n" +
                        "JOIN (SELECT L.ORDER_ID, SUM(P.PART_PRICE * LINE_QTY) AS ORDERTOTAL\n" +
                        "      FROM \"ORDER\" O\n" +
                        "      JOIN LINE L on O.ORDER_ID = L.ORDER_ID\n" +
                        "      JOIN PART P on L.PART_ID = P.PART_ID\n" +
                        "      GROUP BY L.ORDER_ID) AG1 ON AG1.ORDER_ID = O.ORDER_ID\n" +
                        "JOIN CUSTOMER C on O.CUSTOMER_ID = C.CUSTOMER_ID\n" +
                        "JOIN (SELECT L.ORDER_ID, P.PART_NAME, P.PART_PRICE\n" +
                        "      FROM LINE L\n" +
                        "      JOIN PART P ON L.PART_ID = P.PART_ID) AG2\n" +
                        "ON AG2.ORDER_ID = O.ORDER_ID");
        //init the variable to check the existing list for the orderid
        Integer currentOrderId;
        //this loop is used to format the query into a easily used format
        for(HashMap h : orderQuery) {
            System.out.println(h);
            //this value will be used to check the existing hashmap for orders
            currentOrderId = Integer.parseInt(h.get("ORDER_ID").toString());

            //each row of the query will be stored here
            Object[] lineValues = h.values().toArray();
            //get the username for the order
            String currentCustomerUsername = h.get("CUSTOMER_USERNAME").toString();
            //get the details for the order
            Double orderTotal = Double.parseDouble(lineValues[0].toString());
            Integer orderId = Integer.parseInt(lineValues[2].toString());
            String partName = lineValues[1].toString();
            Double partPrice = Double.parseDouble(lineValues[3].toString());
            //the order total will be stored at index 0 of the arraylist
            ArrayList<Object> orderMetadata = new ArrayList<>();
            orderMetadata.add(orderTotal);
            orderMetadata.add(currentCustomerUsername);
            //the following arraylists will contain the data for the various parts
            ArrayList<Object> part = new ArrayList<>();
            part.add(partName);
            part.add(partPrice);

            //if there is an order with the same orderid, add the next part to the arraylist
            if (orders.containsKey(currentOrderId)) {
                ArrayList<ArrayList> currentOrderParts = orders.get(currentOrderId);
                currentOrderParts.add(part);
                orders.put(currentOrderId, currentOrderParts);
            }
            //otherwise, add to the hashmap another key value pair
            else {
                //this arraylist contains all information on the order
                ArrayList<ArrayList> orderDetails = new ArrayList<>();
                orderDetails.add(orderMetadata);
                orderDetails.add(part);
                //create a new key value pair in the orders hashmap
                orders.put(orderId, orderDetails);
            }
        }
        return orders;
    }
    //get the customer order and their related parts
    //ex [orderId, [[orderTotal], [partName, partPrice], [part2Name, part2Price]], order2Id etc....]
    public HashMap<Integer, ArrayList<ArrayList>> getCustomerOrders(int customerId) {
        //integer represents orderId arraylist of arraylist represents parts
        HashMap<Integer, ArrayList<ArrayList>> orders = new HashMap<>();
        //get the query from the database
        @SuppressWarnings("unchecked")
        ArrayList<HashMap<String, String>> orderQuery =
                _ec.execute("SELECT O.ORDER_ID, C.CUSTOMER_ID, AG1.ORDERTOTAL, AG2.PART_NAME, AG2.PART_PRICE\n" +
                        "FROM \"ORDER\" O\n" +
                        "JOIN (SELECT L.ORDER_ID, SUM(P.PART_PRICE * LINE_QTY) AS ORDERTOTAL\n" +
                        "      FROM \"ORDER\" O\n" +
                        "      JOIN LINE L on O.ORDER_ID = L.ORDER_ID\n" +
                        "      JOIN PART P on L.PART_ID = P.PART_ID\n" +
                        "      GROUP BY L.ORDER_ID) AG1 ON AG1.ORDER_ID = O.ORDER_ID\n" +
                        "JOIN CUSTOMER C on O.CUSTOMER_ID = C.CUSTOMER_ID\n" +
                        "JOIN (SELECT L.ORDER_ID, P.PART_NAME, P.PART_PRICE\n" +
                        "      FROM LINE L\n" +
                        "      JOIN PART P ON L.PART_ID = P.PART_ID) AG2\n" +
                        "ON AG2.ORDER_ID = O.ORDER_ID " +
                        "WHERE C.CUSTOMER_ID = " + customerId + " AND O.ORDER_PAID = false");
        //init the variable to check the existing list for the orderid
        Integer currentOrderId;
        //this loop is used to format the query into a easily used format
        for(HashMap h : orderQuery) {
            //this value will be used to check the existing hashmap for orders
            currentOrderId = Integer.parseInt(h.get("ORDER_ID").toString());
            //each row of the query will be stored here
            Object[] lineValues = h.values().toArray();
            //get the details for the order
            Double orderTotal = Double.parseDouble(lineValues[0].toString());
            Integer orderId = Integer.parseInt(lineValues[2].toString());
            String partName = lineValues[1].toString();
            Double partPrice = Double.parseDouble(lineValues[3].toString());
            //the order total will be stored at index 0 of the arraylist
            ArrayList<Object> orderMetadata = new ArrayList<>();
            orderMetadata.add(orderTotal);
            //the following arraylists will contain the data for the various parts
            ArrayList<Object> part = new ArrayList<>();
            part.add(partName);
            part.add(partPrice);

            //if there is an order with the same orderid, add the next part to the arraylist
            if (orders.containsKey(currentOrderId)) {
                ArrayList<ArrayList> currentOrderParts = orders.get(currentOrderId);
                currentOrderParts.add(part);
                orders.put(currentOrderId, currentOrderParts);
            }
            //otherwise, add to the hashmap another key value pair
            else {
                //this arraylist contains all information on the order
                ArrayList<ArrayList> orderDetails = new ArrayList<>();
                orderDetails.add(orderMetadata);
                orderDetails.add(part);
                //create a new key value pair in the orders hashmap
                orders.put(orderId, orderDetails);
            }
        }
        return orders;
    }

    public void createCustomerOrder(int customerId) {
        String query = "INSERT INTO \"ORDER\" (\"CUSTOMER_ID\", \"ORDER_PAID\") VALUES ("+customerId+", DEFAULT)";
        _eu.execute(query);
        System.out.println("SUCCESS: " + query);
    }

    //when the user creates a new order, get one that has no parts
    //return the order id
    public int getEmptyCustomerOrderId(int customerId) {
        String query = "SELECT O.ORDER_ID\n" +
                "FROM \"ORDER\" O\n" +
                "LEFT JOIN LINE L ON L.ORDER_ID = O.ORDER_ID\n" +
                "WHERE L.LINE_QTY IS NULL AND CUSTOMER_ID = " + customerId;
        ArrayList<HashMap<String, String>> result = _ec.execute(query);
        int orderId = Integer.parseInt(result.get(0).get("ORDER_ID"));
        return orderId;
    }

    public ArrayList<String> getOrderParts(int orderId, int customerId) {
        ArrayList<String> parts = new ArrayList<>();
        String query = "SELECT P.PART_ID, P.PART_NAME FROM\n" +
                "\"ORDER\" O\n" +
                "JOIN LINE L ON L.ORDER_ID = O.ORDER_ID\n" +
                "JOIN PART P on L.PART_ID = P.PART_ID\n" +
                "WHERE L.ORDER_ID = "+orderId+" AND O.CUSTOMER_ID = " + customerId;
        ArrayList<HashMap<String, String>> orderPartsQuery = _ec.execute(query);
        for (HashMap h: orderPartsQuery) {
            String part = h.get("PART_ID") + ": " + h.get("PART_NAME");
            System.out.println(part);
            parts.add(part);
        }
        return parts;
    }

    public void addPartToCustomerOrder(int orderId, int partId, int quantity) {
        String query = "INSERT INTO \"APP\".\"LINE\" (\"ORDER_ID\", \"PART_ID\", \"LINE_QTY\") " +
                        "VALUES ("+orderId+", "+partId+", "+quantity+")";
        _eu.execute(query);
        System.out.println("SUCCESS: " + query);
    }

    public void removePartFromCustomerOrder(int orderId, int partId) {
        String query = "DELETE FROM LINE WHERE PART_ID = "+partId+" AND ORDER_ID = "+orderId;
        _eu.execute(query);
        System.out.println("SUCCESS: "+ query);
    }

    public void clearOrderParts(int orderId) {
        String query = "DELETE FROM LINE WHERE ORDER_ID = " + orderId;
        _eu.execute(query);
    }

    public double getOrderTotal(int orderId) {
        double total = -99.00;
        String query = "SELECT SUM(AG1.LINETOTAL) ORDERTOTAL\n" +
                "FROM (SELECT\n" +
                "      L.LINE_QTY * P.PART_PRICE LINETOTAL\n" +
                "      FROM LINE L\n" +
                "      JOIN PART P ON L.PART_ID = P.PART_ID\n" +
                "      WHERE L.ORDER_ID = "+orderId+") AG1";
        ArrayList<HashMap<String, String >> orderQuery = _ec.execute(query);
        try {
            total = Double.parseDouble(orderQuery.get(0).get("ORDERTOTAL"));
        } catch (Exception e) {
            System.out.println("Unable to parse total...");
            System.out.println(e);
        }
        return total;
    }

    public void submitOrder(int orderId) {
        String query = "UPDATE \"ORDER\" SET ORDER_PAID = TRUE WHERE ORDER_ID = " + orderId;
        if(_eu.execute(query)) {
            System.out.println("Order purchased");
        } else {
            System.out.println("Something went wrong...");
        }
    }
}
