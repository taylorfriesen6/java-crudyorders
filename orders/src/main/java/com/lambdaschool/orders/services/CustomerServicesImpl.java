package com.lambdaschool.orders.services;

import com.lambdaschool.orders.models.Agent;
import com.lambdaschool.orders.models.Customer;
import com.lambdaschool.orders.models.Order;
import com.lambdaschool.orders.models.Payment;
import com.lambdaschool.orders.repositories.AgentsRepository;
import com.lambdaschool.orders.repositories.CustomersRepository;
import com.lambdaschool.orders.repositories.PaymentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.util.ArrayList;
import java.util.List;

@Transactional
@Service(value = "customerService")
public class CustomerServicesImpl implements CustomerServices{
    @Autowired
    private CustomersRepository customersRepository;
    @Autowired
    private AgentsRepository agentsRepository;
    @Autowired
    private PaymentRepository paymentRepository;

    @Override
    public List<Customer> findAllCustomers() {
        List<Customer> list = new ArrayList<>();
        customersRepository.findAll().iterator().forEachRemaining(list::add);
        return list;
    }

    @Override
    public Customer findByCustcode(long custcode) {
        return customersRepository.findByCustcode(custcode);
    }

    @Override
    public List<Customer> findByNameLike(String matcher) {
        //System.out.println(matcher);
        List<Customer> result = customersRepository.findByCustnameContainingIgnoringCase(matcher);
        //System.out.println(result);
        return result;
    }

    @Transactional
    @Override
    public Customer save(Customer customer) {
        Customer newCustomer = new Customer();

        if(customer.getCustcode() != 0) {
            customersRepository.findById(customer.getCustcode())
                .orElseThrow(() -> new EntityNotFoundException("Customer " + customer.getCustcode() + " not found"));
            newCustomer.setCustcode(customer.getCustcode());
        }

        newCustomer.setCustname(customer.getCustname());
        newCustomer.setCustcity(customer.getCustcity());
        newCustomer.setWorkingarea(customer.getWorkingarea());
        newCustomer.setCustcountry(customer.getCustcountry());
        newCustomer.setGrade(customer.getGrade());
        newCustomer.setOpeningamt(customer.getOpeningamt());
        newCustomer.setReceiveamt(customer.getReceiveamt());
        newCustomer.setPaymentamt(customer.getPaymentamt());
        newCustomer.setOutstandingamt(customer.getOutstandingamt());
        newCustomer.setPhone(customer.getPhone());
//        @ManyToOne
//        @JoinColumn(name = "agentcode", nullable = false)
//        @JsonIgnoreProperties(value = "customers", allowSetters = true)
//        private Agent agent;
        Agent agent = customer.getAgent();
        Agent newAgent = agentsRepository.findById(agent.getAgentcode())
                .orElseThrow(() -> new EntityNotFoundException("Agent " + agent.getAgentcode() + " not found"));
        newCustomer.setAgent(newAgent);

//        @OneToMany(mappedBy = "customer",
//                cascade = CascadeType.ALL,
//                orphanRemoval = true)
//        @JsonIgnoreProperties(value = "customer", allowSetters = true)
//        private List<Order> orders = new ArrayList<>();

        for (Order order : customer.getOrders()) {
            Order newOrder = new Order();
            newOrder.setOrdamount(order.getOrdamount());
            newOrder.setAdvanceamount(order.getAdvanceamount());
            newOrder.setOrderdescription(order.getOrderdescription());
            newOrder.setCustomer(newCustomer);
            for (Payment payment : order.getPayments()) {
                Payment newPayment = paymentRepository.findById(payment.getPaymentid())
                        .orElseThrow(() -> new EntityNotFoundException("Payment " + payment.getPaymentid() + " not found"));
                newOrder.getPayments().add(newPayment);
            }
            newCustomer.getOrders().add(newOrder);
        }

        return customersRepository.save(newCustomer);
    }

    @Transactional
    @Override
    public Customer update(Customer customer, long id) {
        Customer newCustomer = customersRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Customer " + id + " not found"));

        if (customer.getCustname() != null) newCustomer.setCustname(customer.getCustname());
        if (customer.getCustcity() != null) newCustomer.setCustcity(customer.getCustcity());
        if (customer.getWorkingarea() != null) newCustomer.setWorkingarea(customer.getWorkingarea());
        if (customer.getCustcountry() != null) newCustomer.setCustcountry(customer.getCustcountry());
        if (customer.getGrade() != null) newCustomer.setGrade(customer.getGrade());
        if (customer.hasvalueforopeningamt) newCustomer.setOpeningamt(customer.getOpeningamt());
        if (customer.hasvalueforreceiveamt) newCustomer.setReceiveamt(customer.getReceiveamt());
        if (customer.hasvalueforpaymentamt) newCustomer.setPaymentamt(customer.getPaymentamt());
        if (customer.hasvalueforoutstandingamt) newCustomer.setOutstandingamt(customer.getOutstandingamt());
        if (customer.getPhone() != null) newCustomer.setPhone(customer.getPhone());
        if (customer.getAgent() != null) {
            Agent agent = customer.getAgent();
            Agent newAgent = agentsRepository.findById(agent.getAgentcode())
                    .orElseThrow(() -> new EntityNotFoundException("Agent " + agent.getAgentcode() + " not found"));
            newCustomer.setAgent(newAgent);
        }
        if (customer.getOrders().size() > 0) {
            for (Order order : customer.getOrders()) {
                Order newOrder = new Order();
                newOrder.setOrdamount(order.getOrdamount());
                newOrder.setAdvanceamount(order.getAdvanceamount());
                newOrder.setOrderdescription(order.getOrderdescription());
                newOrder.setCustomer(newCustomer);
                for (Payment payment : order.getPayments()) {
                    Payment newPayment = paymentRepository.findById(payment.getPaymentid())
                            .orElseThrow(() -> new EntityNotFoundException("Payment " + payment.getPaymentid() + " not found"));
                    newOrder.getPayments().add(newPayment);
                }
                newCustomer.getOrders().add(newOrder);
            }
        }

        return customersRepository.save(newCustomer);
    }
}
