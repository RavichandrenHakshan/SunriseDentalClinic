package com.sunriseclinic.backend;

import org.junit.Test;
import static org.junit.Assert.*;
import java.sql.Connection;

public class DBConnectionTest {

    @Test
    public void testDatabaseConnection() {
        try {
           
            Connection con = util.DBconnection.getConnection();
            
            assertNotNull("The database connection failed. Please ensure XAMPP/MySQL is running.", con);
            
            assertTrue("The database connection should be open and active.", !con.isClosed());

            if (con != null) {
                con.close();
            }
            
        } catch (Exception e) {
            fail("Database connection threw an exception: " + e.getMessage());
        }
    }
}