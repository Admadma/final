package com.epam.training.ticketservice.movies;

import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
public class MovieServiceImpl implements MovieServiceInterface{

    private MovieRepository movieRepository;

    public MovieServiceImpl(MovieRepository movieRepository) {
        this.movieRepository = movieRepository;
    }

    @Override
    public void addMovie(String title, String genre, int length) throws NullPointerException{
        if(Objects.isNull(title) || Objects.isNull(genre) || Objects.isNull(length)){
            throw new NullPointerException();
        }
        Movie movie = new Movie(title, genre, length);
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
    public void updateMovie(String title, String genre, int length) throws NullPointerException{
        if(!Objects.isNull(findMovieByTitle(title)))
            addMovie(title, genre, length);
        else {
            System.out.println("There are no movies titled " + title);
            throw new NullPointerException();
        }

    }

    @Override
    public void listMovies() throws NullPointerException{
        Iterable<Movie> movies = getMovies();
        if(!movies.iterator().hasNext()){
            System.out.println("There are no movies at the moment");
            throw new NullPointerException();
        } else{
            movies.forEach(s -> System.out.println(s.getTitle() + " (" + s.getGenre() + ", " + s.getLength() + " minutes)"));
        }
    }
}
