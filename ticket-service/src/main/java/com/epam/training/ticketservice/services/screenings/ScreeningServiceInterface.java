package com.epam.training.ticketservice.services.screenings;

import java.util.Date;

public interface ScreeningServiceInterface {

    boolean isTimeSlotFree(Date date, int length, String roomName);

    boolean addScreening(String title, String room, String startTime);

    Iterable<Screening> getScreenings();

    void deleteScreening(String title, String room, String startTime);

    Date convertStringToDate(String original);

    boolean listScreenings();
}
