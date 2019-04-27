package Controllers;

import Configuration.Config;
import Services.ExecuteSelect;
import java.util.ArrayList;


public class PartController {
    public ArrayList getParts() {
        ArrayList<ArrayList> parts = new ArrayList<>();
        ExecuteSelect select = new ExecuteSelect();
        ArrayList partsQuery = select.execute("SELECT * FROM APP.PART");
        return partsQuery;
    }
}
