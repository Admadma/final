package com.epam.training.ticketservice.services.screenings;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import javax.persistence.Embeddable;
import java.io.Serializable;
import java.util.Date;
import java.util.Objects;

@Embeddable
@AllArgsConstructor
@NoArgsConstructor
public class ScreeningId implements Serializable {

    private String movieTitle;
    private String roomName;
    private Date startTime;

    public void setMovieTitle(String movieTitle) {
        this.movieTitle = movieTitle;
    }

    public void setRoomName(String roomName) {
        this.roomName = roomName;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public String getMovieTitle() {
        return movieTitle;
    }

    public String getRoomName() {
        return roomName;
    }

    public Date getStartTime() {
        return startTime;
    }
}
