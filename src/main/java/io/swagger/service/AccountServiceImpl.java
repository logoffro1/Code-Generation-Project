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
        if (!isIbanPresent(iban))
            throw new ApiRequestException("Iban is not present, please input a valid iban", HttpStatus.NOT_FOUND);

        return accountRepository.findByIBAN(iban);

    }

    //Change this to only update account type for EMPLOYEE
    public Account updateAccount(String iban, Account newAccount) {

        if (!isIbanPresent(iban))
            throw new ApiRequestException("Iban is not found, please input the correct iban to modify the account", HttpStatus.NOT_FOUND);


        Account oldAccount = accountRepository.findByIBAN(iban);
        oldAccount.setType(newAccount.getType());
        accountRepository.save(oldAccount);
        return oldAccount;
    }

    public List<Account> getAllAccounts(Integer limit, Integer offset) {

        if (offset == null || offset < 0)
            throw new ApiRequestException("Offset can't be lower than 0 or NULL.", HttpStatus.BAD_REQUEST);

        if (limit == null || limit < 0)
            throw new ApiRequestException("Limit can't be lower than 0 or NULL.", HttpStatus.BAD_REQUEST);

        Pageable pageable = PageRequest.of(offset, limit);
        return accountRepository.findAll(pageable).getContent();
    }

    public void createAccount(Account account) {
        //Account should contain a balance more than absolute limit from the start
        if (account.getBalance().compareTo(account.getAbsoluteLimit()) == -1)
            throw new ApiRequestException("Balance can not be less than absoluteLimit", HttpStatus.BAD_REQUEST);

        //There should be a present user in the database
        if (!userService.isUserPresent((int) account.getUser().getId()))
            throw new ApiRequestException("User is not present in database", HttpStatus.FORBIDDEN);
        //Acount shouldn't be closed by default
        if (account.getStatus() == Account.StatusEnum.CLOSED)
            throw new ApiRequestException("Account must not be closed when it is created", HttpStatus.FORBIDDEN);

        //This would never happen but it's here in case of future code changes in iban generator
        if (isIbanPresent(account.getIBAN()))
            throw new ApiRequestException("Iban is already present in the database", HttpStatus.NOT_FOUND);


        accountRepository.save(account);

    }

    public boolean isIbanPresent(String iban) {

        if (accountRepository.findByIBAN(iban) != null)
            return true;
        else
            return false;

    }


    public Account softDeleteAccount(String iban) {

        if (!isIbanPresent(iban))
            throw new ApiRequestException("Iban is not found", HttpStatus.NOT_FOUND);

        Account account = accountRepository.findByIBAN(iban);

        if(account.getStatus()==Account.StatusEnum.CLOSED)
            throw new ApiRequestException("Account is already closed",HttpStatus.BAD_REQUEST);

        //Since deleting accounts are dangerous, Deleting accounts means simply closing them
        account.setStatus(Account.StatusEnum.CLOSED);
        accountRepository.save(account);

        return account;

    }


}
