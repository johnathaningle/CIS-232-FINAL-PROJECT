import BootstrapDB.BootstrapDB;
import javafx.application.Application;
import javafx.stage.Stage;

/**
 * The sole function of this class is to execute the main GUI.
 */
public class Main extends Application {
    //Creates an instance of the GUI and executes it
    @Override
    public void start(Stage primaryStage) {
        MainGUI gui = new MainGUI();
        gui.SetMainGuiStage(primaryStage);
        gui.buildGUI();
        primaryStage.show();
    }
    public static void main(String[] args) {
        BootstrapDB bootstrapDB = new BootstrapDB();
        bootstrapDB.ResetDatabase();
        launch(args);

    }
}
