package io.swagger.service;

import io.swagger.exceptions.ApiRequestException;
import io.swagger.model.Account;
import io.swagger.model.ModifyAccountDTO;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import io.swagger.repository.AccountRepository;


import java.util.List;

@Service
public class AccountServiceImpl implements AccountService {

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private UserServiceImpl userService;

    public Account getAccountByIban(String iban) {
        if (!isIbanPresent(iban))
            throw new ApiRequestException("Iban is not present, please input a valid iban", HttpStatus.NOT_FOUND);

        return accountRepository.findByIBAN(iban);

    }

    //Change this to only update account type for EMPLOYEE
    public Account updateAccount(Account oldAccount, ModifyAccountDTO newAccount) {

        if (!isIbanPresent(oldAccount.getIBAN()))
            throw new ApiRequestException("Iban is not found, please input the correct iban to modify the account", HttpStatus.NOT_FOUND);

        oldAccount.setType(newAccount.getType());
        accountRepository.save(oldAccount);
        return oldAccount;
    }

    public Page<Account> getAllAccounts(Integer limit, Integer offset) {

        if (offset == null || offset < 0)
            throw new ApiRequestException("Offset can't be lower than 0 or Null.", HttpStatus.BAD_REQUEST);

        // !!!!!!!!!! <=?
        if (limit == null || limit <= 0)
            throw new ApiRequestException("Limit can't be lower or equal to 0 or Null", HttpStatus.BAD_REQUEST);

        Pageable pageable = PageRequest.of(offset, limit);
        return  accountRepository.findAll(pageable);
    }

    public void createAccount(Account account) {
        //IMPORTANT NOTE: I do not check if the user with the given id is present in the database because it is checked in userService
        //Layer which I get the user from. Therefore it would have been redundant for me to check it.
     //   Account should contain a balance more than absolute limit from the start
        if (account.getBalance().compareTo(account.getAbsoluteLimit()) == -1)
            throw new ApiRequestException("Balance can not be less than absoluteLimit", HttpStatus.BAD_REQUEST);

        //User can not be null
        if (account.getUser()==null)
            throw new ApiRequestException("User can not be null", HttpStatus.BAD_REQUEST);
        //Acount shouldn't be closed by default
        if (account.getStatus() == Account.StatusEnum.CLOSED)
            throw new ApiRequestException("Account must not be closed when it is created", HttpStatus.FORBIDDEN);

        //This would never happen but it's here in case of future code changes in iban generator
        if (isIbanPresent(account.getIBAN()))
            throw new ApiRequestException("Iban is already present in the database", HttpStatus.NOT_FOUND);


        accountRepository.save(account);

    }

    public boolean isIbanPresent(String iban) {
        return (accountRepository.findByIBAN(iban) != null);
    }


    public Account softDeleteAccount(String iban) {

        Account account = getAccountByIban(iban);

        if(account.getStatus()==Account.StatusEnum.CLOSED)
            throw new ApiRequestException("Account is already closed",HttpStatus.BAD_REQUEST);

        //Since deleting accounts are dangerous, Deleting accounts means simply closing them
        account.setStatus(Account.StatusEnum.CLOSED);
        accountRepository.save(account);

        return account;

    }


}
