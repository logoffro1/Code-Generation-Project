package io.swagger.service;

import com.sun.xml.bind.v2.model.core.ID;
import io.swagger.model.Account;
import io.swagger.model.Transaction;
import io.swagger.repository.AccountRepository;
import org.hibernate.tool.schema.internal.exec.ScriptTargetOutputToFile;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AccountService {

    @Autowired
    private AccountRepository accountRepository;

    public Account getAccountById(long id) {
        // return accountRepository != null ? accountRepository.findById(id).get() : null;
        return accountRepository.findById(id).get();
    }

    public List<Account> getAllAccounts() {
        return (List<Account>) accountRepository.findAll();
    }

    public void createAccount(Account account) {

        accountRepository.save(account);
    }

    public boolean isIbanTaken(String iban) {
        List<Account> listOfAccounts = getAllAccounts();


        for (Account account: listOfAccounts){
            if(account.getIBAN().equals(iban)){
                return true;
            }
        }
        return false;
    }

}
