package com.epam.training.ticketservice.managers;

import com.epam.training.ticketservice.services.screenings.ScreeningServiceImpl;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;

@ShellComponent
public class ScreeningManager {

    private final ScreeningServiceImpl screeningServiceImpl;
    private final UserManager userManager;

    public ScreeningManager(ScreeningServiceImpl screeningServiceImpl, UserManager userManager) {
        this.screeningServiceImpl = screeningServiceImpl;
        this.userManager = userManager;
    }

    @ShellMethod(key="create screening", value="Allow admin to create given screening")
    public void createScreening(String movieTitle, String roomName, String startTimeAsString){
        if(userManager.isAdminUser())
            screeningServiceImpl.addScreening(movieTitle, roomName, startTimeAsString);
    }

    @ShellMethod(key="delete screening", value="Allow admin to delete given screening")
    public void deleteScreening(String movieTitle, String roomName, String startTimeAsString){
        if(userManager.isAdminUser())
            screeningServiceImpl.deleteScreening(movieTitle, roomName, startTimeAsString);
    }

    @ShellMethod(key="list screenings", value="Allow anyone to list screenings")
    public void listScreenings(){
        boolean succesfullListing = screeningServiceImpl.listScreenings();
    }
}
