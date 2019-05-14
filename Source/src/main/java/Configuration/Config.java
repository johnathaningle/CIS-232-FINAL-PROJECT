package Configuration;

public class Config {
    public final String DB_NAME = "ComputerDB";
    public final String DB_URL = "jdbc:derby:"+DB_NAME;
    public final String SCHEMA_NAME = "APP";
    public final String CREATE_URL = "jdbc:derby:"+DB_NAME+";create=true";
    public final double TAXRATE = 0.06;

}
