/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.panesarinfotech.flooringmastery.dao;

import com.panesarinfotech.flooringmastery.dto.Tax;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 *
 * @author panesar
 */
public class TaxDaoImplTest {
    TaxDao testDao;
    
    public TaxDaoImplTest() {
    }
    
    @BeforeEach
    public void setUp() throws IOException{
        String testFile = "testTax.txt";
        testDao = new TaxDaoImpl(testFile);
    }
    

    @Test
    public void testGetTaxInfo() throws FlooringMasteryPersistenceException {
        String stateName = "MN";
        Tax t = testDao.getTax(stateName);
        assertEquals("MN", t.getStateAb(), "Return should be MN");
        assertEquals(new BigDecimal("3.09"), t.getTaxRate(),"Tax rate should be 3.09");
        
        String state = "ON";
        Tax tax = testDao.getTax(state);
        assertEquals("ON", tax.getStateAb(), "Return should be ON");
        assertEquals(new BigDecimal("7.51"),tax.getTaxRate(), "Tax rate should be 7.51");
    }
    
    @Test
    public void testDisplayTax() throws FlooringMasteryPersistenceException {
        List<Tax> taxList = testDao.displayTax();
        assertNotNull(taxList, "Should not be null");
        assertEquals(6, taxList.size(),"List size should be 6");
    }
}
