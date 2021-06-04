package io.swagger.service;

import io.swagger.model.Account;

import java.util.List;

public interface AccountService {

     Account getAccountByIban(String iban);
     List<Account> getAllAccounts(Integer limit, Integer offset);
    void createAccount(Account account);
    boolean isIbanPresent(String iban);
    Account softDeleteAccount(String iban);
    Account updateAccount(String iban,Account newAccount);

}
