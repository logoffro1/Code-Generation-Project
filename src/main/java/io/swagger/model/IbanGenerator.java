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

    private final String ibanOfTheBank="NL01INHO00000001";


    public String generateIban()
    {
        String newIban;
        do
        {
            int ibanNumbers = randomRange(100000000, 999999999);
            int afterPrefixNumbers = randomRange(10, 99);

            newIban = String.format("%s%d%s%d", this.PREFIX, afterPrefixNumbers, this.PREFIX_INH, ibanNumbers);
            //Loop continues if generated iban is in the database OR generated Iban is actually equal to bank's iban
            // Even though it's a slight possibility, we have to ensure that it doesn't happen
        } while (accountServiceImpl.isIbanPresent(newIban)|| newIban.equals(this.ibanOfTheBank));
        return newIban;
    }

    //For randomizing the number parts of the generated IBAN.
    private int randomRange(int min, int max)
    {
        return new Random().nextInt(max + 1 - min) + min;
    }

}
