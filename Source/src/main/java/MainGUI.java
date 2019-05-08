import Controllers.CustomersController;
import Controllers.OrdersController;
import Controllers.PartsController;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
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

    private void updateScene(Scene scene) {
        this._stage.setScene(scene);
    }

    public void buildGUI() {
        Scene scene = buildLogin();
        _stage.setScene(scene);
    }

    private Scene buildLogin() {
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

    private void buildDashboard() {
        Label title = new Label("Ordering System Dashboard");
        /*
            create the buttons to handle which view is loaded
         */
        Button ordersButton = new Button("Order Admin");
        ordersButton.setOnAction(event -> {
            buildAdminOrders();
        });
        Button partsButton = new Button("Parts Admin");
        partsButton.setOnAction(event -> {
            buildAdminParts();
        });

        Button ordersCreatorButton = new Button("Create an Order");
        ordersCreatorButton.setOnAction(event -> {
            buildOrderCreator();
        });
        HBox navButtonsContainer = new HBox(SPACING, ordersButton, partsButton, ordersCreatorButton);
        VBox mainContainer = new VBox(SPACING, title, navButtonsContainer);
        mainContainer.setPadding(new Insets(SPACING, SPACING, SPACING, SPACING));
        Scene scene = new Scene(mainContainer);
        updateScene(scene);
    }

    private void buildOrderCreator() {
        Label subTotalNum = new Label();
        Label totalNum = new Label();

        ComboBox partTypes = new ComboBox();

        ListView<String> parts = new ListView<>();
        ListView<String> selectedParts = new ListView<>();

        //Order Interface Labels
        Label title = new Label("Customize Your Order");
        Label partLabel = new Label("Choose your parts");
        Label or = new Label("OR");
        Label view = new Label("View: ");
        Label cart = new Label("Shopping Cart");
        Label subTotalLabel = new Label("Subtotal: ");
        Label totalLabel = new Label("Total (+6% tax): ");

        //Order Interface Buttons
        Button viewPresetsButton = new Button("View Presets");
        Button addButton = new Button("Add \u2192");
        Button removeButton = new Button("Remove \u2190");
        Button clearButton = new Button("Clear");
        Button submitOrderButton = new Button("Submit Order");

        //Order Interface formatting
        HBox titleBox = new HBox(title);
        titleBox.setAlignment(Pos.CENTER);


        partTypes.getItems().addAll("All","Keyboards","Mice","Monitors","RAM");
        partTypes.getSelectionModel().selectFirst();
        HBox partOptions = new HBox(view,partTypes);
        partOptions.setSpacing(5);

        GridPane optionGrid = new GridPane();
        optionGrid.add(partLabel,0,0);
        optionGrid.add(or,1,0);
        optionGrid.add(viewPresetsButton,2,0);
        optionGrid.add(partOptions,0,1);
        optionGrid.add(cart,2,1);

        or.setPadding(new Insets(0,50,0,0));

        optionGrid.setAlignment(Pos.CENTER);
        optionGrid.setHgap(150);
        optionGrid.setVgap(10);
        optionGrid.setPadding(new Insets(5));

        VBox buttonBox = new VBox(addButton,removeButton,clearButton);

        HBox partSelectionBox = new HBox(parts,buttonBox,selectedParts);

        partSelectionBox.setAlignment(Pos.CENTER);
        partSelectionBox.setSpacing(10);
        buttonBox.setAlignment(Pos.CENTER);
        buttonBox.setSpacing(30);

        HBox subTotalBox = new HBox(subTotalLabel,subTotalNum);
        HBox totalBox = new HBox(totalLabel,totalNum);

        GridPane orderResults = new GridPane();
        orderResults.add(subTotalBox,0,0);
        orderResults.add(totalBox,0,1);
        orderResults.add(submitOrderButton,1,1);

        subTotalBox.setSpacing(5);
        totalBox.setSpacing(5);

        orderResults.setAlignment(Pos.CENTER);
        orderResults.setHgap(330);
        orderResults.setVgap(10);
        orderResults.setPadding(new Insets(5));

        //Scene buttons
        viewPresetsButton.setOnAction(event -> {
            buildOrderPresets();
        });

        //Set scene and show interface
        VBox masterBox = new VBox(titleBox,optionGrid,partSelectionBox,orderResults);
        Scene scene = new Scene(masterBox);
        updateScene(scene);
    }

    private void buildOrderPresets() {
        ListView<String> selectedPresets = new ListView<>();
        //Preset Interface Labels
        Label presetTitle = new Label("Presets");

        //Preset Interface formatting
        HBox presetTitleBox = new HBox(presetTitle);
        presetTitleBox.setAlignment(Pos.CENTER);
        presetTitleBox.setPadding(new Insets(10));

        Label preset1 = new Label("Preset1"); //replace "preset1 with database output.
        Label preset2 = new Label("Preset2");
        Label preset3 = new Label("Preset3");
        Label presetParts1 = new Label("\u2022 parts from database (1) \n" +
                "\u2022 parts from database (1) \n" +
                "\u2022 parts from database (1)");
        presetParts1.setPadding(new Insets(0,0,5,0));
        Label presetParts2 = new Label("\u2022 parts from database (2) \n" +
                "\u2022 parts from database (2) \n" +
                "\u2022 parts from database (2)");
        presetParts2.setPadding(new Insets(0,0,5,0));
        Label presetParts3 = new Label("\u2022 parts from database (3) \n" +
                "\u2022 parts from database (3) \n" +
                "\u2022 parts from database (3)");
        presetParts3.setPadding(new Insets(0,0,5,0));
        Label presetPrice1 = new Label("[dynamic preset price]");
        Label presetPrice2 = new Label("[dynamic preset price]");
        Label presetPrice3 = new Label("[dynamic preset price]");
        Label presetCart = new Label("Shopping Cart");
        presetCart.setPadding(new Insets(0,0,5,0));

        //Preset Interface Buttons
        Button addToCart1 = new Button("Add to Cart");
        Button addToCart2 = new Button("Add to Cart");
        Button addToCart3 = new Button("Add to Cart");
        Button removeFromCart = new Button("Remove From Cart");
        Button clearCart = new Button("Clear");
        Button backToOrderPage = new Button("Back to Order Page");
        backToOrderPage.setOnAction(event -> {
            buildOrderCreator();
        });
        HBox preset1Result = new HBox(presetPrice1,addToCart1);
        preset1Result.setSpacing(5);
        HBox preset2Result = new HBox(presetPrice2,addToCart2);
        preset2Result.setSpacing(5);
        HBox preset3Result = new HBox(presetPrice3,addToCart3);
        preset3Result.setSpacing(5);
        VBox presetBox1 = new VBox(preset1,presetParts1,preset1Result);
        preset1Result.setAlignment(Pos.CENTER);
        VBox presetBox2 = new VBox(preset2,presetParts2,preset2Result);
        preset2Result.setAlignment(Pos.CENTER);
        VBox presetBox3 = new VBox(preset3,presetParts3,preset3Result);
        preset3Result.setAlignment(Pos.CENTER);
        HBox presetDisplay = new HBox(presetBox1,presetBox2,presetBox3);
        presetDisplay.setSpacing(20);
        presetDisplay.setAlignment(Pos.CENTER);
        presetDisplay.setPadding(new Insets(10));

        VBox presetItemBox = new VBox(presetCart,selectedPresets);
        selectedPresets.setPrefWidth(300);
        selectedPresets.setPrefHeight(100);
        presetItemBox.setPadding(new Insets(5));

        HBox presetButtonBox = new HBox(removeFromCart,clearCart);
        presetButtonBox.setAlignment(Pos.CENTER);
        presetButtonBox.setSpacing(20);

        VBox backBox = new VBox(backToOrderPage);
        backBox.setAlignment(Pos.CENTER_RIGHT);
        VBox masterPresetBox = new VBox(presetTitleBox,presetDisplay,presetItemBox,presetButtonBox,backBox);
        Scene presetScene = new Scene(masterPresetBox);
        updateScene(presetScene);
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
