package io.swagger.service;

import io.swagger.model.IbanGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class IbanGeneratorService {


    @Autowired
    private IbanGenerator ibanGenerator;


    public String generateIban(){
        return this.ibanGenerator.generateIban();
    }
}
