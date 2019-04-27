package Configuration;

public class Config {
    public final String DB_URL = "jdbc:derby:TestDB";
    public final String SCHEMA_NAME = "APP";
    public final String DB_NAME = "TestDB";
    public final String CREATE_URL = "jdbc:derby:"+DB_NAME+";create=true";

}
