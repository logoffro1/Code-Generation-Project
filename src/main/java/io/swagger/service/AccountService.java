package io.swagger.service;

import io.swagger.model.Account;

import java.util.List;

public interface AccountService {

     Account getAccountById(long id);
     List<Account> getAllAccounts();
    void createAccount(Account account);
    boolean isIbanTaken(String iban);
    Account softDeleteAccount(Long accountId);
    Account updateAccount(Long accountId,Account newAccount);

}
