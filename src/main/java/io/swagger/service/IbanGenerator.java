package io.swagger.service;

import io.swagger.repository.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Random;


public class IbanGenerator {

    @Autowired
    private AccountService accountService;

    // General format that needs to be created : NLxxINHO0xxxxxxxxx

    private final String prefix = "NL"; // Prefix is a constant of NL
    private final String prefixInh= "INHO0";


    public  String generateIban(){
        String newIban="";
        do{
            int ibanNumbers=randomRange(100000000,999999999);
            int afterPrefixNumbers=randomRange(10,99);
            newIban=String.format("%s%d%s%d",this.prefix,afterPrefixNumbers,this.prefixInh,ibanNumbers);
        }while(accountService.isIbanTaken(newIban));
        return newIban;
    }

    //For randomizing the number parts of the generated IBAN.
    private int randomRange (int min, int max){
        return (
                new Random().nextInt(max+1-min)+min
        );
    }

}
