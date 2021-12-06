package com.epam.training.ticketservice.screenings;

import javax.persistence.Embeddable;
import java.io.Serializable;
import java.util.Date;
import java.util.Objects;

@Embeddable
public class ScreeningId implements Serializable {

    private String movieTitle;
    private String roomName;
    private Date startTime;

    public ScreeningId(String movieTitle, String roomName, Date startTime) {
        this.movieTitle = movieTitle;
        this.roomName = roomName;
        this.startTime = startTime;
    }

    public ScreeningId() {

    }

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ScreeningId that = (ScreeningId) o;
        return Objects.equals(movieTitle, that.movieTitle) && Objects.equals(roomName, that.roomName) && Objects.equals(startTime, that.startTime);
    }

    @Override
    public int hashCode() {
        return Objects.hash(movieTitle, roomName, startTime);
    }
}
