package com.sliceheaven.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import com.sliceheaven.model.Customer;
import com.sliceheaven.model.Modifier;
import com.sliceheaven.model.Order;
import com.sliceheaven.model.OrderItem;
import com.sliceheaven.model.Pizza;
import com.sliceheaven.service.BillCalculator;

@Controller
public class OrderController {

    @GetMapping("/")
    public String home() {
        return "forward:/index.html";
    }

    @PostMapping("/calculate")
    @ResponseBody
    public String calculateBill(

            @RequestParam String customerName,
            @RequestParam String phone,
            @RequestParam String pizzaName,
            @RequestParam int quantity,
            @RequestParam String modifierName,
            @RequestParam String drinkName) {

        // Pizza Price decided by server
        double pizzaPrice = 0;

        switch (pizzaName) {

            case "Margherita": pizzaPrice = 200; break;
            case "Farm House": pizzaPrice = 350; break;
            case "Veg Supreme": pizzaPrice = 320; break;
            case "Paneer Pizza": pizzaPrice = 300; break;
            case "Cheese Burst": pizzaPrice = 400; break;
            case "Mexican Green Wave": pizzaPrice = 380; break;
            case "Corn Delight": pizzaPrice = 250; break;
            case "Veg Loaded": pizzaPrice = 420; break;
            case "Paneer Tikka": pizzaPrice = 340; break;
            case "Double Cheese": pizzaPrice = 450; break;

            case "Chicken Dominator": pizzaPrice = 520; break;
            case "Chicken Tikka": pizzaPrice = 540; break;
            case "Chicken Pepperoni": pizzaPrice = 560; break;
            case "Chicken Fiesta": pizzaPrice = 500; break;
            case "BBQ Chicken": pizzaPrice = 580; break;
            case "Chicken Supreme": pizzaPrice = 620; break;
            case "Peri Peri Chicken": pizzaPrice = 600; break;
            case "Chicken Sausage": pizzaPrice = 520; break;
            case "Spicy Chicken": pizzaPrice = 550; break;
            case "Chicken Deluxe": pizzaPrice = 650; break;
        }

        Customer customer = new Customer(customerName, phone);

        Pizza pizza = new Pizza(pizzaName, pizzaPrice);

        Order order = new Order(customer);

        OrderItem item = new OrderItem(pizza, quantity);
        
       
        
        
        double modifierPrice = 0;

        switch(modifierName){

            case "Extra Cheese": modifierPrice = 50; break;
            case "Olives": modifierPrice = 40; break;
            case "Corn": modifierPrice = 30; break;
            case "Mushroom": modifierPrice = 60; break;
            case "Paneer": modifierPrice = 70; break;
            case "Jalapeno": modifierPrice = 45; break;    
        } 
        
        double drinkPrice = 0;

        switch(drinkName){

            case "Coke": drinkPrice = 40; break;
            case "Pepsi": drinkPrice = 40; break;
            case "Sprite": drinkPrice = 45; break;
            case "Fanta": drinkPrice = 40; break;
            case "Water Bottle": drinkPrice = 20; break;
            case "Cold Coffee": drinkPrice = 120; break;
            case "Chocolate Milkshake": drinkPrice = 150; break;
        }
        if (!modifierName.equals("None")) {
            item.addModifier(new Modifier(modifierName, modifierPrice, true));
        }

        if (!drinkName.equals("None")) {
            item.addModifier(new Modifier(drinkName, drinkPrice, true));
        }

        order.addItem(item);

        double subtotal = order.getSubTotal();

        double serviceCharge = order.getServiceCharge();

        double tax = (subtotal + serviceCharge) * 0.10;

        double total = BillCalculator.calculateTotal(order);

        return "<html>"
                + "<body style='font-family:Arial;background:#f2f2f2;'>"

                + "<div style='width:550px;margin:auto;background:white;padding:20px;border-radius:10px;'>"

                + "<h2 align='center'>SliceHeaven Restaurant</h2>"

                + "<hr>"

                + "<b>Customer :</b> " + customerName + "<br><br>"

                + "<b>Phone :</b> " + phone + "<br><br>"

                + "<b>Pizza :</b> " + pizzaName + "<br>"

                + "<b>Pizza Price :</b> ₹" + pizzaPrice + "<br><br>"

                + "<b>Quantity :</b> " + quantity + "<br><br>"

                + "<b>Modifier :</b> " + modifierName + " (₹" + modifierPrice + ")<br><br>"

                + "<b>Drink :</b> " + drinkName + " (₹" + drinkPrice + ")<br><br>"

                + "<hr>"

                + "<b>Subtotal :</b> ₹" + String.format("%.2f", subtotal) + "<br><br>"

                + "<b>Service Charge (5%) :</b> ₹"
                + String.format("%.2f", serviceCharge)
                + "<br><br>"

                + "<b>GST (10%) :</b> ₹"
                + String.format("%.2f", tax)
                + "<br><br>"

                + "<h2>Total Bill : ₹"
                + String.format("%.2f", total)
                + "</h2>"

                + "<hr>"

                + "<form action='/split' method='post'>"

                + "<input type='hidden' name='total' value='" + total + "'>"

                + "<label><b>Number of Friends</b></label><br><br>"

                + "<input type='number' name='friends' min='1' required><br><br>"

                + "<button style='padding:10px;background:green;color:white;'>Split Bill Evenly</button>"

                + "</form>"

                + "</div>"

                + "</body></html>";
    }

    @PostMapping("/split")
    @ResponseBody
    public String splitBill(@RequestParam double total,
                            @RequestParam int friends) {

        if (friends <= 0) {
            return "<h2>Invalid Number of Friends</h2>";
        }

        double each = Math.round((total / friends) * 100.0) / 100.0;

        StringBuilder bill = new StringBuilder();

        bill.append("<html>");
        bill.append("<body style='font-family:Arial;background:#f2f2f2;'>");
        bill.append("<div style='width:500px;margin:auto;background:white;padding:20px;border-radius:10px;'>");

        bill.append("<h2 align='center'>Bill Split Successfully</h2>");
        bill.append("<hr>");

        bill.append("<h3>Total Bill : ₹")
            .append(String.format("%.2f", total))
            .append("</h3>");

        bill.append("<h3>Friends : ")
            .append(friends)
            .append("</h3>");

        bill.append("<table border='1' width='100%' cellpadding='8'>");
        bill.append("<tr><th>Friend</th><th>Amount</th></tr>");

        for (int i = 1; i <= friends; i++) {
            bill.append("<tr>");
            bill.append("<td>Friend ").append(i).append("</td>");
            bill.append("<td>₹").append(String.format("%.2f", each)).append("</td>");
            bill.append("</tr>");
        }

        bill.append("</table>");

        bill.append("<br><a href='/'><button>New Order</button></a>");

        bill.append("</div>");
        bill.append("</body>");
        bill.append("</html>");

        return bill.toString();
    }
}