package com.lambdaschool.orders.repositories;

import com.lambdaschool.orders.models.Customer;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface CustomersRepository extends CrudRepository<Customer, Long> {
    Customer findByCustcode(long custcode);
    List<Customer> findByCustnameContainingIgnoringCase(String matcher);
}
