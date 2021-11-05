/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.panesarinfotech.flooringmastery.dao;

import java.io.FileNotFoundException;
import java.util.List;

/**
 *
 * @author panesar
 */
public interface ExportDao {

    void writeFile(List<String> myList) throws FileNotFoundException, FlooringMasteryPersistenceException;

}
