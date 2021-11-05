/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.panesarinfotech.flooringmastery.service;

import com.panesarinfotech.flooringmastery.dao.ExportDataDaoImpl;
import com.panesarinfotech.flooringmastery.dao.FlooringMasteryInvalidAreaInputException;
import com.panesarinfotech.flooringmastery.dao.FlooringMasteryInvalidCustomerNameException;
import com.panesarinfotech.flooringmastery.dao.FlooringMasteryInvalidDateException;
import com.panesarinfotech.flooringmastery.dao.FlooringMasteryInvalidProductNameException;
import com.panesarinfotech.flooringmastery.dao.FlooringMasteryInvalidStateNameException;
import com.panesarinfotech.flooringmastery.dao.FlooringMasteryNoOrderException;
import com.panesarinfotech.flooringmastery.dao.FlooringMasteryPersistenceException;
import com.panesarinfotech.flooringmastery.dao.OrderDaoImpl;
import com.panesarinfotech.flooringmastery.dao.ProductDaoImpl;
import com.panesarinfotech.flooringmastery.dao.TaxDaoImpl;
import com.panesarinfotech.flooringmastery.dto.Order;
import com.panesarinfotech.flooringmastery.dto.Product;
import com.panesarinfotech.flooringmastery.dto.Tax;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author panesar
 */
public class FlooringMasteryServiceImpl implements FlooringMasteryService {

    OrderDaoImpl orderDao;
    ProductDaoImpl productDao;
    TaxDaoImpl taxDao;
    ExportDataDaoImpl dataDao;

    public FlooringMasteryServiceImpl(OrderDaoImpl orderDao, ProductDaoImpl productDao, TaxDaoImpl taxDao, ExportDataDaoImpl dataDao) {
        this.orderDao = orderDao;
        this.productDao = productDao;
        this.taxDao = taxDao;
        this.dataDao = dataDao;
    }

    @Override // done
    public List<Order> displayOrdersByDate(String date)
            throws FlooringMasteryNoOrderException, FlooringMasteryPersistenceException, FlooringMasteryInvalidDateException {
        return orderDao.getOrderListByDate(date);
    }

    @Override
    public void addOrder(Order order) // Order Date, Customer Name, State, Product Type, Area.
         {
System.out.println("1");
        // validateOrder(order);
        Tax tax = new Tax();
        Product product = new Product();
System.out.println("2");
        // Verification
        try {
            tax.setStateAb(order.getStateName().toUpperCase());
            tax = taxDao.getTax(tax.getStateAb());
        } catch (FlooringMasteryPersistenceException e) {
//            throw new FlooringMasteryPersistenceException("The entered State does not match any states in our catalog", e);
        }
        // Verification
        try {
            product.setProductName(order.getProductType());
            product = productDao.getProduct(product.getProductName());
        } catch (FlooringMasteryPersistenceException e) {
//            throw new FlooringMasteryPersistenceException("The entered product does not match any item in our catalog", e);
        }

        if (tax.getStateAb().equals(order.getStateName())) { // Verify's state name input with tax records
            order.setTaxRate(tax.getTaxRate()); // Tax rate is set
        }
        if (product.getProductName().equals(order.getProductType())) { // Verify's product input with product records
            order.setCostPerSqFt(product.getCostPerSqFt()); // CostPerSq for product in particular set
            order.setLaborCostPerSqFt(product.getLabourCostPerSqFt()); // LaborCostPerSq for product in particular set
        }
System.out.println("3");
        BigDecimal matCost = materialCost(order.getArea(), order.getCostPerSqFt());
        BigDecimal labourCost = laborCost(order.getArea(), order.getLaborCostPerSqFt());
        BigDecimal totalTaxCost = tax(matCost, labourCost, order);
        BigDecimal totalCost = total(matCost, labourCost, totalTaxCost);
System.out.println("4");
        order.setMaterialCost(matCost);
        order.setLaborCost(labourCost);
        order.setTotalTax(totalTaxCost);
        order.setTotalCost(totalCost);
System.out.println("5");
//        try {
////            return orderDao.addOrder(order);
//        } catch (FlooringMasteryPersistenceException ex) {
//            Logger.getLogger(FlooringMasteryServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
//        } catch (FlooringMasteryNoOrderException ex) {
//            Logger.getLogger(FlooringMasteryServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
//        } catch (IOException ex) {
//            Logger.getLogger(FlooringMasteryServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
//        } catch (FlooringMasteryInvalidDateException ex) {
//            Logger.getLogger(FlooringMasteryServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
//        }
//        return null;
    }

    @Override // done
    public Order updateOrder(Order order, Order updated) // If any changes are made, the fields will be recalculated.
            throws FlooringMasteryNoOrderException, FlooringMasteryPersistenceException, FlooringMasteryInvalidDateException,
            FlooringMasteryInvalidAreaInputException, IOException,
            FlooringMasteryInvalidStateNameException, FlooringMasteryInvalidCustomerNameException,
            FlooringMasteryInvalidProductNameException {

        // validateOrder(updated);
        updated.setOrderNumber(order.getOrderNumber());
        updated.setOrderDate(order.getOrderDate());

        Tax tax = new Tax();
        Product product = new Product();

        if ("".equals(updated.getCustomerName())) {
            updated.setCustomerName(order.getCustomerName());
        }
        if (updated.getArea().compareTo(new BigDecimal("0")) == 0) {
            updated.setArea(order.getArea());
        }
        if ("".equals(updated.getStateName())) {
            updated.setStateName(order.getStateName());
            try {
                tax.setStateAb(updated.getStateName().toUpperCase());
                tax = taxDao.getTax(tax.getStateAb());
            } catch (FlooringMasteryPersistenceException e) {
                throw new FlooringMasteryPersistenceException("The entered State does not match any states in our catalog", e);
            }
        } else {
            // Verification
            try {
                tax.setStateAb(updated.getStateName().toUpperCase());
                tax = taxDao.getTax(tax.getStateAb());
            } catch (FlooringMasteryPersistenceException e) {
                throw new FlooringMasteryPersistenceException("The entered State does not match any states in our catalog", e);
            }
        }
        if ("".equals(updated.getProductType())) {
            updated.setProductType(order.getProductType());
            try {
                product.setProductName(updated.getProductType());
                product = productDao.getProduct(product.getProductName());
            } catch (FlooringMasteryPersistenceException e) {
                throw new FlooringMasteryPersistenceException("The entered product does not match any item in our catalog", e);
            }
        } else {
            try {
                product.setProductName(updated.getProductType());
                product = productDao.getProduct(product.getProductName());
            } catch (FlooringMasteryPersistenceException e) {
                throw new FlooringMasteryPersistenceException("The entered product does not match any item in our catalog", e);
            }
        }

        if (tax.getStateAb().equals(updated.getStateName())) { // Verify's state name input with tax records
            updated.setTaxRate(tax.getTaxRate()); // Tax rate is set
        }
        if (product.getProductName().equals(updated.getProductType())) { // Verify's product input with product records
            updated.setCostPerSqFt(product.getCostPerSqFt()); // CostPerSq for product in particular set
            updated.setLaborCostPerSqFt(product.getLabourCostPerSqFt()); // LaborCostPerSq for product in particular set
        }

        BigDecimal matCost = materialCost(updated.getArea(), updated.getCostPerSqFt());
        BigDecimal labourCost = laborCost(updated.getArea(), updated.getLaborCostPerSqFt());
        BigDecimal totalTaxCost = tax(matCost, labourCost, updated);
        BigDecimal totalCost = total(matCost, labourCost, totalTaxCost);

        updated.setMaterialCost(matCost);
        updated.setLaborCost(labourCost);
        updated.setTotalTax(totalTaxCost);
        updated.setTotalCost(totalCost);

        return orderDao.updateOrder(order, updated);
    }

    @Override // done
    public Order removeOrder(Order order)
            throws FlooringMasteryNoOrderException, FlooringMasteryPersistenceException, FlooringMasteryInvalidDateException {
        return orderDao.removeOrder(order);
    }

    @Override
    public Order getOrderByDate(String date, int number)
            throws FlooringMasteryPersistenceException, FlooringMasteryNoOrderException, FlooringMasteryInvalidDateException {
        return orderDao.getOrderByDate(date, number);
    }

    @Override // done
    public BigDecimal materialCost(BigDecimal area, BigDecimal costPerSq) {
        BigDecimal totalCost;
        totalCost = area.multiply(costPerSq).setScale(2, RoundingMode.HALF_UP);
        return totalCost;
    } // Objects area value multiplied by its costPerSq value

    @Override // done
    public BigDecimal laborCost(BigDecimal area, BigDecimal laborCostPerSq) {
        BigDecimal totalCost;
        totalCost = area.multiply(laborCostPerSq).setScale(2, RoundingMode.HALF_UP);
        return totalCost;
    } // objects area value multiplied by its laborCostPerSq value;

    @Override // done
    public BigDecimal tax(BigDecimal material, BigDecimal labor, Order order) {
        BigDecimal totalTax;
        BigDecimal costOfMatandLabor;
        BigDecimal costOfTaxRateDivided;
        costOfMatandLabor = material.add(labor).setScale(2, RoundingMode.HALF_UP);
        costOfTaxRateDivided = order.getTaxRate().divide(new BigDecimal("100").setScale(2, RoundingMode.HALF_UP));
        totalTax = costOfMatandLabor.multiply(costOfTaxRateDivided).setScale(2, RoundingMode.HALF_UP);
        return totalTax;
    }
    // Tax = (MaterialCost + LaborCost) * (TaxRate/100)

    @Override // done
    public BigDecimal total(BigDecimal materialC, BigDecimal laborC, BigDecimal taxTotal) {
        BigDecimal totalCostReturn;
        totalCostReturn = materialC.add(laborC).add(taxTotal).setScale(2, RoundingMode.HALF_UP);
        return totalCostReturn;
    } // Total = (MaterialCost + LaborCost + Tax) 

//    private Order validateOrder(Order order) throws FlooringMasteryInvalidAreaInputException,
//            FlooringMasteryInvalidStateNameException, FlooringMasteryInvalidCustomerNameException,
//            FlooringMasteryInvalidProductNameException {
//        if (order.getCustomerName() == null || order.getCustomerName().trim().length() == 0) {
//            throw new FlooringMasteryInvalidCustomerNameException("Please enter a valid name.");
//        } else if (order.getStateName() == null || order.getStateName().trim().length() == 0) {
//            throw new FlooringMasteryInvalidStateNameException("Please enter a valid state name");
//        } else if (order.getProductType() == null || order.getProductType().trim().length() == 0) {
//            throw new FlooringMasteryInvalidProductNameException("Please enter a valid product name");
//        } else if (order.getArea().compareTo(new BigDecimal("0")) == 0 || order.getArea().compareTo(new BigDecimal("0")) == -1
//                || order.getArea() == null) {
//            throw new FlooringMasteryInvalidAreaInputException("Please enter a value greater than 0 for this field.");
//        }
//        return order;
//    }
    @Override
    public void exportData(List<String> myList) throws FileNotFoundException, FlooringMasteryPersistenceException,
            FlooringMasteryNoOrderException, FlooringMasteryInvalidDateException {
        dataDao.writeFile(myList);
    }

    @Override
    public List<String> getFileNames() {
        List<String> myList = orderDao.getFileNames();
        return myList;
    }
}
