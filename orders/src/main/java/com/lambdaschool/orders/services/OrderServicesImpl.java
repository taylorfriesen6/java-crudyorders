package com.lambdaschool.orders.services;

import com.lambdaschool.orders.models.Customer;
import com.lambdaschool.orders.models.Order;
import com.lambdaschool.orders.models.Payment;
import com.lambdaschool.orders.repositories.CustomersRepository;
import com.lambdaschool.orders.repositories.OrdersRepository;
import com.lambdaschool.orders.repositories.PaymentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;

@Transactional
@Service(value = "orderService")
public class OrderServicesImpl implements OrderServices {
    @Autowired
    private OrdersRepository ordersRepository;
    @Autowired
    private CustomersRepository customersRepository;
    @Autowired
    private PaymentRepository paymentRepository;

    @Override
    public Order findOrderByNum(long num) {
        return ordersRepository.findByOrdnum(num);
    }

    @Transactional
    @Override
    public Order save(Order order) {
        Order newOrder = new Order();
        newOrder.setOrdamount(order.getOrdamount());
        newOrder.setAdvanceamount(order.getAdvanceamount());
        newOrder.setOrderdescription(order.getOrderdescription());
        Customer customer = order.getCustomer();
        Customer newCustomer = customersRepository.findById(customer.getCustcode())
                .orElseThrow(() -> new EntityNotFoundException("Customer " + customer.getCustcode() + " not found"));
        newOrder.setCustomer(newCustomer);
        for (Payment payment : order.getPayments()) {
            Payment newPayment = paymentRepository.findById(payment.getPaymentid())
                    .orElseThrow(() -> new EntityNotFoundException("Payment " + payment.getPaymentid() + " not found"));
            newOrder.getPayments().add(newPayment);
        }
        return ordersRepository.save(newOrder);
    }
}
