package io.swagger.service;

import io.swagger.model.Account;
import io.swagger.repository.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AccountService  {

    @Autowired
    private AccountRepository accountRepository;


    public void createAccount(Account account){
        accountRepository.save(account);
    }

}
