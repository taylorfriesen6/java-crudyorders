package com.lambdaschool.orders.services;

import com.lambdaschool.orders.models.Customer;
import com.lambdaschool.orders.repositories.CustomersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Transactional
@Service(value = "customerService")
public class CustomerServicesImpl implements CustomerServices{
    @Autowired
    private CustomersRepository restrepos;

    @Override
    public List<Customer> findAllCustomers() {
        List<Customer> list = new ArrayList<>();
        restrepos.findAll().iterator().forEachRemaining(list::add);
        return list;
    }

    @Override
    public Customer findByCustcode(long custcode) {
        return restrepos.findByCustcode(custcode);
    }

    @Override
    public List<Customer> findByNameLike(String matcher) {
        //System.out.println(matcher);
        List<Customer> result = restrepos.findByCustnameContainingIgnoringCase(matcher);
        //System.out.println(result);
        return result;
    }
}
