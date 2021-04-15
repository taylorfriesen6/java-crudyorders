package com.lambdaschool.orders.services;

import com.lambdaschool.orders.models.Order;
import com.lambdaschool.orders.repositories.OrdersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@Service(value = "orderService")
public class OrderServicesImpl implements OrderServices {
    @Autowired
    private OrdersRepository ordersRepository;

    @Override
    public Order findOrderByNum(long num) {
        return ordersRepository.findByOrdnum(num);
    }
}
