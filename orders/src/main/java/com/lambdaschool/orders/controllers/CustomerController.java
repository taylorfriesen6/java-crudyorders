package com.lambdaschool.orders.controllers;

import com.lambdaschool.orders.models.Customer;
import com.lambdaschool.orders.services.CustomerServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
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

    @PostMapping(value = "/customer", consumes = "application/json")
    public ResponseEntity<Void> addCustomer (@Valid @RequestBody Customer customer) {
        customer.setCustcode(0);
        customer = customerServices.save(customer);

        HttpHeaders responseHeaders = new HttpHeaders();
        URI newCustomerURI = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{custcode}")
                .buildAndExpand(customer.getCustcode())
                .toUri();
        responseHeaders.setLocation(newCustomerURI);

        return new ResponseEntity<>(null, responseHeaders, HttpStatus.CREATED);
    }

    @PutMapping(value = "/customer/{id}", consumes = "application/json")
    public ResponseEntity<Void> replaceCustomer (@Valid @RequestBody Customer customer, @PathVariable long id) {
        customer.setCustcode(id);
        customerServices.save(customer);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PatchMapping(value = "/customer/{id}", consumes = "application/json")
    public ResponseEntity<Void> updateCustomer (@Valid @RequestBody Customer customer, @PathVariable long id) {
        customerServices.update(customer, id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping(value = "/customer/{id}")
    public ResponseEntity<Void> deleteCustomer (@PathVariable long id) {
        customerServices.delete(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
