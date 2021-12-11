package com.epam.training.ticketservice.services.accounts;

public interface AccountServiceInterface {

    void addUser(Account account);

    Account signIn(String username, String password);

    Account findAccountByUsername(String username);
}
