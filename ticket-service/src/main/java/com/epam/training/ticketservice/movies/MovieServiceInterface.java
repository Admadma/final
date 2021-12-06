package com.epam.training.ticketservice.movies;

public interface MovieServiceInterface {

    void addMovie(String title, String genre, int length);

    Iterable<Movie> getMovies();

    Movie findMovieByTitle(String title);

    void deleteMovie(String title);

    void updateMovie(String title, String genre, int length);

    void listMovies();
}