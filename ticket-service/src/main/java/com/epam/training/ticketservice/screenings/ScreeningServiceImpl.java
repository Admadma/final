package com.epam.training.ticketservice.screenings;

import com.epam.training.ticketservice.movies.Movie;
import com.epam.training.ticketservice.movies.MovieServiceImpl;
import com.epam.training.ticketservice.rooms.Room;
import com.epam.training.ticketservice.rooms.RoomServiceImpl;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

@Service
public class ScreeningServiceImpl implements ScreeningServiceInterface{

    private ScreeningRepository screeningRepository;
    private MovieServiceImpl movieServiceImpl;
    private RoomServiceImpl roomServiceImpl;

    public ScreeningServiceImpl(ScreeningRepository screeningRepository, MovieServiceImpl movieServiceImpl, RoomServiceImpl roomServiceImpl) {
        this.screeningRepository = screeningRepository;
        this.movieServiceImpl = movieServiceImpl;
        this.roomServiceImpl = roomServiceImpl;
    }

    @Override
    public boolean isTimeSlotfree(Date date, int length, String roomName) {
        Calendar targetStartDate = Calendar.getInstance();
        Calendar targetEndDate = Calendar.getInstance();
        targetStartDate.setTime(date);
        targetEndDate.setTime(date);
        targetEndDate.add(Calendar.MINUTE, length);

        Calendar observedStartDate = Calendar.getInstance();
        Calendar observedEndDate = Calendar.getInstance();

        for(Screening s : getScreenings()){
            if(!s.getId().getRoomName().equals(roomName))
                continue;

            observedStartDate.setTime(s.getId().getStartTime());
            observedEndDate.setTime(s.getId().getStartTime());
            observedEndDate.add(Calendar.MINUTE, movieServiceImpl.findMovieByTitle(s.getId().getMovieTitle()).getLength());

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

    @Override
    public void addScreening(String title, String room, String startTime) throws NullPointerException{
        if(Objects.isNull(movieServiceImpl.findMovieByTitle(title))){
            System.out.println("Couldn't find a movie with that name");
            throw new NullPointerException();
        }
        if(Objects.isNull(roomServiceImpl.findRoomByName(room))){
            System.out.println("Couldn't find a room with that name");
            throw new NullPointerException();
        }

        Date date = convertStringToDate(startTime);
        if(isTimeSlotfree(date, movieServiceImpl.findMovieByTitle(title).getLength(), room)){
            Screening screening = new Screening();
            screening.setId(new ScreeningId(title, room, date));
            screeningRepository.save(screening);
        }
    }

    @Override
    public Iterable<Screening> getScreenings() {
        return screeningRepository.findAll();

    }

    @Override
    public void deleteScreening(String title, String room, String startTime) {
        screeningRepository.deleteScreeningById(new ScreeningId(title, room, convertStringToDate(startTime)));
    }

    @Override
    public Date convertStringToDate(String original) {
        SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm");
        Date date = null;
        try {
            return sdf.parse(original);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public void listScreenings() throws NullPointerException{
        Iterable<Screening> screenings = getScreenings();
        ArrayList<Screening> screens = new ArrayList<Screening>(){};
        for(Screening s : screenings){
            screens.add(s);
        }

        if (screens.isEmpty()){
            System.out.println("There are no screenings");
            throw new NullPointerException();
        } else {
            Collections.reverse(screens);
            for (Screening s : screens) {
                Movie movie = movieServiceImpl.findMovieByTitle(s.getId().getMovieTitle());
                Room room = roomServiceImpl.findRoomByName(s.getId().getRoomName());
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
                String startTime = sdf.format(s.getId().getStartTime());

                //System.out.println(s.getId().getMovieTitle() + "  " + s.getId().getStartTime() + "  " + s.getId().getRoomName());
                System.out.println(movie.getTitle() + " ("
                        + movie.getGenre() + ", "
                        + movie.getLength() + " minutes), screened in room "
                        + room.getName() + ", at "
                        + startTime);
            }
        }
    }
}
