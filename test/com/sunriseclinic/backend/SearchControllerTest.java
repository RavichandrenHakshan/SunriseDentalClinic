package com.sunriseclinic.backend;

import controller.SearchController;
import org.junit.Test;
import static org.junit.Assert.*;
import javax.swing.table.DefaultTableModel;

public class SearchControllerTest {

    @Test
    public void testValidSearchReturnsResults() {
        SearchController searchCtrl = new SearchController();
        
        // "Sil" should match "Amila Silva" from the 50 test records we inserted
        DefaultTableModel resultModel = searchCtrl.getAppointmentTable("Sil");
        
        // Assert that the table successfully retrieves at least 1 row of data
        assertTrue("Search for 'Sil' should return matching records.", resultModel.getRowCount() > 0);
    }

    @Test
    public void testInvalidSearchReturnsEmpty() {
        SearchController searchCtrl = new SearchController();
        
        // A random string that does not exist in the database
        DefaultTableModel resultModel = searchCtrl.getAppointmentTable("GhostPatient999");
        
        // Assert that the table correctly returns exactly 0 rows without crashing
        assertEquals("Search for non-existent data should return an empty table.", 0, resultModel.getRowCount());
    }
    
    @Test
    public void testEmptySearchReturnsAll() {
        SearchController searchCtrl = new SearchController();
        
        // An empty search string should act as a wildcard and return all records
        DefaultTableModel resultModel = searchCtrl.getAppointmentTable("");
        
        // Assert that the table returns multiple rows (validating the 50 records)
        assertTrue("An empty search should return the full database list.", resultModel.getRowCount() > 10);
    }
}