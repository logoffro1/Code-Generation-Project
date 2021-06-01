package io.swagger.service;

import com.sun.xml.bind.v2.model.core.ID;
import io.swagger.model.Account;
import io.swagger.model.Transaction;
import io.swagger.repository.AccountRepository;
import org.hibernate.tool.schema.internal.exec.ScriptTargetOutputToFile;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Service
public class AccountServiceImpl implements AccountService {

    @Autowired
    private AccountRepository accountRepository;

    public Account getAccountById(long id) {
        return accountRepository.findById(id).get();
    }

    public Account updateAccount(Long accountId,Account newAccount){
        Account oldAccount = accountRepository.findById(accountId).get();
        oldAccount.setBalance(newAccount.getBalance());
        oldAccount.setAbsoluteLimit(newAccount.getAbsoluteLimit());
        oldAccount.setStatus(newAccount.getStatus());
        oldAccount.setType(newAccount.getType());
        accountRepository.save(oldAccount);
        return oldAccount;
    }

    public List<Account> getAllAccounts() {
        return (List<Account>) accountRepository.findAll();
    }

    public void createAccount(Account account) {

        accountRepository.save(account);
    }

    public boolean isIbanTaken(String iban) {
        List<Account> listOfAccounts = getAllAccounts();


        for (Account account: listOfAccounts){
            if(account.getIBAN().equals(iban)){
                return true;
            }
        }
        return false;
    }


    public Account softDeleteAccount(Long accountId) {

       Account account= accountRepository.findById(accountId).get();

       //Since deleting accounts are dangerous, Deleting accounts means simply closing them
       account.setStatus(Account.StatusEnum.CLOSED);
       accountRepository.save(account);

       return account;

    }




}
