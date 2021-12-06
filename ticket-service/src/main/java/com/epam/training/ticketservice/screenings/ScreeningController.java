package com.epam.training.ticketservice.screenings;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.Optional;

@Component
public class ScreeningController{

    @Autowired
    ScreeningRepository screeningRepository;

    public void addScreening(String movie, String room, Date startTime){
        Screening screening = new Screening();
        screening.setId(new ScreeningId(movie, room, startTime));
        screeningRepository.save(screening);
    }

    public Iterable<Screening> getScreenings(){
        return screeningRepository.findAll();
    }

    public void deleteScreening(String title, String room, Date startTime){
        screeningRepository.deleteScreeningById(new ScreeningId(title, room, startTime));
    }
}
