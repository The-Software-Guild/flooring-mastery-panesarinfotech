/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.panesarinfotech.flooringmastery.dao;

import com.panesarinfotech.flooringmastery.dto.Product;
import java.util.List;

/**
 *
 * @author panesar
 */
public interface ProductDao {
    /**
     * 
     * @param key
     * @return returns a products price based off it's name
     * @throws com.panesarinfotech.flooringmastery.dao.FlooringMasteryPersistenceException
     */
    Product getProduct(String key) 
            throws FlooringMasteryPersistenceException;

    
    /**
     *
     * @return
     * @throws com.panesarinfotech.flooringmastery.dao.FlooringMasteryPersistenceException
     */
    List<Product> displayProduct() 
            throws FlooringMasteryPersistenceException;
}
