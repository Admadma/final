package com.epam.training.ticketservice.services.rooms;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
@NoArgsConstructor
@AllArgsConstructor
public class Room {

    @Id
    private String name;

    private int numbOfRows;
    private int numOfColumns;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getNumbOfRows() {
        return numbOfRows;
    }

    public void setNumbOfRows(int numbOfRows) {
        this.numbOfRows = numbOfRows;
    }

    public int getNumOfColumns() {
        return numOfColumns;
    }

    public void setNumOfColumns(int numOfColumns) {
        this.numOfColumns = numOfColumns;
    }
}
