package com.epam.training.ticketservice.services.movies;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;

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
    //private static final Movie MOVIE_1 = new Movie(TITLE_1, GENRE_1, LENGTH_1);
    //private static final Movie MOVIE_2 = new Movie(TITLE_2, GENRE_2, LENGTH_2);
    //private static final List<Movie> MOVIES = List.of(MOVIE_1, MOVIE_2);



    private final MovieRepository movieRepository = mock(MovieRepository.class);
    private MovieServiceImpl underTest = new MovieServiceImpl(movieRepository);

    @BeforeEach
    public void setUp(){
        MockitoAnnotations.openMocks(this);
    }


    @Test
    public void addMovieShouldAddItToTheMovies(){
        //Given
        Movie MOVIE_1 = new Movie(TITLE_1, GENRE_1, LENGTH_1);
        when(movieRepository.save(MOVIE_1)).thenReturn(MOVIE_1);

        //When
        underTest.addMovie(MOVIE_1);

        //Then
        verify(movieRepository).save(MOVIE_1);
    }

    @Test
    public void getMoviesShouldReturnStoredMovies(){
        //Given
        Movie MOVIE_1 = new Movie(TITLE_1, GENRE_1, LENGTH_1);
        Movie MOVIE_2 = new Movie(TITLE_2, GENRE_2, LENGTH_2);
        when(movieRepository.findAll()).thenReturn(List.of(MOVIE_1, MOVIE_2));

        //When
        underTest.getMovies();

        //Then
        verify(movieRepository).findAll();
    }

    @Test
    public void getMoviesShouldReturnEmptyIfThereAreNoStoredMovies(){
        //Given
        when(movieRepository.findAll()).thenReturn(List.of());

        //When
        underTest.getMovies();

        //Then
        verify(movieRepository).findAll();
    }

    @Test
    public void findMovieByTitleShouldReturnNullIfThereAreNoMatchingMovies(){
        assertNull(underTest.findMovieByTitle(TITLE_2));
    }

    @Test
    public void findMovieByTitleShouldReturnTheMovieIfItExists(){
        //Given
        Movie MOVIE_1 = new Movie(TITLE_1, GENRE_1, LENGTH_1);
        when(movieRepository.findMovieByTitle(TITLE_1)).thenReturn(MOVIE_1);

        //When
        underTest.findMovieByTitle(TITLE_1);

        //Then
        verify(movieRepository).findMovieByTitle(TITLE_1);
    }

    @Test
    public void deleteMovieShouldRemoveIt(){
        //Given
        Movie MOVIE_1 = new Movie(TITLE_1, GENRE_1, LENGTH_1);
        when(movieRepository.save(MOVIE_1)).thenReturn(MOVIE_1);

        //When
        underTest.deleteMovie(TITLE_1);

        //Then
        verify(movieRepository).deleteById(TITLE_1);
    }

    @Test
    public void updateMovieShouldReturnTrueIfTheMovieExistsAndUpdated(){
        //Given
        Movie MOVIE_1 = new Movie(TITLE_1, GENRE_1, LENGTH_1);
        Movie MOVIE_2 = new Movie(TITLE_1, GENRE_2, LENGTH_2); //Movie with same title but different genre and length
        when(movieRepository.findMovieByTitle(TITLE_1)).thenReturn(MOVIE_1);

        //Then
        assertTrue(underTest.updateMovie(MOVIE_2.getTitle(), MOVIE_2.getGenre(), MOVIE_2.getLength()));
    }

    @Test
    public void updateMovieShouldReturnFalseIfMovieNotExists(){
        //Given
        Movie MOVIE_1 = new Movie(TITLE_1, GENRE_1, LENGTH_1);
        Movie MOVIE_2 = new Movie(TITLE_1, GENRE_2, LENGTH_2); //Movie with same title but different genre and length
        when(movieRepository.findMovieByTitle(TITLE_1)).thenReturn(null);

        //Then
        assertFalse(underTest.updateMovie(MOVIE_2.getTitle(), MOVIE_2.getGenre(), MOVIE_2.getLength()));
    }

}