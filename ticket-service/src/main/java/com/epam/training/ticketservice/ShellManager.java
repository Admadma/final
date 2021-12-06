package com.epam.training.ticketservice;

import com.epam.training.ticketservice.accounts.Account;
import com.epam.training.ticketservice.accounts.AccountController;
import com.epam.training.ticketservice.accounts.AccountService;
import com.epam.training.ticketservice.movies.MovieService;
import com.epam.training.ticketservice.rooms.RoomService;
import com.epam.training.ticketservice.screenings.Screeningservice;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellOption;

import javax.annotation.PostConstruct;
import java.util.Objects;


@ShellComponent
public class ShellManager {

    @Autowired
    MovieService movieService;
    @Autowired
    RoomService roomService;
    @Autowired
    AccountService accountService;
    @Autowired
    Screeningservice screeningservice;

    @Autowired
    AccountController accountController;

    @Autowired
    ConfigurableApplicationContext context;

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
                    movieService.createMovie(params);
                    break;
                case "room":
                    roomService.createRoom(params);
                    break;
                case "screening":
                    screeningservice.createScreening(params);
                    break;
            }
        }
    }

    @ShellMethod("delete")
    public void delete(@ShellOption(arity=2)String[] params){
        if(!Objects.isNull(currentUser) && currentUser.isPrivileged()) {
            switch (params[0]) {
                case "movie":
                    movieService.deleteMovie(params[1]);
                    break;
                case "room":
                    roomService.deleteRoom(params[1]);
                    break;
            }
        }
    }

    @ShellMethod(value="Delete a screening", key="delete screening")
    public void deleteScreening(@ShellOption(arity=3)String[] params){
        screeningservice.deleteScreening(params);
    }

    @ShellMethod("update")
    public void update(@ShellOption(arity=4)String[] params){
        if(!Objects.isNull(currentUser) && currentUser.isPrivileged()) {
            switch (params[0]) {
                case "movie":
                    movieService.updateMovie(params);
                    break;
                case "room":
                    roomService.updateRoom(params);
                    break;
            }
        }
    }

    @ShellMethod(value="listing", key="list")
    public void list(String target){
        switch (target){
            case "movies":
                movieService.listMovies();
                break;
            case "rooms":
                roomService.listRooms();
                break;
            case "screenings":
                screeningservice.listScreenings();
                break;
        }
    }
}
