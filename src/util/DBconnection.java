package util;

import java.sql.Connection;
import java.sql.DriverManager;

public class DBconnection {
    
    public static Connection getConnection(){
        Connection con = null;
        try {
            // This matches the 8.x driver syntax your lecturer used
            Class.forName("com.mysql.cj.jdbc.Driver");

            // Connecting to the WAMP database we discussed
            con = DriverManager.getConnection(
            "jdbc:mysql://localhost:3306/sunrise_dental_db",
            "root",
            "");
            System.out.println("Database Connected");
        }
        catch(Exception e){
            System.out.println(e);
        }
        return con;
    }
}