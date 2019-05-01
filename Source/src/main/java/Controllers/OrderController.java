package Controllers;

import Services.ExecuteSelect;

import java.util.ArrayList;
import java.util.HashMap;

public class OrderController {
    private ExecuteSelect _ec;

    public OrderController() {
        _ec = new ExecuteSelect();
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
                        "ON AG2.ORDER_ID = O.ORDER_ID");
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

}
