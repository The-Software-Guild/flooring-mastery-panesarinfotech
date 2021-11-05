/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.panesarinfotech.flooringmastery.dao;

import com.panesarinfotech.flooringmastery.dto.Tax;
import java.util.List;

/**
 *
 * @author panesar
 */
public interface TaxDao {

    /**
     *
     * @param key
     * @return
     * @throws FlooringMasteryPersistenceException
     */
    Tax getTax(String key) 
            throws FlooringMasteryPersistenceException;
    
    /**
     * 
     * @return
     * @throws FlooringMasteryPersistenceException 
     */
    List<Tax> displayTax() throws FlooringMasteryPersistenceException;
}
