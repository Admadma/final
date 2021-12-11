package com.epam.training.ticketservice.services.movies;

import org.springframework.data.repository.CrudRepository;

public interface MovieRepository extends CrudRepository<Movie, String> {

    Movie findMovieByTitle(String title);
}
