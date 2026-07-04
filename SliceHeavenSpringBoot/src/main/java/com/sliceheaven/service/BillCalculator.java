package com.sliceheaven.service;

import com.sliceheaven.model.Order;

public class BillCalculator {

    public static double calculateTotal(Order order) {

        double subtotal = order.getSubTotal();

        double serviceCharge = order.getServiceCharge();

        double tax = TaxCalculator.calculateTax(subtotal + serviceCharge);

        double total = subtotal + serviceCharge + tax;

        return Math.round(total * 100.0) / 100.0;
    }
}