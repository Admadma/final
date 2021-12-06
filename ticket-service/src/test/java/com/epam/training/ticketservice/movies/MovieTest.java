package com.epam.training.ticketservice.movies;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class MovieTest {

    private static final String TITLE = "Spirited Away";
    private static final String GENRE = "animateion";
    private static final int LENGTH = 125;
    private static final Movie MOVIE_1 = new Movie(TITLE, GENRE, LENGTH);

    @Test
    void getTitle() {
        assertEquals(TITLE, MOVIE_1.getTitle());
    }

    @Test
    void setTitle() {
        String newTitle = "New title";
        //MOVIE_1.setTitle(newTitle);
        //assertEquals(newTitle, MOVIE_1.getTitle());
    }

    @Test
    void getGenre() {
    }

    @Test
    void getLength() {
    }

}