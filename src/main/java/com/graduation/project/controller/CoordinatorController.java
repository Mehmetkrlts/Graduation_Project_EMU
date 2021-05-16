package com.graduation.project.controller;

import com.graduation.project.model.entities.Coordinator;
import com.graduation.project.service.impl.CoordinatorDetailsServiceImpl;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class CoordinatorController {

    private final CoordinatorDetailsServiceImpl coordinatorDetailsService;

    public CoordinatorController(CoordinatorDetailsServiceImpl coordinatorDetailsService) {
        this.coordinatorDetailsService = coordinatorDetailsService;
    }

    @RequestMapping(value = "/createcoordinator", method = RequestMethod.POST)
    public ResponseEntity<?> saveUser(@RequestBody Coordinator coordinator) throws Exception {
        coordinatorDetailsService.createCoordinator(coordinator);
        return ResponseEntity.ok("Coordinator registered");
    }


}
