package BootstrapDB;


import Services.ExecuteGeneric;
import Services.CreateDB;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;

public class BootstrapDB {
    public void ResetDatabase() {
        String line;
        //create a new database...
        CreateDB createDB = new CreateDB();
        if(createDB.CreateNewDatabase()) {

            //create a new execute generic object
            ExecuteGeneric eg = new ExecuteGeneric();

            //get the text files for creating the tables
            File tablesfolder = new File("./src/main/java/BootstrapDB/DDL");
            File[] fileList = tablesfolder.listFiles();

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
                    eg.execute(query.toString());
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
                        eg.execute(query.toString());
                    }
                } catch (Exception e) {
                    System.out.println("Error opening file in inserts");
                    System.out.println(e);
                }
            }
        } else {
            System.out.println("Error during database drop");
        }
    }
}
