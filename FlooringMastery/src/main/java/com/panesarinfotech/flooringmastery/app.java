package com.panesarinfotech.flooringmastery;

import com.panesarinfotech.flooringmastery.controller.FlooringMasteryController;
import com.panesarinfotech.flooringmastery.dao.FlooringMasteryInvalidAreaInputException;
import com.panesarinfotech.flooringmastery.dao.FlooringMasteryInvalidCustomerNameException;
import com.panesarinfotech.flooringmastery.dao.FlooringMasteryInvalidDateException;
import com.panesarinfotech.flooringmastery.dao.FlooringMasteryInvalidProductNameException;
import com.panesarinfotech.flooringmastery.dao.FlooringMasteryInvalidStateNameException;
import com.panesarinfotech.flooringmastery.dao.FlooringMasteryNoOrderException;
import com.panesarinfotech.flooringmastery.dao.FlooringMasteryPersistenceException;
import com.panesarinfotech.flooringmastery.service.FlooringMasteryService;
import com.panesarinfotech.flooringmastery.ui.FlooringMasteryView;
import com.panesarinfotech.flooringmastery.ui.UserIO;
import com.panesarinfotech.flooringmastery.ui.UserIOImpl;
import java.io.IOException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author panesar
 */
public class app {
    
   
    
    public static void main(String[] args) throws FlooringMasteryPersistenceException, FlooringMasteryNoOrderException, FlooringMasteryInvalidDateException,
            FlooringMasteryInvalidAreaInputException, FlooringMasteryInvalidStateNameException, FlooringMasteryInvalidCustomerNameException,
            FlooringMasteryInvalidProductNameException, IOException  {
        
//        UserIO myIo = new UserIOImpl();
//        FlooringMasteryView view = new FlooringMasteryView(myIo) ;
//        FlooringMasteryService service = null;
//        FlooringMasteryController controller;
//        controller = new FlooringMasteryController(view,service);
//        controller.run();
        
        //FlooringMasteryController controller = new FlooringMasteryController();
        
        ApplicationContext ctx = new ClassPathXmlApplicationContext("applicationContext.xml");
        FlooringMasteryController controller = ctx.getBean("controller", FlooringMasteryController.class);
        controller.run(); 
    }
}
