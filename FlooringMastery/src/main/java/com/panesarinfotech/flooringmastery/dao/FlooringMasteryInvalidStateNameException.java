/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.panesarinfotech.flooringmastery.dao;

/**
 *
 * @author panesar
 */
public class FlooringMasteryInvalidStateNameException extends Exception {
    
    public FlooringMasteryInvalidStateNameException(String msg){
        super(msg);
    } 
    
    public FlooringMasteryInvalidStateNameException(String msg, Throwable cause){
        super(msg, cause);
    }
}
