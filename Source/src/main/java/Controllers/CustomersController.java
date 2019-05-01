package Controllers;

import Services.ExecuteSelect;

import java.util.ArrayList;

public class CustomersController {
    private ExecuteSelect _ec;

    public CustomersController() {
        _ec = new ExecuteSelect();
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
        ArrayList<String> customer =
                _ec.execute("SELECT * FROM CUSTOMER WHERE " +
                "CUSTOMER_USERNAME = '" + username + "'" +
                " AND CUSTOMER_PASSWORD = '" + password +"'");
        return !customer.isEmpty();
    }
}
