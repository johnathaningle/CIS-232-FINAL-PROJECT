package Controllers;

import Services.ExecuteSelect;
import jdk.nashorn.internal.runtime.ECMAException;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class PartsController {
    private ExecuteSelect _es;
    private ArrayList<HashMap<String, String>> _partsArray;

    public PartsController() {
        _es = new ExecuteSelect();
        @SuppressWarnings("unchecked")
        ArrayList<HashMap<String, String >> partsQuery = _es.execute("SELECT * FROM APP.PART");
        _partsArray = partsQuery;
    }

    public ArrayList<HashMap<String, String >> getParts() {
        return _partsArray;
    }

    public ArrayList<String> getPartNames() {
        ArrayList<String> parts = new ArrayList<>();
        for (HashMap h : _partsArray) {
            String name = h.get("PART_NAME").toString();
            parts.add(name);
        }
        return parts;
    }

    public Double getPartPrice(int id) {
        Double price = 0.00;
        for (HashMap h : _partsArray) {
            if(Integer.parseInt(h.get("PART_ID").toString()) == id) {
                price = Double.parseDouble(h.get("PART_PRICE").toString());
            }
        }
        return price;
    }

    public Double getPartPrice(String name) {
        Double price = 0.00;
        for (HashMap h : _partsArray) {
            if(h.get("PART_NAME").toString() == name) {
                price = Double.parseDouble(h.get("PART_PRICE").toString());
            }
        }
        return price;
    }
}
