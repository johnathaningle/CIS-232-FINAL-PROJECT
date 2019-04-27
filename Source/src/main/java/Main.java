import BootstrapDB.BootstrapDB;
import Controllers.PartController;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class Main {
    public static void main(String[] args) {
//        BootstrapDB bootstrapDB = new BootstrapDB();
//        bootstrapDB.ResetDatabase();
        PartController partController = new PartController();
        ArrayList<HashMap> parts = partController.getParts();
        for(HashMap part : parts) {
            Iterator it = part.entrySet().iterator();
            while(it.hasNext()) {
                Map.Entry pair = (Map.Entry)it.next();
                System.out.println(pair.getKey().toString() + ": " + pair.getValue().toString());
            }
        }


    }
}
