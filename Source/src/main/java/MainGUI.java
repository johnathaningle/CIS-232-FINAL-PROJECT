import Controllers.CustomersController;
import Controllers.OrdersController;
import Controllers.PartsController;
import Controllers.PresetsController;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
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
    private PartsController _partsController;
    private OrdersController _ordersController;
    private CustomersController _customersController;
    private PresetsController _presetsController;

    //values for keeping track of the user
    private String currentUsername;
    private int currentUserId;
    private int currentOrderId;


    public MainGUI() {
        _loggedin = false;
        _partsController = new PartsController();
        _ordersController = new OrdersController();
        _customersController = new CustomersController();
        _presetsController = new PresetsController();
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
        Label title = new Label("BestPrice");
        Label title2 = new Label("Please login to order your computer parts\nor review your pevious orders.");
        Label usernameLabel = new Label("Username");
        Label passwordLabel = new Label("Password");
        Label errorLabel = new Label(" ");
        errorLabel.setId("errorLabel");

        Image logo = new Image("file:/main/java/bestPriceLogo.png");
        ImageView logoView = new ImageView(logo);
        logoView.setFitHeight(100);
        logoView.setFitWidth(100);

        TextField usernameTF = new TextField();
        PasswordField passwordTF = new PasswordField();

        Button loginButton = new Button("Login");
        loginButton.setOnAction(event -> {
            String pw = passwordTF.getText();
            String un = usernameTF.getText();
            if(_customersController.login(un, pw)) {
                currentUserId = _customersController.getCurrentCustomerId();
                buildDashboard();
            }
        });
        Button signUpButton = new Button("Create An Account");

        signUpButton.setOnAction(event ->{
            buildAccount();
        });

        //display
        title.setStyle("-fx-font-size: 25pt");
        HBox titleH = new HBox(SPACING,logoView,title);
        titleH.setAlignment(Pos.CENTER);
        VBox titleV = new VBox(titleH,title2);
        VBox usernameV = new VBox(usernameLabel,usernameTF);
        VBox passwordV = new VBox(passwordLabel,passwordTF);
        HBox buttonH = new HBox(SPACING,loginButton,signUpButton);
        buttonH.setAlignment(Pos.CENTER_RIGHT);
        VBox mainContainer = new VBox(SPACING, titleV, errorLabel, usernameV, passwordV, buttonH);
        mainContainer.setPadding(new Insets(SPACING, SPACING, SPACING, SPACING));

        Scene scene =  new Scene(mainContainer);
        scene.getStylesheets().add("file:../src/main/java/login.css");
        return scene;
    }

    public void buildAccount(){
        //Labels
        Label title = new Label("Creating An Account");
        Label infoLabel = new Label("Please fill out the fields below: ");
        Label contactInfo = new Label("Contact Information");
        Label firstNameLabel = new Label("First Name: ");
        Label lastNameLabel = new Label("Last Name: ");
        Label phoneLabel = new Label("Phone Number: ");
        Label emailLabel = new Label("Email: ");
        Label address = new Label("Address ");
        Label streetLabel = new Label("Street: ");
        Label cityLabel = new Label("City: ");
        Label stateLabel = new Label("State: ");
        Label zipCodeLabel = new Label("Zip code: ");
        Label loginInfo = new Label("Login Information");
        Label usernameLabel = new Label("Username: ");
        Label passwordLabel = new Label("Password: ");
        Label passwordInfo = new Label("* Password must contain at least 8 characters, " +
                "1 number, and 1 special character.");
        Label confirmPasswordLabel = new Label("Confirm Password: ");
        Label confirmMsg = new Label(" ");

        //change the style of the titles
        title.setStyle("-fx-font-size: 20pt; -fx-font-weight: bold");
        contactInfo.setId("title");
        address.setId("title");
        loginInfo.setId("title");

        //textFields
        TextField firstNameTF = new TextField();
        TextField lastNameTF = new TextField();
        TextField phoneTF = new TextField();
        TextField emailTF = new TextField();
        TextField streetTF = new TextField();
        TextField cityTF = new TextField();
        TextField zipCodeTF = new TextField();
        TextField usernameTF = new TextField();
        PasswordField passwordTF = new PasswordField();
        PasswordField confirmTF = new PasswordField();

        //set textFields' size
        emailTF.setPrefSize(300,10);
        streetTF.setPrefSize(250,10);

        //create the state options
        ArrayList<String> state = new ArrayList<String>();
        String[] s = {"Alabama" ,"Alaska", "Arizona","Arkansas", "California", "Colorado", "Connecticut", "Delaware",
                "Florida", "Georgia", "Hawaii", "Idaho", "Illinois", "Indiana", "Iowa", "Kansas", "Kentucky",
                "Louisiana", "Maine", "Maryland", "Massachusetts", "Michigan", "Minnesota","Mississippi","Missouri",
                "Montana","Nebraska","Nevada","New Hampshire","New Jersey","New Mexico","New York","North Carolina",
                "North Dakota","Ohio","Oklahoma","Oregon","Pennsylvania","Rhode Island","South Carolina","South Dakota",
                "Tennessee", "Texas", "Utah", "Vermont", "Virginia","Washington","West Virginia","Wisconsin","Wyoming"};
        for(int i = 0;i<s.length;i++){
            state.add(s[i]);
        }
        ObservableList stateOL = FXCollections.observableArrayList(state);
        ComboBox stateCB = new ComboBox(stateOL);

        //create an image
        Image logo = new Image("file:/bestPriceLogo.png");
        ImageView logoView = new ImageView(logo);
        logoView.setFitHeight(80);
        logoView.setFitWidth(80);

        //create a button for finishing creating an account
        Button backToLogin = new Button("Back to login");
        backToLogin.setOnAction(event -> {
            updateScene(buildLogin());
        });

        Button creatA = new Button("Create Account");
        //set on action
        creatA.setOnAction(event ->{
            System.out.println("Creating Account...");
            String firstName = firstNameTF.getText();
            String lastName = lastNameTF.getText();
            String phoneNumber = phoneTF.getText();
            String email = emailTF.getText();
            String street = streetTF.getText();
            String city = cityTF.getText();
            String stateStr = stateCB.getSelectionModel().getSelectedItem().toString();
            String zipCode = zipCodeTF.getText();
            String newUsername = usernameTF.getText();
            String newPassword = passwordTF.getText();

            confirmMsg.setText(" ");
            passwordInfo.setId(" ");
            if(newPassword.length()<8){
                passwordInfo.setId("errorLabel");
            }
            else{
                boolean specialChar = false;
                boolean number = false;

                for(int j=0;j < newPassword.length();j++){
                    char c = newPassword.charAt(j);

                    if(Character.isDigit(c)){
                        number = true;
                    };

                    if(!Character.isLetter(c)){
                        specialChar = true;
                    }
                }

                if(!specialChar||!number){
                    passwordInfo.setId("errorLabel");
                }
            }
            int zip;
            if(!newPassword.equals(confirmTF.getText())){
                confirmMsg.setText("Passwords do not match.");
                confirmMsg.setId("errorLabel");
            }
            try {
                zip = Integer.parseInt(zipCode);
            } catch (Exception e) {
                System.out.println("Cannot parse zip code");
                zip = 12345;
            }

            //make sure that everything went smoothly with the registration
            if(!_customersController.register
                    (firstName, lastName, phoneNumber, email, street, city, stateStr, zip
                            , newUsername, newPassword)) {
                confirmMsg.setText("Error During Registration");
                confirmMsg.setId("errorLabel");
            } else {
                System.out.println("Successfully Created User");
                JOptionPane.showMessageDialog(null,"Account Created");
                updateScene(buildLogin());
            }

        });

        HBox row1 = new HBox(SPACING+10,logoView,title);
        row1.setAlignment(Pos.CENTER_LEFT);
        HBox fnameRow = new HBox(firstNameLabel, firstNameTF);
        HBox lnameRow = new HBox(lastNameLabel,lastNameTF);
        HBox nameRow = new HBox(SPACING+10,fnameRow,lnameRow);
        HBox emailRow = new HBox(emailLabel,emailTF);
        HBox phoneRow = new HBox(phoneLabel,phoneTF);
        HBox streetRow = new HBox(streetLabel,streetTF);
        HBox cityRow = new HBox(cityLabel,cityTF);
        HBox addressRow = new HBox(SPACING+10,streetRow,cityRow);
        HBox zipRow = new HBox(zipCodeLabel,zipCodeTF);
        HBox stateRow = new HBox(stateLabel,stateCB);
        HBox addressRow2 = new HBox(SPACING+10,stateRow,zipRow);
        HBox usernameRow = new HBox(usernameLabel,usernameTF);
        HBox passwordRow = new HBox(passwordLabel,passwordTF);
        HBox confirmRow = new HBox(confirmPasswordLabel,confirmTF);

        VBox main = new VBox(SPACING,row1,infoLabel,contactInfo,nameRow,phoneRow,emailRow,address,addressRow,addressRow2,
                loginInfo,usernameRow,passwordRow,passwordInfo,confirmRow,confirmMsg,creatA, backToLogin);
        main.setPadding(new Insets(SPACING, SPACING, SPACING, SPACING));

        Scene account = new Scene(main);
        account.getStylesheets().add("file:/login.css");
        updateScene(account);
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
        //Preset Interface Labels
        Label presetTitle = new Label("Presets");

        //Preset Interface formatting
        HBox presetTitleBox = new HBox(presetTitle);
        presetTitleBox.setAlignment(Pos.CENTER);
        presetTitleBox.setPadding(new Insets(SPACING));

        //listview for the list of presets
        ListView<String> presetListView = new ListView<>();
        //listview for the list of parts in each preset
        ListView<String> presetPartsListView = new ListView<>();
        presetListView.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                int presetId = Integer.parseInt(newValue.split(":")[0]);
                presetPartsListView.getItems().clear();
                ArrayList<String> partNames = _presetsController.getPresetParts(presetId);
                presetPartsListView.getItems().addAll(partNames);
            }
        });
        //get preset names
        ArrayList<String> presetNames = new ArrayList<>();
        ArrayList<HashMap<String, String>> presets = _presetsController.getPresets();
        for (HashMap h : presets) {
            String presetId = h.get("PRESET_ID").toString();
            String presetName = h.get("PRESET_NAME").toString();
            presetNames.add(presetId+ ": " + presetName);
        }
        presetListView.getItems().addAll(presetNames);

        //create the listview to display the list of parts in each preset

        HBox presetsContainer = new HBox(SPACING, presetListView, presetPartsListView);

        Button addAllToCart = new Button("Add All to Cart");
        addAllToCart.setOnAction(event -> {
            Object[] partsList = presetPartsListView.getItems().toArray();
            for(Object o : partsList) {

            }
        });

        Button removeFromCart = new Button("Remove From Cart");
        Button clearCart = new Button("Clear");
        Button backToOrderPage = new Button("Back to Order Page");
        backToOrderPage.setOnAction(event -> {
            buildOrderCreator();
        });

        HBox presetButtonBox = new HBox(removeFromCart,clearCart);
        presetButtonBox.setAlignment(Pos.CENTER);
        presetButtonBox.setSpacing(20);

        VBox backBox = new VBox(backToOrderPage);
        backBox.setAlignment(Pos.CENTER_RIGHT);
        VBox masterPresetBox = new VBox(presetTitleBox,presetsContainer, presetButtonBox,backBox);
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
        HashMap<Integer, ArrayList<ArrayList>> orderQuery = _ordersController.getAllCustomerOrders();
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
        ArrayList<String> partNames = _partsController.getPartNames();
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
