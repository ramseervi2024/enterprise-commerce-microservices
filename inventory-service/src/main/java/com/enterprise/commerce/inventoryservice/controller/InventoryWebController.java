package com.enterprise.commerce.inventoryservice.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.GetMapping;

@Controller
public class InventoryWebController {

    @GetMapping("/")
    public String index(Model model) {
        model.addAttribute("serviceName", "Inventory Service");
        model.addAttribute("status", "Running successfully connected to Eureka & Config Server!");
        return "index";
    }
}
