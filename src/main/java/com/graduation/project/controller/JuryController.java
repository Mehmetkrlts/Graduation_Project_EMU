package com.graduation.project.controller;

import com.graduation.project.model.entities.Jury;
import com.graduation.project.service.impl.JuryDetailsServiceImpl;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class JuryController {

    private final JuryDetailsServiceImpl juryDetailsService;

    public JuryController(JuryDetailsServiceImpl juryDetailsService) {
        this.juryDetailsService = juryDetailsService;
    }

    @RequestMapping(value ="/createjury", method = RequestMethod.POST)
    public ResponseEntity<String> createJury(@RequestBody Jury jury){
        juryDetailsService.createJury(jury);
        return ResponseEntity.ok("Jury Created");
    }

}
