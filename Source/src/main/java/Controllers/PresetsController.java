package Controllers;

import Services.ExecuteSelect;

import java.util.ArrayList;
import java.util.HashMap;

public class PresetsController {
    private ExecuteSelect _ec;
    private ArrayList<HashMap<String, String >> _presetsQuery;
    public PresetsController() {
        _ec = new ExecuteSelect();
        @SuppressWarnings("unchecked")
        ArrayList<HashMap<String, String>> presets = _ec.execute("SELECT * FROM PRESET");
        _presetsQuery = presets;
    }

    public ArrayList<HashMap<String, String>> getPresets() {
        return _presetsQuery;
    }

    public void getPresetParts(String name) {

    }



}
