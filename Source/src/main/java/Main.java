import BootstrapDB.BootstrapDB;
import javafx.application.Application;
import javafx.stage.Stage;

public class Main extends Application {
    @Override
    public void start(Stage primaryStage) {
        MainGUI gui = new MainGUI(primaryStage);
        gui.buildGUI();
        primaryStage.show();
    }
    public static void main(String[] args) {
        BootstrapDB bootstrapDB = new BootstrapDB();
        bootstrapDB.ResetDatabase();
        launch(args);

    }
}
