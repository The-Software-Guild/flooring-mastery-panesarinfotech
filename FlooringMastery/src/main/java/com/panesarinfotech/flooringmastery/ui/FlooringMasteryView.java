/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.panesarinfotech.flooringmastery.ui;

import com.panesarinfotech.flooringmastery.dao.FlooringMasteryPersistenceException;
import com.panesarinfotech.flooringmastery.dao.ProductDaoImpl;
import com.panesarinfotech.flooringmastery.dao.TaxDaoImpl;
import com.panesarinfotech.flooringmastery.dto.Order;
import com.panesarinfotech.flooringmastery.dto.Product;
import com.panesarinfotech.flooringmastery.dto.Tax;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author panesar
 */
public class FlooringMasteryView {
    UserIO io;

    public FlooringMasteryView(UserIO io) {
        this.io = io;
    }

    public int printMenu() {
        io.print("***********************************");
        io.print("*   << FLOORING PROGRAM >>        *");
        io.print("*     1. Display Orders           *");
        io.print("*     2. Add an Order             *");
        io.print("*     3. Edit an Order            *");
        io.print("*     4. Remove an Order          *");
        io.print("*     ------------------          *");
        io.print("*     6. Quit                     *");
        io.print("*                                 *");
        return io.readInt("***********************************", 1, 6);
    }

    public Order newOrderInfo(List<Product> productList, List<Tax> taxList ) {
        Order order = new Order();
        String customerName = io.readString("Please enter a name, allowed characters are [a-z] [0-9] as well as periods and comma's. ");
        order.setCustomerName(customerName);
        displayTaxList(taxList);
            Boolean stateChoosen = true;
             while(stateChoosen){
                String stateName = io.readString("Enter your states name here, abbreviations are accepted.").toUpperCase();
                if(containsStateName(taxList,stateName)){
                    order.setStateName(stateName);
                    stateChoosen = false;
                }else{
                    io.print("The STATE NAME you selected is not is the List. Please try again.");
                    io.print(" ");
                }
            }
         
        displayProductList(productList);
            Boolean productChoosen = true;
             while(productChoosen){
                String productType = io.readString("Enter the type of product you're looking to purchase");
                if(containsProductName(productList,productType)){
                    order.setProductType(productType);
                    productChoosen = false;
                }else{
                    io.print("The PRODUCT NAME you selected is not is the List. Please try again.");
                    io.print(" ");
                }
            }
            
        BigDecimal area = io.readDecimal("Enter the area, minimum order size is 100 Sq Ft", new BigDecimal("100"));
        order.setArea(area);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM-dd-yyyy");
        LocalDate ld = LocalDate.now();
        Boolean run = true;
        while(run){
            String dayDate = io.readString("Enter the date you'd like the order to be placed. (in MM-dd-yyyy Format)");
            LocalDate userInput = LocalDate.parse(dayDate, formatter);
            if(userInput.isBefore(ld)){
                io.print("Please enter a valid date");
            }else{
                order.setOrderDate(dayDate);
                run = false;
            }
        }
//        System.out.print("ORDER DETAILS -  VIEW : " +order.getCustomerName()+order.getOrderDate()+order.getProductType()+order.getStateName());
        return order;
    }
    
    public void displayProductList(List<Product> itemList) {
        io.print("----------------------------");
        int indexNum = 0;
        for (Product currentItem : itemList) {
            // Index number to count loops and show ITEM INDEX NUM in menu
                indexNum++;
         
                    String itemInfo = String.format(" %s >> %s : Cost= %s",
                            indexNum,
                            currentItem.getProductName(),
//                            currentItem.getLabourCostPerSqFt(),
                            currentItem.getCostPerSqFt()
                            
                      );
                      io.print(itemInfo);
   
        }
        io.print("^^^^^^^^^^^^^^^^^^^^^^^^^^^^");
    } 
    public void displayTaxList(List<Tax> itemList) {
        io.print("-----------------------");
        int indexNum = 0;
        for (Tax currentItem : itemList) {
            // Index number to count loops and show ITEM INDEX NUM in menu
                indexNum++;
         
                    String itemInfo = String.format("  %s >> %s : Tax= %s",
                            indexNum,
                            currentItem.getStateAb(),
//                            currentItem.getLabourCostPerSqFt(),
                            currentItem.getTaxRate()
                            
                      );
                      io.print(itemInfo);
   
        }
        io.print("^^^^^^^^^^^^^^^^^^^^^^^");
    } 
    
    public boolean containsStateName(final List<Tax> list, final String name){
        return list.stream().filter(o -> o.getStateAb().equals(name)).findFirst().isPresent();
    }
    public boolean containsProductName(final List<Product> list, final String name){
        return list.stream().filter(o -> o.getProductName().equals(name)).findFirst().isPresent();
    }

    public String listOfOrdersByDatePrompt() {
        return io.readString("Please enter the date of the orders you'd like to view/select (in MM-dd-yyyy Format)");
    }

    public void listOfOrdersByDate(List<Order> orderList) {
        for (Order order : orderList) {
            io.print(String.valueOf(order.getOrderNumber()) + " , " + order.getCustomerName() + " , "
                    + order.getStateName() + " , " + order.getProductType() + " , " + order.getOrderDate());

        }
        io.print("\n");
    }

    public void genericText(String msg) {
        io.print(msg);
    }

    public int genericTextInt() {
        return io.readInt("Please enter the order number for the specific order you wish to select");
    }

    public int updateInfoPrompt() {
        io.print("=== EDIT ORDER INFORMATION ===");
        io.print("1. Customer Name");
        io.print("2. State");
        io.print("3. Product Type");
        io.print("4. Area");
        io.print("5. Return to main menu.\n");
        return io.readInt("Please choose from one of the available options you'd like to make changes to.", 1, 5);
    }

    public String changeInfoGeneric() {
        return io.readString("Enter changes here.");
    }

    public BigDecimal changeInfoGenericBigDecimal() {
        return io.readDecimal("Enter changes here");
    }

    public void removeOrderBanner() {
        io.print("===== REMOVE ORDER MENU =====");
    }

    public void orderListBanner() {
        io.print("===== ORDER LIST MENU =====");
    }

    public void addOrderBanner() {
        io.print("===== ADD ORDER MENU =====");
    }

    public void updateOrderBanner() {
        io.print("===== UPDATE ORDER MENU =====");
    }

    public void genericSuccessBanner() {
        io.print("--");
        io.print("----");
        io.print("-------");
        io.print("---------->> Success!");
    }

    public String removeOrderGenericString() {
        return io.readString("Enter the date of the order you wish to remove. (in MM-dd-yyyy Format)");
    }

    public int removeOrderGenericInt() {
        return io.readInt("Enter the number associated with the order you wish to remove.");
    }

    public void viewOrder(Order order) {
        io.print(String.valueOf(order.getOrderNumber()) + " - " + order.getCustomerName() + " - "
                + order.getStateName() + " - " + order.getProductType() + " - " + order.getOrderDate());
    }

    public int toRemoveOrNotToRemove() {
        io.print("Is this the order you wish to remove? Selecting the options below..");
        io.print("1. YES");
        io.print("2. NO");
        return io.readInt("Please select the following options", 1, 2);
    }

    public void genericRemovalSuccessBanner() {
        io.print(".");
        io.print(".");
        io.print(".");
        io.print("... Success! The selected order has been expunged.");
    }

    public void orderRemovalWithheld() {
        io.print("The selected Order has not been removed.");
        io.print("Returning to main menu..");
        io.print("\n");
    }

    public void genericErrorMsg() {
        io.print("Hmmm.. Something went wrong.");
    }

    public int export() {
        io.print("Export existing data? Selecting the options below.");
        io.print("1. YES");
        io.print("2. NO");
        return io.readInt("Please select the following options", 1, 2);
    }

    public void exportSuccess() {
        io.print(".");
        io.print(".");
        io.print(".");
        io.print("... Success!");
    }

    public void exportBanner() {
        io.print("===== EXPORT DATA =====");
    }

    public void exportDenied() {
        io.print("Export withheld.. Returning to menu!");
    }
}
