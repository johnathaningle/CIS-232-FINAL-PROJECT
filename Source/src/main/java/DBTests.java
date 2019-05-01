import BootstrapDB.BootstrapDB;
import Controllers.CustomersController;
import Controllers.OrderController;
import Controllers.PartsController;
import Controllers.PresetsController;

import java.util.ArrayList;
import java.util.HashMap;

public class DBTests {
    public static void main(String[] args) {
//        BootstrapDB bootstrapDB = new BootstrapDB();
//        bootstrapDB.ResetDatabase();
        //init the controllers
        PartsController partsController = new PartsController();
        PresetsController presetsController = new PresetsController();
        CustomersController customersController = new CustomersController();
        OrderController orderController = new OrderController();
        //this is how you get the price of an item
        Double price = partsController.getPartPrice(1);
        System.out.println(price);
        //this is how you check if login credentials are correct
        System.out.println(customersController.login("bobbymon", "password"));
        //this is how you get a list of parts for each order and it's total
        HashMap<Integer, ArrayList<ArrayList>> orders = orderController.getCustomerOrders(1);
        System.out.println(orders);
    }
}
