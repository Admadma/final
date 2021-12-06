package com.epam.training.ticketservice;

import com.epam.training.ticketservice.accounts.Account;
import com.epam.training.ticketservice.accounts.AccountController;
import com.epam.training.ticketservice.accounts.AccountService;
import com.epam.training.ticketservice.movies.MovieServiceImpl;
import com.epam.training.ticketservice.rooms.RoomServiceImpl;
import com.epam.training.ticketservice.screenings.ScreeningServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellOption;

import javax.annotation.PostConstruct;
import java.util.Objects;

@ShellComponent
public class ShellManager {

    @Autowired
    MovieServiceImpl movieServiceImpl;
    @Autowired
    RoomServiceImpl roomServiceImpl;
    @Autowired
    AccountService accountService;
    @Autowired
    ScreeningServiceImpl screeningserviceImpl;

    @Autowired
    AccountController accountController;

    private Account currentUser = null;


    @PostConstruct
    public void init() {
        accountController.addAccount("admin", "admin", true);
    }

    @ShellMethod(value="sign in", key="sign in privileged")
    public void signIn(String username, String password){
        currentUser = accountController.signIn(username, password);
        if(Objects.isNull(currentUser))
            System.out.println("Login failed due to incorrect credentials");
    }
    @ShellMethod(value="sign out", key="sign out")
    public void singOut(){
        currentUser = null;
    }
    @ShellMethod(value="Describe account", key="describe account")
    public void describeAccount(){
        if(Objects.isNull(currentUser))
            System.out.println("You are not signed in");
        else if(currentUser.isPrivileged())
            System.out.println("Signed in with privileged account '" + currentUser.getUsername() + "'");
        else
            System.out.println("Signed in with account '" + currentUser.getUsername() + "'");
    }


    @ShellMethod("create")
    public void create(@ShellOption(arity=4)String[] params){
        if(!Objects.isNull(currentUser) && currentUser.isPrivileged()){
            switch (params[0]) {
                case "movie":
                    movieServiceImpl.addMovie(params[1], params[2], Integer.valueOf(params[3]));
                    break;
                case "room":
                    roomServiceImpl.addRoom(params[1], Integer.valueOf(params[2]), Integer.valueOf(params[3]));
                    break;
                case "screening":
                    screeningserviceImpl.addScreening(params[1], params[2], params[3]);
                    break;
            }
        }
    }

    @ShellMethod("delete")
    public void delete(@ShellOption(arity=2)String[] params){
        if(!Objects.isNull(currentUser) && currentUser.isPrivileged()) {
            switch (params[0]) {
                case "movie":
                    movieServiceImpl.deleteMovie(params[1]);
                    break;
                case "room":
                    roomServiceImpl.deleteRoom(params[1]);
                    break;
            }
        }
    }

    @ShellMethod(value="Delete a screening", key="delete screening")
    public void deleteScreening(@ShellOption(arity=3)String[] params){
        screeningserviceImpl.deleteScreening(params[0], params[1], params[2]);
    }

    @ShellMethod("update")
    public void update(@ShellOption(arity=4)String[] params){
        if(!Objects.isNull(currentUser) && currentUser.isPrivileged()) {
            switch (params[0]) {
                case "movie":
                    movieServiceImpl.updateMovie(params[1], params[2], Integer.valueOf(params[3]));
                    break;
                case "room":
                    roomServiceImpl.updateRoom(params[1], Integer.valueOf(params[2]), Integer.valueOf(params[3]));
                    break;
            }
        }
    }

    @ShellMethod(value="listing", key="list")
    public void list(String target){
        switch (target){
            case "movies":
                movieServiceImpl.listMovies();
                break;
            case "rooms":
                roomServiceImpl.listRooms();
                break;
            case "screenings":
                screeningserviceImpl.listScreenings();
                break;
        }
    }
}
