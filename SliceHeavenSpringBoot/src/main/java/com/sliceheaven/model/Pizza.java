package com.sliceheaven.model;

public class Pizza {

    private String name;
    private double basePrice;

    public Pizza(String name, double basePrice) {
        this.name = name;
        this.basePrice = basePrice;
    }

    public String getName() {
        return name;
    }

    public double getBasePrice() {
        return basePrice;
    }
}