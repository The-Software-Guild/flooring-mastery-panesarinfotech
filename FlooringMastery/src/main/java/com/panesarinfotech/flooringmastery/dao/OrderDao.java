/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.panesarinfotech.flooringmastery.dao;

import com.panesarinfotech.flooringmastery.dto.Order;
import java.io.IOException;
import java.util.List;
import java.util.Set;
/**
 *
 * @author panesar
 */


public interface OrderDao {

    /**
     *
     * @param order
     * @return
     * @throws com.panesarinfotech.flooringmastery.dao.FlooringMasteryPersistenceException
     * @throws com.panesarinfotech.flooringmastery.dao.FlooringMasteryNoOrderException
     * @throws java.io.IOException
     * @throws com.panesarinfotech.flooringmastery.dao.FlooringMasteryInvalidDateException
     */
    Order addOrder(Order order)
            throws FlooringMasteryPersistenceException,
            FlooringMasteryNoOrderException, IOException, FlooringMasteryInvalidDateException;

    /**
     *
     * @param date
     * @return
     * @throws com.panesarinfotech.flooringmastery.dao.FlooringMasteryPersistenceException
     * @throws com.panesarinfotech.flooringmastery.dao.FlooringMasteryNoOrderException
     * @throws com.panesarinfotech.flooringmastery.dao.FlooringMasteryInvalidDateException
     */
    List<Order> getOrderListByDate(String date)
            throws FlooringMasteryPersistenceException,
            FlooringMasteryNoOrderException, FlooringMasteryInvalidDateException;

    /**
     *
     * @param date
     * @param number
     * @return
     * @throws com.panesarinfotech.flooringmastery.dao.FlooringMasteryPersistenceException
     * @throws com.panesarinfotech.flooringmastery.dao.FlooringMasteryNoOrderException
     * @throws com.panesarinfotech.flooringmastery.dao.FlooringMasteryInvalidDateException
     */
    Order getOrderByDate(String date, int number)
            throws FlooringMasteryPersistenceException,
            FlooringMasteryNoOrderException, FlooringMasteryInvalidDateException;

    /**
     *
     * @param order
     * @param updated
     * @return
     * @throws com.panesarinfotech.flooringmastery.dao.FlooringMasteryPersistenceException
     * @throws com.panesarinfotech.flooringmastery.dao.FlooringMasteryNoOrderException
     * @throws com.panesarinfotech.flooringmastery.dao.FlooringMasteryInvalidDateException
     */
    Order updateOrder(Order order, Order updated)
            throws FlooringMasteryPersistenceException,
            FlooringMasteryNoOrderException, FlooringMasteryInvalidDateException;

    /**
     *
     * @param order
     * @return
     * @throws com.panesarinfotech.flooringmastery.dao.FlooringMasteryPersistenceException
     * @throws com.panesarinfotech.flooringmastery.dao.FlooringMasteryNoOrderException
     * @throws com.panesarinfotech.flooringmastery.dao.FlooringMasteryInvalidDateException
     */
    Order removeOrder(Order order)
            throws FlooringMasteryPersistenceException,
            FlooringMasteryNoOrderException, FlooringMasteryInvalidDateException;
    
    List<String> getFileNames();
}
