/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.panesarinfotech.flooringmastery.controller;

import com.panesarinfotech.flooringmastery.dao.FlooringMasteryInvalidAreaInputException;
import com.panesarinfotech.flooringmastery.dao.FlooringMasteryInvalidCustomerNameException;
import com.panesarinfotech.flooringmastery.dao.FlooringMasteryInvalidDateException;
import com.panesarinfotech.flooringmastery.dao.FlooringMasteryInvalidProductNameException;
import com.panesarinfotech.flooringmastery.dao.FlooringMasteryInvalidStateNameException;
import com.panesarinfotech.flooringmastery.dao.FlooringMasteryNoOrderException;
import com.panesarinfotech.flooringmastery.dao.FlooringMasteryPersistenceException;
import com.panesarinfotech.flooringmastery.dto.Order;
import com.panesarinfotech.flooringmastery.dto.Product;
import com.panesarinfotech.flooringmastery.dto.Tax;
import com.panesarinfotech.flooringmastery.service.FlooringMasteryService;
import com.panesarinfotech.flooringmastery.ui.FlooringMasteryView;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;

/**
 *
 * @author panesar
 */
public class FlooringMasteryController {

    FlooringMasteryView view;
    FlooringMasteryService service;

    public FlooringMasteryController(FlooringMasteryView view, FlooringMasteryService service) {
        this.view = view;
        this.service = service;
    }

    public void run() throws FlooringMasteryPersistenceException, FlooringMasteryNoOrderException, FlooringMasteryInvalidDateException,
            FlooringMasteryInvalidAreaInputException, FlooringMasteryInvalidStateNameException, FlooringMasteryInvalidStateNameException,
            FlooringMasteryInvalidCustomerNameException, FlooringMasteryInvalidProductNameException, IOException {
        boolean keepGoing = true;
        int menuSelect = 0;
        while (keepGoing) {

            menuSelect = menu();
//            try{
                
            
            switch (menuSelect) {
                case 1:
                    orderList(); 
                    break;
                case 2:
                    addOrder(); 
                    break;
                case 3:
                    updateOrder();
                    break;
                case 4:
                    removeOrder(); 
                    break;
                case 5:
                    exportOrder();
                    break;
                case 6:
                    keepGoing = false;
                    break;
                default:
                    view.genericText("Input one of the above choices, please.");
            }
//            }catch(Exception exception){
//                System.out.println("Unexpected error has Occured. Returning you to main menu. XXX");
//            }
            
        }
    }

    private int menu() {
        return view.printMenu();
    }

    private void addOrder() throws FlooringMasteryPersistenceException, FlooringMasteryNoOrderException, IOException, 
            FlooringMasteryInvalidDateException, FlooringMasteryInvalidAreaInputException, FlooringMasteryInvalidStateNameException, 
            FlooringMasteryInvalidCustomerNameException, FlooringMasteryInvalidProductNameException  {
        view.addOrderBanner();
        List<Product> products = service.getProductList();
        List<Tax> taxs = service.getTaxList();

        Order newOrder = view.newOrderInfo(products,taxs);
        service.addOrder(newOrder);
        System.out.println("ORDER DETAILS: - " + newOrder.getCustomerName()+" - "+newOrder.getProductType()+" - "+newOrder.getOrderDate()+" - "+newOrder.getStateName());
        view.genericSuccessBanner();
    }

    private void orderList() throws FlooringMasteryNoOrderException, FlooringMasteryPersistenceException, FlooringMasteryInvalidDateException {
        view.orderListBanner();
        String orderDate = view.listOfOrdersByDatePrompt();
        List<Order> myList = service.displayOrdersByDate(orderDate);
        view.genericSuccessBanner();
        view.listOfOrdersByDate(myList);
    }

    private void updateOrder() throws FlooringMasteryPersistenceException, FlooringMasteryNoOrderException, FlooringMasteryInvalidDateException,
            FlooringMasteryInvalidAreaInputException, FlooringMasteryInvalidStateNameException, FlooringMasteryInvalidCustomerNameException,
            FlooringMasteryInvalidProductNameException, IOException {
        view.updateOrderBanner();
        String orderDate = view.listOfOrdersByDatePrompt();
        int orderNumber = view.genericTextInt();
        Order getOrder = service.getOrderByDate(orderDate, orderNumber);
        Order updatedOrder = new Order();
        boolean run = true;
        int updateInfo = 0;
        while (run) {
            updateInfo = view.updateInfoPrompt();
            switch (updateInfo) {
                case 1:
                    String newName = view.changeInfoGeneric();
                    updatedOrder.setCustomerName(newName);
                    view.genericSuccessBanner();
                    break;
                case 2:
                    String newState = view.changeInfoGeneric();
                    updatedOrder.setStateName(newState);
                    view.genericSuccessBanner();
                    break;
                case 3:
                    String newProduct = view.changeInfoGeneric();
                    updatedOrder.setProductType(newProduct);
                    view.genericSuccessBanner();
                    break;
                case 4:
                    BigDecimal newArea = view.changeInfoGenericBigDecimal();
                    updatedOrder.setArea(newArea);
                    view.genericSuccessBanner();
                    break;
                case 5:
                    run = false;
                    break;
            }
        }
        service.updateOrder(getOrder, updatedOrder);
    }

    private void removeOrder() throws FlooringMasteryPersistenceException, FlooringMasteryNoOrderException, FlooringMasteryInvalidDateException {
        view.removeOrderBanner();
        String orderDate = view.removeOrderGenericString();
        int orderNumber = view.removeOrderGenericInt();
        Order getOrder = service.getOrderByDate(orderDate, orderNumber);
        view.viewOrder(getOrder);
        boolean run = true;
        int trueOrFalse = 0;
        while(run){
            trueOrFalse = view.toRemoveOrNotToRemove();
            switch(trueOrFalse){
                case 1:
                    service.removeOrder(getOrder);
                    view.genericRemovalSuccessBanner();
                    run = false;
                    break;
                case 2:
                    view.orderRemovalWithheld();
                    run = false;
                    break;
                default:
                    view.genericErrorMsg();
            }
        }
    }
    private void exportOrder() throws FileNotFoundException, FlooringMasteryPersistenceException, 
            FlooringMasteryNoOrderException, FlooringMasteryInvalidDateException{
//        view.exportBanner();
//         boolean run = true;
//        int trueOrFalse = 0;
//        while(run){
//            trueOrFalse = view.export();
//                  switch(trueOrFalse){
//                case 1:
//                    List<String> myList = service.getFileNames();
//                    service.exportData(myList);
//                    view.exportSuccess();
//                    run = false;
//                    break;
//                case 2:
//                    view.exportDenied();
//                    run = false;
//                    break;
//                default:
//                    view.genericErrorMsg();
//                
//            }
//        }
            view.genericSuccessBanner();
    }
}
