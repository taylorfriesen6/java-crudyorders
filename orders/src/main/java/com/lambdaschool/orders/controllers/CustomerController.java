package com.lambdaschool.orders.controllers;

import com.lambdaschool.orders.models.Customer;
import com.lambdaschool.orders.services.CustomerServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/customers")
public class CustomerController {
    @Autowired
    private CustomerServices customerServices;

    @GetMapping(value = "/orders", produces = "application/json")
    public ResponseEntity<List<Customer>> getAllCustomers () {
        List<Customer> customers = customerServices.findAllCustomers();
        return new ResponseEntity<>(customers, HttpStatus.OK);
    }

    @GetMapping(value = "/customer/{custcode}", produces = "application/json")
    public ResponseEntity<Customer> getCustomerByCustcode (@PathVariable long custcode) {
        return new ResponseEntity<>(customerServices.findByCustcode(custcode), HttpStatus.OK);
    }

    @GetMapping(value = "/namelike/{matcher}", produces = "application/json")
    public ResponseEntity<List<Customer>> getByNameLike (@PathVariable String matcher) {
        List<Customer> customers = customerServices.findByNameLike(matcher);
        return new ResponseEntity<>(customers, HttpStatus.OK);
    }
}
