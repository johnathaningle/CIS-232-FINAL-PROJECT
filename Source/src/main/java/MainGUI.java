import Controllers.CustomersController;
import Controllers.OrdersController;
import Controllers.PartsController;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import javax.swing.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Set;

public class MainGUI {
    private final int SPACING = 25;
    final int LV_WIDTH = 150;
    final int LV_HEIGHT = 75;
    private boolean _loggedin;
    private Stage _stage;
    private PartsController _pc;
    private OrdersController _oc;
    private CustomersController _cc;
    public MainGUI() {
        _loggedin = false;
        _pc = new PartsController();
        _oc = new OrdersController();
        _cc = new CustomersController();
    }

    public void SetMainGuiStage(Stage stage) {
        _stage = stage;
    }

    public void updateScene(Scene scene) {
        this._stage.setScene(scene);
    }

    public void buildGUI() {
        Scene scene = buildLogin();
        _stage.setScene(scene);
    }

    public Scene buildLogin() {
        Label title = new Label("Login to Computer Ordering System");
        TextField username = new TextField("Username");
        PasswordField password = new PasswordField();
        Button loginButton = new Button("Login");
        loginButton.setOnAction(event -> {
            String pw = password.getText();
            String un = username.getText();
            if(_cc.login(un, pw)) {
                JOptionPane.showMessageDialog(null,"You are now logged in");
                buildDashboard();
            } else {
                JOptionPane.showMessageDialog(null,"Incorrect Login Credentials");
            }


        });
        VBox mainContainer = new VBox(SPACING, title, username, password, loginButton);
        mainContainer.setPadding(new Insets(SPACING, SPACING, SPACING, SPACING));
        return new Scene(mainContainer);
    }

    public void buildDashboard() {
        Label title = new Label("Ordering System Dashboard");
        /*
            create the buttons to handle which view is loaded
         */
        Button ordersButton = new Button("Orders");
        ordersButton.setOnAction(event -> {
            buildAdminOrders();
        });
        Button partsButton = new Button("Parts");
        partsButton.setOnAction(event -> {
            buildAdminParts();
        });
        HBox navButtonsContainer = new HBox(SPACING, ordersButton, partsButton);
        VBox mainContainer = new VBox(SPACING, title, navButtonsContainer);
        mainContainer.setPadding(new Insets(SPACING, SPACING, SPACING, SPACING));
        Scene scene = new Scene(mainContainer);
        updateScene(scene);
    }

    public void buildAdminOrders() {
        Label title = new Label("DB Admin");
        /*
            create the gui components for displaying the order data
         */
        Label orderPartsListViewLabel = new Label("Order Details");
        ListView<String> orderPartsListView = new ListView<>();
        VBox orderPartsContainer = new VBox(SPACING, orderPartsListViewLabel, orderPartsListView);

        /*
            create the gui components for displaying the id for each order
         */
        Label orderListViewLabel = new Label("Orders");
        //this will display all open customer orders
        ListView<String> orderListView = new ListView<>();
        //this will get the hashmap of all cusomer orders
        HashMap<Integer, ArrayList<ArrayList>> orderQuery = _oc.getAllCustomerOrders();
        System.out.println(orderQuery);
        //get the order id's for the orderlistview
        Set<Integer> orderIds = orderQuery.keySet();
        //create a list of orderId's when clicked they will display the order info
        for(Integer i : orderIds) {
            orderListView.getItems().addAll("Order Number: " + i.toString());
        }
        VBox orderListViewContainer = new VBox(SPACING, orderListViewLabel, orderListView);
        //when a user clicks on an order, load the data into the partslistview
        orderListView.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                //get the id for the selected order
                Integer selectedOrderId = Integer.parseInt(newValue.replace("Order Number: ", ""));
                //get the parts for the selected order
                ArrayList<ArrayList> newParts = orderQuery.get(selectedOrderId);
                //add all the details to the order parts list so that users can view them
                //clear the existing parts from the list
                orderPartsListView.getItems().clear();
                for(ArrayList a: newParts) {
                    String part = a.toString().replace("[", "").replace("]", "");
                    orderPartsListView.getItems().addAll(part);
                }
            }
        });


        /*
            compose the gui components and update the scene.
        */

        //create a button to navigate back to dashboard
        Button dashButton = new Button("Back to Dashboard");
        dashButton.setOnAction(event -> {
            buildDashboard();
        });
        HBox ordersMainContainer = new HBox(SPACING, orderListViewContainer, orderPartsContainer);
        VBox mainContainer = new VBox(SPACING, title, ordersMainContainer, dashButton);
        mainContainer.setPadding(new Insets(SPACING, SPACING, SPACING, SPACING));
        Scene scene = new Scene(mainContainer);
        updateScene(scene);
    }

    private void buildAdminParts() {
        Label title = new Label("Parts");

        /*
            Create the gui component that will display details for a given part
         */
        Label partsDetailLabel = new Label("Part Details");
        ListView<String> partsDetailListView = new ListView<>();

        /*
            Create the gui that will display the list of parts
            when selected it will update the partsDetail list view
         */
        Label partsLabel = new Label("Parts List");
        ListView<String> partsListView = new ListView<>();
        ArrayList<String> partNames = _pc.getPartNames();
        partsListView.getItems().addAll(partNames);
        VBox partsListViewContainer = new VBox(partsLabel, partsListView);
        HBox partsMainContainer = new HBox(SPACING, partsListViewContainer);

        //create a button to navigate back to dashboard
        Button dashButton = new Button("Back to Dashboard");
        dashButton.setOnAction(event -> {
            buildDashboard();
        });
        VBox mainContainer = new VBox(SPACING, title, partsMainContainer, dashButton);
        mainContainer.setPadding(new Insets(SPACING, SPACING, SPACING, SPACING));
        Scene scene = new Scene(mainContainer);
        updateScene(scene);
    }

}
