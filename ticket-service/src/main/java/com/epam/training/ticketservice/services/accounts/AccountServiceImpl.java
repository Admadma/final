package com.epam.training.ticketservice.services.accounts;

import org.springframework.stereotype.Service;

@Service
public class AccountServiceImpl implements AccountServiceInterface{

    AccountRepository accountRepository;

    public AccountServiceImpl(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    @Override
    public void addUser(Account account) {
        accountRepository.save(account);
    }

    @Override
    public Account signIn(String username, String password) {
        Account account = findAccountByUsername(username);
        if (password.equals(account.getPassword()))
            return account;
        else return null;
    }

    @Override
    public Account findAccountByUsername(String username) {
        return accountRepository.findAccountByUsername(username);
    }
}
