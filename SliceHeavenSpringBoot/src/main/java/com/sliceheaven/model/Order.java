package com.sliceheaven.model;

import java.util.ArrayList;
import java.util.List;

public class Order {

    private Customer customer;
    private List<OrderItem> items;

    public Order(Customer customer) {
        this.customer = customer;
        this.items = new ArrayList<>();
    }

    public void addItem(OrderItem item) {
        items.add(item);
    }

    public Customer getCustomer() {
        return customer;
    }

    public List<OrderItem> getItems() {
        return items;
    }

    // Calculate subtotal
    public double getSubTotal() {

        double subtotal = 0;

        for (OrderItem item : items) {
            subtotal += item.getTotal();
        }

        return subtotal;
    }

    // 5% Service Charge
    public double getServiceCharge() {
        return getSubTotal() * 0.05;
    }

}