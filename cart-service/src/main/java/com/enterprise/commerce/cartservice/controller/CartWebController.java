package com.enterprise.commerce.cartservice.controller;

import com.enterprise.commerce.cartservice.entity.CartItem;
import com.enterprise.commerce.cartservice.repository.CartRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class CartWebController {

    @Autowired
    private CartRepository cartRepository;

    @GetMapping("/cart")
    public String viewCart(Model model) {
        model.addAttribute("items", cartRepository.findAll());
        return "cart";
    }

    @PostMapping("/cart/add")
    public String addToCart(@RequestParam Long productId, @RequestParam String productName, @RequestParam Integer quantity) {
        CartItem item = new CartItem();
        item.setProductId(productId);
        item.setProductName(productName);
        item.setQuantity(quantity);
        cartRepository.save(item);
        return "redirect:/cart";
    }
}
