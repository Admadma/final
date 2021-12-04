package com.epam.training.ticketservice.movies;

import org.springframework.data.repository.CrudRepository;

public interface MovieRepository extends CrudRepository<Movie, String> {

    Movie findMovieByTitle(String title);
}
