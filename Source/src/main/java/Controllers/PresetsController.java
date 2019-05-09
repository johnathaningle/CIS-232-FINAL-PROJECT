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

    public ArrayList<String> getPresetParts(int presetId) {
        String query = "SELECT PART.PART_ID, PART.PART_NAME\n" +
                "FROM PRESET\n" +
                "JOIN PRESET_PART USING(PRESET_ID)\n" +
                "JOIN PART ON PART.PART_ID = PRESET_PART.PART_ID\n" +
                "WHERE PRESET.PRESET_ID = " + presetId;
        @SuppressWarnings("unchecked")
        ArrayList<HashMap<String, String>> presetsQuery  = _ec.execute(query);
        ArrayList<String> partNames = new ArrayList<>();
        for(HashMap h : presetsQuery) {
            String partId = h.get("PART_ID").toString();
            String partName = h.get("PART_NAME").toString();
            partNames.add(partId + ": "+ partName);
        }
        System.out.println("SUCCESS: " + query);
        return partNames;
    }



}
