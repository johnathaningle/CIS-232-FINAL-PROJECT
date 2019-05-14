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
//
//        ordersController.clearOrderParts(1);


    }
}
