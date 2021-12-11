package com.epam.training.ticketservice.services.movies;

import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
public class MovieServiceImpl implements MovieServiceInterface {

    private MovieRepository movieRepository;

    public MovieServiceImpl(MovieRepository movieRepository) {
        this.movieRepository = movieRepository;
    }

    @Override
    public void addMovie(Movie movie) {
        movieRepository.save(movie);
    }

    @Override
    public Iterable<Movie> getMovies() {
        return movieRepository.findAll();
    }

    @Override
    public Movie findMovieByTitle(String title) {
        return movieRepository.findMovieByTitle(title);
    }

    @Override
    public void deleteMovie(String title) {
        movieRepository.deleteById(title);
    }

    @Override
    public boolean updateMovie(String title, String genre, int length){
        if(!Objects.isNull(findMovieByTitle(title))){
            addMovie(new Movie(title, genre, length));
            return true;
        }
        else {
            System.out.println("There are no movies titled " + title);
            return false;
        }

    }
}
