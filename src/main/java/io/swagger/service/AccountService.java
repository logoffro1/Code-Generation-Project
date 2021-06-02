package io.swagger.service;

import com.sun.xml.bind.v2.model.core.ID;
import io.swagger.model.Account;
import io.swagger.repository.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AccountService  {

    @Autowired
    private AccountRepository accountRepository;

    public Account getAccountById(long id)
    {
       // return accountRepository != null ? accountRepository.findById(id).get() : null;
        return accountRepository.findById(id).get();
    }

    public void createAccount(Account account){
        accountRepository.save(account);
    }

}
