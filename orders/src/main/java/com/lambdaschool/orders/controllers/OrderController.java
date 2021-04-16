package com.lambdaschool.orders.controllers;

import com.lambdaschool.orders.models.Order;
import com.lambdaschool.orders.services.OrderServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

@RestController
@RequestMapping("/orders")
public class OrderController {
    @Autowired
    private OrderServices orderServices;

    @GetMapping(value = "/order/{num}", produces = "application/json")
    public ResponseEntity<Order> findOrderByNum(@PathVariable long num) {
        Order order = orderServices.findOrderByNum(num);
        return new ResponseEntity<>(order, HttpStatus.OK);
    }

    @PostMapping(value = "/order", consumes = "application/json")
    public ResponseEntity<Void> addOrder(@RequestBody Order order) {
        order = orderServices.save(order);
        HttpHeaders responseHeaders = new HttpHeaders();
        URI newCustomerURI = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{ordnum}")
                .buildAndExpand(order.getOrdnum())
                .toUri();
        responseHeaders.setLocation(newCustomerURI);
        return new ResponseEntity<>(responseHeaders, HttpStatus.CREATED);
    }

    @PutMapping(value = "/order/{id}", consumes = "application/json")
    public ResponseEntity<Void> replaceOrder(@RequestBody Order order, @PathVariable long id) {
        order.setOrdnum(id);
        orderServices.save(order);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
