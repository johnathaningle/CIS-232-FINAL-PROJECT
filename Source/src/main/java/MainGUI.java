import Controllers.PartsController;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.util.ArrayList;

public class MainGUI {
    private final int SPACING = 25;
    final int LV_WIDTH = 150;
    final int LV_HEIGHT = 75;
    private boolean _loggedin;
    private Stage _stage;
    private PartsController _pc;
    public MainGUI(Stage stage) {
        _loggedin = false;
        _stage = stage;
        _pc = new PartsController();
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
            buildDashboard();
        });
        VBox mainContainer = new VBox(SPACING, title, username, password, loginButton);
        mainContainer.setPadding(new Insets(SPACING, SPACING, SPACING, SPACING));
        return new Scene(mainContainer);
    }

    public void buildDashboard() {
        Label title = new Label("Ordering System Dashboard");
        ListView<String> partsListView = new ListView<>();
        ArrayList<String> partNames = _pc.getPartNames();
        partsListView.getItems().addAll(partNames);
        partsListView.setPrefSize(LV_HEIGHT, LV_WIDTH);
        Button button = new Button("Click me");
        VBox mainContainer = new VBox(SPACING, title, partsListView, button);
        mainContainer.setPadding(new Insets(SPACING, SPACING, SPACING, SPACING));
        Scene scene = new Scene(mainContainer);
        updateScene(scene);
    }
}
