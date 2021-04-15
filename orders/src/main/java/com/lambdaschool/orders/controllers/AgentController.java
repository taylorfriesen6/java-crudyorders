package com.lambdaschool.orders.controllers;

import com.lambdaschool.orders.models.Agent;
import com.lambdaschool.orders.services.AgentServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/agents")
public class AgentController {
    @Autowired
    private AgentServices agentServices;

    @GetMapping(value = "/agent/{code}", produces = "application/json")
    public ResponseEntity<Agent> findAgentByCode(@PathVariable long code) {
        Agent agent = agentServices.findAgentByCode(code);
        return new ResponseEntity<>(agent, HttpStatus.OK);
    }
}
