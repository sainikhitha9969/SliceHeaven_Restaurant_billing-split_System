package com.sliceheaven.model;

import java.util.ArrayList;
import java.util.List;

public class OrderItem {

    private Pizza pizza;
    private int quantity;
    private List<Modifier> modifiers;
    private boolean voidItem;

    public OrderItem(Pizza pizza, int quantity) {
        this.pizza = pizza;
        this.quantity = quantity;
        this.modifiers = new ArrayList<>();
        this.voidItem = false;
    }

    public void addModifier(Modifier modifier) {
        modifiers.add(modifier);
    }

    public void voidItem() {
        voidItem = true;
    }

    public double getTotal() {

        if (voidItem)
            return 0;

        double total = pizza.getBasePrice();

        for (Modifier m : modifiers) {
            total += m.getPrice();
        }

        return total * quantity;
    }
}