package com.epam.training.ticketservice.services.screenings;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;

@Entity
@NoArgsConstructor
@AllArgsConstructor
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
