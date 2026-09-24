package com.enterprise.commerce.orderservice.controller;

import com.enterprise.commerce.orderservice.entity.Order;
import com.enterprise.commerce.orderservice.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class OrderWebController {

    @Autowired
    private OrderRepository orderRepository;

    @GetMapping("/orders")
    public String viewOrders(Model model) {
        model.addAttribute("orders", orderRepository.findAll());
        return "orders";
    }

    @PostMapping("/orders/place")
    public String placeOrder() {
        Order order = new Order();
        order.setStatus("CREATED");
        orderRepository.save(order);
        return "redirect:/orders";
    }
}
