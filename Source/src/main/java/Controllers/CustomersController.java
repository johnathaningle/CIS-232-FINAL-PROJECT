package Controllers;

import Services.ExecuteSelect;
import Services.ExecuteUpdate;

import java.util.ArrayList;
import java.util.HashMap;

public class CustomersController {
    private ExecuteSelect _ec;
    private ExecuteUpdate _eu;
    private int _currentCustomerId;

    public CustomersController() {
        _ec = new ExecuteSelect();
        _eu = new ExecuteUpdate();
    }

    public int getCustomerId(String username) {
       @SuppressWarnings("unchecked")
       ArrayList<String> customer =
               _ec.execute("SELECT CUSTOMER_ID FROM APP.CUSTOMER WHERE CUSTOMER_USERNAME = " +
                       username);
       return Integer.parseInt(customer.get(0));
    }

    public boolean login(String username, String password) {
        @SuppressWarnings("unchecked")
        ArrayList<HashMap<String, String>> customer =
                _ec.execute("SELECT * FROM CUSTOMER WHERE " +
                "CUSTOMER_USERNAME = '" + username + "'" +
                " AND CUSTOMER_PASSWORD = '" + password +"'");
        try {
            _currentCustomerId = Integer.parseInt(customer.get(0).get("CUSTOMER_ID"));
        } catch (Exception e) {
            _currentCustomerId = -99;
        }
        return !customer.isEmpty();
    }

    public int getCurrentCustomerId() {
        return _currentCustomerId;
    }

    public boolean register(
            String fname, String lname, String phone, String email, String street,
            String city, String state, int zip, String username, String password) {
        String query =
                "INSERT INTO \"APP\".\"CUSTOMER\" " +
                "(\"CUSTOMER_USERNAME\", \"CUSTOMER_FNAME\", \"CUSTOMER_LNAME\", \"CUSTOMER_PASSWORD\", " +
                        "\"CUSTOMER_EMAIL\", \"CUSTOMER_STREET\", \"CUSTOMER_CITY\", \"CUSTOMER_STATE\", \"CUSTOMER_PHONE\", \"CUSTOMER_ZIP\") " +
                        "VALUES ('"+username+"', '"+fname+"', '"+lname+"', '"+password+"', '"+email+"', '"+street+"', '"+city+"', '"+state+"', '"+phone+"', "+zip+")";
        System.out.println(query);
        if(!_eu.execute(query)) {
            System.out.println("USER ERROR: Could not create user");
            return false;
        } else {
            System.out.println("USER CREATED");
        }
        return true;
    }
}
