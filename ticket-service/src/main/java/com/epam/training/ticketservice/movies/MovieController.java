package com.epam.training.ticketservice.movies;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MovieController {

    @Autowired
    private MovieRepository movieRepository;

    public void addMovie(String title, String genre, int length){
        Movie movie = new Movie();
        movie.setTitle(title);
        movie.setGenre(genre);
        movie.setLength(length);
        movieRepository.save(movie);
    }

    public Iterable<Movie> getMovies(){
        return movieRepository.findAll();
    }

    public Movie findMovieByTitle(String title){
        return movieRepository.findMovieByTitle(title);
    }

    public void deleteMovie(String id){
        movieRepository.deleteById(id);
    }
}
