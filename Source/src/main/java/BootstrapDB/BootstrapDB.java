package BootstrapDB;


import Services.ExecuteGeneric;
import Services.CreateDB;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;

public class BootstrapDB {
    public void ResetDatabase() {
        //create a new execute generic object
        ExecuteGeneric eg = new ExecuteGeneric();
        String line;
        //create a new database...
        CreateDB createDB = new CreateDB();
        if(createDB.CreateNewDatabase()) {

            //get the text files for creating the tables
            File tablesfolder = new File("./src/main/java/BootstrapDB/DDL");
            File[] fileList = tablesfolder.listFiles();
            ArrayList<String> badQueries = new ArrayList<>();
            //loop over each file to create the tables
            for(int i = 0; i < fileList.length; i++) {
                StringBuilder query = new StringBuilder();
                File f = fileList[i];
                try {
                    BufferedReader bf = new BufferedReader(new FileReader(f));
                    while((line = bf.readLine()) != null) {
                        query.append(line.replace(";", ""));
                    }
                    System.out.println("CREATE TABLE " + query.toString().split("[ \\t]")[2]);
                    //if the table could not be created, save it for later, a foreign key may be referencing a table that doesnt exist
                    if(!eg.execute(query.toString())) {
                        badQueries.add(query.toString());
                    }
                } catch (Exception e) {
                    System.out.println("Unable to read files...");
                }
            }

            //now add the data for each table
            File insertsfolder = new File("./src/main/java/BootstrapDB/INSERT");
            File[] files = insertsfolder.listFiles();
            for(int i = 0; i < files.length; i++) {
                File f = files[i];
                try {
                    BufferedReader bf = new BufferedReader(new FileReader(f));
                    while ((line = bf.readLine()) != null) {
                        StringBuilder query = new StringBuilder();
                        query.append(line.replace(";", ""));
                        System.out.println(query);
                        if(!eg.execute(query.toString())) {
                            badQueries.add(query.toString());
                        }
                    }
                } catch (Exception e) {
                    System.out.println("Error opening file in inserts");
                    System.out.println(e);
                }
            }

            //if there are bad queries, try to run them again
            int reRunCounter = badQueries.size();
            if (reRunCounter > 0) {
                int tries = 0;
                while (!badQueries.isEmpty()) {
                    if(tries > reRunCounter*2) {
                        break;
                    } else {
                        System.out.println("RE-RUNNING: " + badQueries.get(0));
                        //try to execute the query again
                        if(eg.execute(badQueries.get(0))) {
                            badQueries.remove(0);
                        }
                    }
                    tries++;
                }
            }
        } else {
            System.out.println("Error during database drop");
        }
    }
}
