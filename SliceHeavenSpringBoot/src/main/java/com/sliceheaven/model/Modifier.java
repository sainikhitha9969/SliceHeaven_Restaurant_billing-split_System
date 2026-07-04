package com.sliceheaven.model;

public class Modifier {

    private String name;
    private double price;
    private boolean add;

    public Modifier(String name, double price, boolean add) {
        this.name = name;
        this.price = price;
        this.add = add;
    }

    public String getName() {
        return name;
    }

    public double getPrice() {
        if (add)
            return price;
        else
            return -price;
    }
}