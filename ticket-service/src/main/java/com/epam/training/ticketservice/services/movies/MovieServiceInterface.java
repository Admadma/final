package com.epam.training.ticketservice.services.movies;

public interface MovieServiceInterface {

    void addMovie(Movie movie);

    Iterable<Movie> getMovies();

    Movie findMovieByTitle(String title);

    void deleteMovie(String title);

    boolean updateMovie(String title, String genre, int length);
}