package com.lambdaschool.orders.services;

import com.lambdaschool.orders.models.Agent;
import com.lambdaschool.orders.repositories.AgentsRepository;
import com.lambdaschool.orders.repositories.CustomersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@Service(value = "agentService")
public class AgentServicesImpl implements AgentServices {
    @Autowired
    private AgentsRepository agentsRepository;

    @Override
    public Agent findAgentByCode(long code) {
        return agentsRepository.findByAgentcode(code);
    }
}
