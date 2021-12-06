package com.epam.training.ticketservice.movies;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class MovieServiceImplTest {

    private static final String TITLE_1 = "Spirited Away";
    private static final String TITLE_2 = "Sátántangó";
    private static final String GENRE_1 = "animation";
    private static final String GENRE_2 = "drama";
    private static final int LENGTH_1 = 125;
    private static final int LENGTH_2 = 450;
    private static final Movie MOVIE_1 = new Movie(TITLE_1, GENRE_1, LENGTH_1);
    private static final Movie MOVIE_2 = new Movie(TITLE_2, GENRE_2, LENGTH_2);
    private static final List<Movie> MOVIES = List.of(MOVIE_1, MOVIE_2);


    private final MovieRepository movieRepository = mock(MovieRepository.class);
    private MovieServiceImpl underTest = new MovieServiceImpl(movieRepository);

    @BeforeEach
    public void setUp(){
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void addMovieShouldThrownNullPointerExceptionWhenTitleParameterIsNull() {
        assertThrows(NullPointerException.class, () -> underTest.addMovie(null, "animation", 0));
    }

    @Test
    void addMovieShouldThrownNullPointerExceptionWhenGenreParameterIsNull() {
        assertThrows(NullPointerException.class, () -> underTest.addMovie("Spirited Away", null, 0));
    }

    @Test
    void addMovieShouldThrownNullPointerExceptionWhenTitleAndGenreParameterIsNull() {
        assertThrows(NullPointerException.class, () -> underTest.addMovie(null, null, 0));
    }

    @Test
    void getMoviesShouldReturnEmptyIterableWhenThereAreNoMovies(){
        assertFalse(underTest.getMovies().iterator().hasNext());
    }

    @Test
    void findMovieByTitleShouldReturnNullIfTheMovieNotExists(){
        assertNull(underTest.findMovieByTitle("Nonexistent movie"));
    }

    @Test
    void findMovieByTitleShouldReturnTheStoredMovieWithThatName(){
        when(movieRepository.save(MOVIE_1)).thenReturn(MOVIE_1);

        underTest.findMovieByTitle(TITLE_1);

        verify(movieRepository).findMovieByTitle(TITLE_1);
    }

    @Test
    void deleteMovieShouldDeleteIt(){
        when(movieRepository.save(MOVIE_1)).thenReturn(MOVIE_1);

        underTest.deleteMovie(TITLE_1);

        verify(movieRepository).deleteById(TITLE_1);
    }

    @Test
    void updateMovieShouldThrowNullPointerExceptionWhenMovieWasNotFound(){
        assertThrows(NullPointerException.class, () -> underTest.updateMovie(TITLE_1, GENRE_2, LENGTH_2));
    }

    @Test
    void listMoviesThrowsNullPointerExceptionIfThereAreNoMovies(){
        assertThrows(NullPointerException.class, () -> underTest.listMovies());
    }
}