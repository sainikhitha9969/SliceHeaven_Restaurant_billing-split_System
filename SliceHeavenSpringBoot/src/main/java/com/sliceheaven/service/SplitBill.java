package com.sliceheaven.service;

import com.sliceheaven.model.Order;

public class SplitBill {

    public static double splitEven(Order order, int people) {

        double total = BillCalculator.calculateTotal(order);

        return Math.round((total / people) * 100.0) / 100.0;
    }
}