package com.lambdaschool.orders.services;

import com.lambdaschool.orders.models.Agent;

public interface AgentServices {
    public Agent findAgentByCode (long code);
}
