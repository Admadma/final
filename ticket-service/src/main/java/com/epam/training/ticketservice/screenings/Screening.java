package com.epam.training.ticketservice.screenings;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;

@Entity
public class Screening {

    @EmbeddedId
    private ScreeningId id;

    public ScreeningId getId() {
        return id;
    }

    public void setId(ScreeningId id) {
        this.id = id;
    }
}
