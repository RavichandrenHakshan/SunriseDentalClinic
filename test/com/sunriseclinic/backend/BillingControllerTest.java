package com.sunriseclinic.backend;

import controller.BillingController;
import org.junit.Test;
import static org.junit.Assert.*;

public class BillingControllerTest {

    @Test
    public void testValidInvoiceGeneration() {
        BillingController billCtrl = new BillingController();
        
        // Pass a known valid ID (ID 1 exists from the 50 test records)
        String receipt = billCtrl.generateReceipt("1");
        
        // Assert that a string is returned and contains basic invoice data
        assertNotNull("The generated receipt should not be null.", receipt);
        assertTrue("The receipt should contain data and not be empty.", receipt.length() > 0);
    }

    @Test
    public void testInvalidIdHandling() {
        BillingController billCtrl = new BillingController();
        
        // Pass an ID that definitely does not exist in the database
        String receipt = billCtrl.generateReceipt("99999");
        
        // Assert that the system handles it gracefully (returns empty or an error message)
        assertTrue("Invalid ID should return an error message or empty string.", 
                   receipt.isEmpty() || receipt.toLowerCase().contains("error") || receipt.toLowerCase().contains("not found"));
    }

    @Test
    public void testEmptyIdHandling() {
        BillingController billCtrl = new BillingController();
        
        // Pass an empty string to simulate the user clicking Generate without typing anything
        String receipt = billCtrl.generateReceipt("");
        
        // Assert the controller intercepts the empty input
        assertTrue("Empty ID should be blocked or return an error.", 
                   receipt.isEmpty() || receipt.toLowerCase().contains("error") || receipt.toLowerCase().contains("invalid"));
    }
}