package com.lambdaschool.orders.services;

import com.lambdaschool.orders.models.Order;

public interface OrderServices {
    Order findOrderByNum(long num);
}
