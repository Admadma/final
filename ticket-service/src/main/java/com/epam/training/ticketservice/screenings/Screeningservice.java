package com.epam.training.ticketservice.screenings;

import com.epam.training.ticketservice.movies.Movie;
import com.epam.training.ticketservice.movies.MovieController;
import com.epam.training.ticketservice.rooms.Room;
import com.epam.training.ticketservice.rooms.RoomController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

@Service
public class Screeningservice {

    @Autowired
    ScreeningController screeningController;
    @Autowired
    MovieController movieController;
    @Autowired
    RoomController roomController;

    private boolean isTimeSlotFree(Date date, int length, String roomName){
        Calendar targetStartDate = Calendar.getInstance();
        Calendar targetEndDate = Calendar.getInstance();
        targetStartDate.setTime(date);
        targetEndDate.setTime(date);
        targetEndDate.add(Calendar.MINUTE, length);

        Calendar observedStartDate = Calendar.getInstance();
        Calendar observedEndDate = Calendar.getInstance();

        for(Screening s : screeningController.getScreenings()){
            if(!s.getId().getRoomName().equals(roomName))
                continue;

            observedStartDate.setTime(s.getId().getStartTime());
            observedEndDate.setTime(s.getId().getStartTime());
            observedEndDate.add(Calendar.MINUTE, movieController.findMovieByTitle(s.getId().getMovieTitle()).getLength());

            if(targetEndDate.compareTo(observedStartDate) > 0 && targetStartDate.compareTo(observedEndDate) < 0){
                System.out.println("There is an overlapping screening");
                return false;
            } else {
                targetEndDate.add(Calendar.MINUTE, 10);
                observedEndDate.add(Calendar.MINUTE, 10);
                if(targetEndDate.compareTo(observedStartDate) > 0 && targetStartDate.compareTo(observedEndDate) < 0) {
                    System.out.println("This would start in the break period after another screening in this room");
                    return false;
                }
                targetEndDate.add(Calendar.MINUTE, -10);
                observedEndDate.add(Calendar.MINUTE, -10);
            }
        }
        return true;
    }

    private Date convertStringToDate(String orignial){
        SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm");
        Date date = null;
        try {
            return sdf.parse(orignial);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }
    public void createScreening(String[] params){
        if(Objects.isNull(movieController.findMovieByTitle(params[1]))){
            System.out.println("Couldn't find a movie with that name");
            return;
        }
        if(Objects.isNull(roomController.findRoomByName(params[2]))){
            System.out.println("Couldn't find a room with that name");
            return;
        }

        Date date = convertStringToDate(params[3]);
        if(isTimeSlotFree(date, movieController.findMovieByTitle(params[1]).getLength(), params[2])){
            screeningController.addScreening(params[1], params[2], date);
        }
    }

    public void deleteScreening(String[] params){
        Date date = convertStringToDate(params[2]);
        screeningController.deleteScreening(params[0], params[1], date);
    }

    public void listScreenings(){
        Iterable<Screening> screenings = screeningController.getScreenings();
        ArrayList<Screening> screens = new ArrayList<Screening>(){};
        for(Screening s : screenings){
            screens.add(s);
        }

        if (screens.isEmpty()){
            System.out.println("There are no screenings");
        } else {
            Collections.reverse(screens);
            for (Screening s : screens) {
                Movie movie = movieController.findMovieByTitle(s.getId().getMovieTitle());
                Room room = roomController.findRoomByName(s.getId().getRoomName());
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
                String startTime = sdf.format(s.getId().getStartTime());

                //System.out.println(s.getId().getMovieTitle() + "  " + s.getId().getStartTime() + "  " + s.getId().getRoomName());
                System.out.println(movie.getTitle() + " ("
                        + movie.getGenre() + ", "
                        + movie.getLength() + " minutes), screened in room "
                        + room.getName() + ", at "
                        + startTime);
            }
        /*
        Collections.reverse(screens);
        if(!screenings.iterator().hasNext()){
            System.out.println("There are no screenings");
        } else{
            for(Screening s : screens){
                Movie movie = movieController.findMovieByTitle(s.getId().getMovieTitle());
                Room room = roomController.findRoomByName(s.getId().getRoomName());
                SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm");
                String startTime = sdf.format(s.getId().getStartTime());

                System.out.println(movie.getTitle() + " ("
                        + movie.getGenre() + ", "
                        + movie.getLength() + " minutes), screened in room "
                        + room.getName() + ", at "
                        + startTime);
            }
        }*/
        }
    }
}
