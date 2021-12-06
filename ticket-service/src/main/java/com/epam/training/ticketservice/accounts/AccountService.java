package com.epam.training.ticketservice.accounts;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
public class AccountService {

    @Autowired
    AccountController accountController;

    public void describeAccount(Account account){
        //Account account = accountController.findAccountByUsername(username);
        if(Objects.isNull(account)){
            System.out.println("You are not signed in");
        } else if(account.isPrivileged()){
            System.out.println("full random text");
            //System.out.println("Signed in with privileged account '" + account.getUsername() + "'");
        } else {
            System.out.println("Signed in with account " + account.getUsername());
        }
    }
}
