import BootstrapDB.BootstrapDB;
import Controllers.CustomersController;
import Controllers.OrdersController;
import Controllers.PartsController;
import Controllers.PresetsController;

import java.lang.reflect.Array;
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
        OrdersController ordersController = new OrdersController();

        //this is how you get the price of an item
        Double price = partsController.getPartPrice(1);
        System.out.println(price);

        //get all parts
        ArrayList<HashMap<String, String>> allParts = partsController.getParts();
        for (HashMap h: allParts) {
            System.out.println(h);
        }

        //get the attributes for a certain part
        ArrayList partAttributes = partsController.getPartAttributes(1);
        for (int i = 0; i < partAttributes.size(); i++) {
            System.out.println(partAttributes.get(i));
        }

        //this is how you check if login credentials are correct
        System.out.println(customersController.login("bobbymon", "password"));

        //this is how you create an order
        ordersController.createCustomerOrder(1);
        //this is how you get a list of parts for each order and it's total
        HashMap<Integer, ArrayList<ArrayList>> orders = ordersController.getCustomerOrders(1);
        System.out.println(orders);

        //get the id of a part
        System.out.println(partsController.getPartId("Samsung SSD"));

        //get preset names
        ArrayList<String> presetNames = new ArrayList<>();
        ArrayList<HashMap<String, String>> presets = presetsController.getPresets();
        for (HashMap h : presets) {
            String presetId = h.get("PRESET_ID").toString();
            String presetName = h.get("PRESET_NAME").toString();
            presetNames.add(presetId+ ": " + presetName);
        }

        //get the parts for a preset
        presetsController.getPresetParts(4);
//        ArrayList<HashMap<String, String>> presetParts = presetsController.getPresetParts();


    }
}
