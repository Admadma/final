package com.epam.training.ticketservice.accounts;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AccountController {

    @Autowired
    private AccountRepository accountRepository;

    public void addAccount(String username, String password, boolean privileged){
        Account account = new Account();
        account.setUsername(username);
        account.setPassword(password);
        account.setPrivileged(privileged);
        accountRepository.save(account);
    }

    public Account signIn(String username, String password){
        Account account = findAccountByUsername(username);
        if (password.equals(account.getPassword()))
            return account;
        else return null;
    }

    public Account findAccountByUsername(String username){
        return accountRepository.findAccountByUsername(username);
    }
}
