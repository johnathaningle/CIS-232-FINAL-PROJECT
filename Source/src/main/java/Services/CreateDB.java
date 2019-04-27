package Services;

import Configuration.Config;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.sql.*;
import java.util.Comparator;

public class CreateDB {
    private Config config = new Config();

    public void dropDB() {
        try {
            System.out.println("Locating existing db...");
            File folder = new File(".");
            File[] fileList = folder.listFiles();
            for (File f : fileList) {
                if(f.getName().equals(config.DB_NAME)) {
                    System.out.println("Begin existing database delete");
                    Files.walk(f.toPath())
                            .sorted(Comparator.reverseOrder())
                            .map(Path::toFile)
                            .forEach(File::delete);

                }
            }
        } catch (Exception e) {
            System.out.println("ERROR HANDING FILES:");
            System.out.println(e);
        }
    }

    public boolean CreateNewDatabase() {
        boolean success = false;
        //make sure to create the driver for the database
        try {
            DriverManager.registerDriver(new org.apache.derby.jdbc.EmbeddedDriver());
        } catch (Exception e) {
            System.out.println(e);
        }
        //drop the existing database
        dropDB();

        //now create a new one...
        try{
            Connection con = DriverManager.getConnection(config.CREATE_URL);
            con.close();
            success = true;
        } catch (Exception e) {
            System.out.println("Error during create connection...");
            System.out.println(e);
        }
        return success;
    }
}
