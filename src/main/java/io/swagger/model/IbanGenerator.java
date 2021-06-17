package io.swagger.model;

import io.swagger.service.AccountServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Random;

@Component
public class IbanGenerator
{

    @Autowired
    private AccountServiceImpl accountServiceImpl;

    // General format that needs to be created : NLxxINHO0xxxxxxxxx

    private final String PREFIX = "NL"; // Prefix is a constant of NL
    private final String PREFIX_INH = "INHO0";


    public String generateIban()
    {
        String newIban;
        do
        {
            int ibanNumbers = randomRange(100000000, 999999999);
            int afterPrefixNumbers = randomRange(10, 99);

            newIban = String.format("%s%d%s%d", this.PREFIX, afterPrefixNumbers, this.PREFIX_INH, ibanNumbers);
        } while (accountServiceImpl.isIbanPresent(newIban));
        return newIban;
    }

    //For randomizing the number parts of the generated IBAN.
    private int randomRange(int min, int max)
    {
        return new Random().nextInt(max + 1 - min) + min;
    }

}
