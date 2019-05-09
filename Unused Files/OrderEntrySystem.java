import javafx.application.Application;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.scene.layout.HBox;
import javafx.scene.layout.BorderPane;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import java.sql.SQLException;
import javafx.event.EventHandler;
import javafx.event.ActionEvent;

public class OrderEntrySystem extends Application
{
   // Fields for input
   private ListView<String> customerListView;
   private ListView<String> ComputerListView;
   private TextField qtyTextField;
   private TextField orderDateTextField;
   
   // Constants
   final int LV_WIDTH = 150;
   final int LV_HEIGHT = 75;
   final double SPACING = 10.0;
   
   private void buildGUI(Stage stage)
   {
      // Build the customer selection area.
      Label customerPrompt = new Label("Select a Customer");
      customerListView = new ListView<String>();
      customerListView.setPrefSize(LV_WIDTH, LV_HEIGHT);
      VBox customerVBox = new VBox(SPACING, customerPrompt,
                                   customerListView);
      customerVBox.setAlignment(Pos.CENTER);
      customerVBox.setPadding(new Insets(SPACING));
      
      // Build the Computer selection area
      Label ComputerPrompt = new Label("Select a Computer");
      ComputerListView = new ListView<String>();
      ComputerListView.setPrefSize(LV_WIDTH, LV_HEIGHT);
      VBox ComputerVBox = new VBox(10, ComputerPrompt,
                                 ComputerListView);
      ComputerVBox.setAlignment(Pos.CENTER);
      ComputerVBox.setPadding(new Insets(SPACING));
      
      // Build the qunatity and date entry areas.
      Label qtyPrompt = new Label("Quantity");
      qtyTextField = new TextField();
      
      Label datePrompt = new Label("Order Date");
      orderDateTextField = new TextField();
      VBox orderVBox = new VBox(SPACING, qtyPrompt, qtyTextField,
                                datePrompt, orderDateTextField);
      orderVBox.setAlignment(Pos.CENTER);
      orderVBox.setPadding(new Insets(SPACING));
      
      // Build the button area.
      Button submitButton = new Button("Submit");
      Button exitButton = new Button("Exit");
      HBox buttonHBox = new HBox(SPACING, submitButton, exitButton);
      buttonHBox.setAlignment(Pos.CENTER);
      buttonHBox.setPadding(new Insets(SPACING));
      
      // Register event handlers for the Button controls.
      submitButton.setOnAction(new SubmitButtonHandler());
      exitButton.setOnAction(e ->
      {
         // Close the stage, end the application.
         stage.close();
      });
      
      // Put everything inside a BorderPane.
      BorderPane borderPane = new BorderPane();
      borderPane.setLeft(customerVBox);
      borderPane.setCenter(ComputerVBox);
      borderPane.setRight(orderVBox);
      borderPane.setBottom(buttonHBox);
      
      // Set the title bar text.
      stage.setTitle("Order Entry System");
      
      // Create a scene and add it to the stage
      Scene scene = new Scene(borderPane);
      stage.setScene(scene);
   }
   
   /**
    * The loadData method loads customer names and
    * Computer names into the ListView controls.
    */
    
   private void loadData()
   {
      try
      {
         // Load customer names into the ListView.
         customerListView.getItems().setAll(
                 ComputerDBManager.getCustomerNames());
         
         // Load Computer names into the ListView.
         ComputerListView.getItems().setAll(
                 ComputerDBManager.getComputerNames());
      }
      catch (SQLException e)
      {
         e.printStackTrace();
         System.exit(0);
      }
   }
   
   public static void main(String[] args)
   {
      // Launch the application
      launch(args);
   }
   
   @Override
   public void start(Stage primaryStage)
   {
      // Build the GUI.
      buildGUI(primaryStage);
         
      // Load data from the database.
      loadData();

      // Show the stage.
      primaryStage.show();
   }
   
   /**
    * Event handler class for submitButton
    */
 
   class SubmitButtonHandler implements EventHandler<ActionEvent>
   {
      @Override
      public void handle(ActionEvent event)
      {
         try
         {
            // Get the customer name from the ListView
            String customerName =
               customerListView.getSelectionModel().getSelectedItem();
            
            // Get the Computer name from the ListView
            String ComputerName =
               ComputerListView.getSelectionModel().getSelectedItem();
            
            // Get the quantity
            int qty = Integer.parseInt(qtyTextField.getText());
            
            // Get the order date
            String orderDate = orderDateTextField.getText();
            
            // Get the customer number from the DB
            String customerNum =
               ComputerDBManager.getCustomerNum(customerName);
            
            // Get the Computer product number from the DB
            String prodNum = ComputerDBManager.getProdNum(ComputerName);
            
            // Get the price per pound from the DB
            double price = ComputerDBManager.getComputerPrice(prodNum);
            
            // Submit the order to the DB
            ComputerDBManager.submitOrder(customerNum, prodNum, qty,
                                        price, orderDate);
                                  
            // Clear the text fields
            qtyTextField.clear();
            orderDateTextField.clear();
         }
         catch (SQLException e)
         {
            e.printStackTrace();
            System.exit(0);
         }
      }
   }
}