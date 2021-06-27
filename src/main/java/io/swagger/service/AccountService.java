package io.swagger.service;

import io.swagger.model.Account;
import io.swagger.model.dtos.ModifyAccountDTO;
import org.springframework.data.domain.Page;

public interface AccountService {

     Account getAccountByIban(String iban);
     Page<Account> getAllAccounts(Integer limit, Integer offset);
    void createAccount(Account account);
    boolean isIbanPresent(String iban);
    Account softDeleteAccount(String iban);
    Account updateAccount(Account oldAccount, ModifyAccountDTO newAccount);

}
