package io.swagger.service;

import io.swagger.exceptions.ApiRequestException;
import io.swagger.model.Account;
import io.swagger.model.dtos.ModifyAccountDTO;

import io.swagger.model.dtos.ResponseAccountDTO;
import io.swagger.util.LoggedInUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import io.swagger.repository.AccountRepository;


import java.util.ArrayList;
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

        Account account= accountRepository.findByIBAN(iban);

        //If user is an employee (OR) A customer is trying to get his/her own account, return the account
        if(LoggedInUser.isEmployee() || LoggedInUser.getUserId()==account.getUser().getId()){
            return account;
        }
        else{
            throw new ApiRequestException("Customers can not look for accounts that does not belong to them",HttpStatus.FORBIDDEN);
        }
    }

    //Transaction methods should be able to get accounts via ibans because of the sender and receiver account fields. This method is required for transaction posting.
    public Account getAccountByIbanForTransactions(String iban){
        if (!isIbanPresent(iban))
            throw new ApiRequestException("Iban is not present, please input a valid iban", HttpStatus.NOT_FOUND);
        return accountRepository.findByIBAN(iban);
    }


    //Change this to only update account type for EMPLOYEE
    public Account updateAccount(Account oldAccount, ModifyAccountDTO newAccount) {

        if(oldAccount.getIBAN().equals("NL01INHO00000001")){
            throw new ApiRequestException("Bank's own account can not be updated by employees",HttpStatus.FORBIDDEN);
        }
        if (!isIbanPresent(oldAccount.getIBAN()))
            throw new ApiRequestException("Iban is not found, please input the correct iban to modify the account", HttpStatus.NOT_FOUND);

        oldAccount.setType(newAccount.getType());
        accountRepository.save(oldAccount);
        return oldAccount;
    }

    public Page<Account> getAllAccounts(Integer limit, Integer offset) {

        if (offset == null || offset < 0)
            offset = 0; //default 0

        if (limit == null || limit < 0)
            limit = 15; //default limit

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

        return (accountRepository.findByIBAN(iban)!= null);
    }


    public Account softDeleteAccount(String iban) {

        if(iban.equals("NL01INHO00000001")){
            throw new ApiRequestException("Bank's own account can not be updated by employees",HttpStatus.FORBIDDEN);
        }
        Account account = getAccountByIban(iban);

        if(account.getStatus()==Account.StatusEnum.CLOSED)
            throw new ApiRequestException("Account is already closed",HttpStatus.BAD_REQUEST);

        //Since deleting accounts are dangerous, Deleting accounts means simply closing them
        account.setStatus(Account.StatusEnum.CLOSED);
        accountRepository.save(account);

        return account;

    }

    public ResponseAccountDTO convertToResponseAccountDTO(Account account){

        return new ResponseAccountDTO(account.getUser().getFirstName()+" "+account.getUser().getLastName(),account.getIBAN(),account.getStatus(),account.getType(),account.getBalance(),account.getAbsoluteLimit());

    }

    public Page<ResponseAccountDTO> convertPageToResponseAccountPage(Page<Account> accounts)
    {
        List<ResponseAccountDTO> listOfResponseAccountDtos= new ArrayList<>();
        for (Account acc:accounts
             ) {
                listOfResponseAccountDtos.add(convertToResponseAccountDTO(acc));
        }
       return new PageImpl<>(listOfResponseAccountDtos);
    }


}
