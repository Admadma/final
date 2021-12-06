package com.epam.training.ticketservice.screenings;

import java.util.Date;

public interface ScreeningServiceInterface {

    boolean isTimeSlotfree(Date date, int length, String roomName);

    void addScreening(String title, String room, String startTime);

    Iterable<Screening> getScreenings();

    void deleteScreening(String title, String room, String startTime);

    Date convertStringToDate(String original);

    void listScreenings();
}
