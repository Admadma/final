package com.epam.training.ticketservice.movies;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MovieService {

    @Autowired
    MovieController movieController;

    public void createMovie(String[] params){
        movieController.addMovie(params[1], params[2], Integer.valueOf(params[3]));
    }

    public void deleteMovie(String id){
        movieController.deleteMovie(id);
    }

    public void updateMovie(String[] params){
        movieController.addMovie(params[1], params[2], Integer.valueOf(params[3]));
    }

    public void listMovies(){
        Iterable<Movie> movies = movieController.getMovies();
        if(!movies.iterator().hasNext()){
            System.out.println("There are no movies at the moment");
        } else{
            movies.forEach(s -> System.out.println(s.getTitle() + " (" + s.getGenre() + ", " + s.getLength() + " minutes)"));
        }
    }
}
