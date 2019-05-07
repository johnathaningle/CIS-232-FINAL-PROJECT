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
                        "WHERE C.CUSTOMER_ID = " + customerId);
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

}
