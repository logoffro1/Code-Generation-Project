package io.swagger.service;

import io.swagger.model.Account;
import io.swagger.model.ModifyAccountDTO;
import org.springframework.data.domain.Page;

import java.util.List;

public interface AccountService {

     Account getAccountByIban(String iban);
     Page<Account> getAllAccounts(Integer limit, Integer offset);
    void createAccount(Account account);
    boolean isIbanPresent(String iban);
    Account softDeleteAccount(String iban);
    Account updateAccount(Account oldAccount, ModifyAccountDTO newAccount);

}
