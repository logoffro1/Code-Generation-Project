package io.swagger.service;

import io.swagger.exceptions.ApiRequestException;
import io.swagger.model.Account;
import io.swagger.repository.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AccountServiceImpl implements AccountService {

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private UserServiceImpl userService;

    public Account getAccountByIban(String iban) {
        return accountRepository.findByIBAN(iban);
    }

    //Change this to only update account type for EMPLOYEE
    public Account updateAccount(String iban, Account newAccount) {
        Account oldAccount = accountRepository.findByIBAN(iban);
        oldAccount.setType(newAccount.getType());
        accountRepository.save(oldAccount);
        return oldAccount;
    }

    public List<Account> getAllAccounts(Integer limit, Integer offset) {
        Pageable pageable = PageRequest.of(offset, limit);
        return accountRepository.findAll(pageable).getContent();
    }

    public void createAccount(Account account) {
        saveAccount(account);
    }

    private void saveAccount(Account account) {
        if (account.getBalance().compareTo(account.getAbsoluteLimit()) == -1)
            throw new ApiRequestException("Balance can not be less than absoluteLimit", HttpStatus.BAD_REQUEST);
              if(!userService.isUserPresent((int) account.getUser().getId()))
                  throw new ApiRequestException("User is not present in database",HttpStatus.FORBIDDEN);
        if (account.getStatus() == Account.StatusEnum.CLOSED)
            throw new ApiRequestException("Account must not be closed when it is created", HttpStatus.FORBIDDEN);


        accountRepository.save(account);

        //Add User/Employee error handling as well
    }

    public boolean isIbanPresent(String iban) {

        if (accountRepository.findByIBAN(iban) != null)
            return true;
        else
            return false;

    }


    public Account softDeleteAccount(String iban) {

        if (!isIbanPresent(iban))
            throw new ApiRequestException("Iban is not found", HttpStatus.BAD_REQUEST);

        Account account = accountRepository.findByIBAN(iban);
        System.out.println(account);

        //Since deleting accounts are dangerous, Deleting accounts means simply closing them
        account.setStatus(Account.StatusEnum.CLOSED);
        accountRepository.save(account);

        return account;

    }


}
