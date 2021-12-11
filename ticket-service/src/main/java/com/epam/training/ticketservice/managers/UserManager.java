package com.epam.training.ticketservice.managers;

import com.epam.training.ticketservice.services.accounts.Account;
import com.epam.training.ticketservice.services.accounts.AccountServiceImpl;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;

import javax.annotation.PostConstruct;
import java.util.Objects;

@ShellComponent
public class UserManager {

    private final AccountServiceImpl accountServiceImpl;

    public UserManager(AccountServiceImpl accountServiceImpl) {
        this.accountServiceImpl = accountServiceImpl;
    }

    private Account currentUser = null;

    @PostConstruct
    public void init() {
        accountServiceImpl.addUser(new Account("admin", "admin", true));
    }

    @ShellMethod(value="sign in", key="sign in privileged")
    public void signIn(String username, String password){
        currentUser = accountServiceImpl.signIn(username, password);
        if(Objects.isNull(currentUser))
            System.out.println("Login failed due to incorrect credentials");
    }
    @ShellMethod(value="sign out", key="sign out")
    public void singOut(){
        currentUser = null;
    }
    @ShellMethod(value="Describe account", key="describe account")
    public String describeAccount(){
        if(Objects.isNull(currentUser))
            return "You are not signed in";
        else if(currentUser.isPrivileged())
            return "Signed in with privileged account '" + currentUser.getUsername() + "'";
        else
            return "Signed in with account '" + currentUser.getUsername() + "'";
    }

    public boolean isAdminUser(){
        return !Objects.isNull(currentUser) && currentUser.isPrivileged();
    }
}
