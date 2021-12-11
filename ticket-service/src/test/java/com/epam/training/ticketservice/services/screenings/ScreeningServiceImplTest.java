package com.epam.training.ticketservice.services.screenings;

import com.epam.training.ticketservice.services.movies.Movie;
import com.epam.training.ticketservice.services.movies.MovieRepository;
import com.epam.training.ticketservice.services.movies.MovieServiceImpl;
import com.epam.training.ticketservice.services.rooms.Room;
import com.epam.training.ticketservice.services.rooms.RoomRepository;
import com.epam.training.ticketservice.services.rooms.RoomServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ScreeningServiceImplTest {

    private static final String TITLE_1 = "Spirited Away";
    private static final String TITLE_2 = "Sátántangó";
    private static final String GENRE_1 = "animation";
    private static final String GENRE_2 = "drama";
    private static final int LENGTH_1 = 60;
    private static final int LENGTH_2 = 120;

    private static final String ROOMNAME1 = "Pedersoli";
    private static final String ROOMNAME2 = "Girotti";
    private static final int rows1 = 10;
    private static final int rows2 = 20;
    private static final int cols1 = 15;
    private static final int cols2 = 25;

    private String NEW_SCREENING_START;
    private String EXISTING_SCREENING_START;
    private String EXISTING_SCREENING_START_2;

    private final MovieRepository movieRepository = mock(MovieRepository.class);
    private final RoomRepository roomRepository = mock(RoomRepository.class);
    private final ScreeningRepository screeningRepository = mock(ScreeningRepository.class);

    private final MovieServiceImpl movieService = new MovieServiceImpl(movieRepository);
    private final RoomServiceImpl roomService = new RoomServiceImpl(roomRepository);
    private final ScreeningServiceImpl underTest = new ScreeningServiceImpl(screeningRepository, movieService, roomService);

    @BeforeEach
    public void setUp(){
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void convertStringToDateShouldReturnNullWhenGivenInvalidInput(){
        assertNull(underTest.convertStringToDate("not a date"));
    }

    @Test
    public void getScreeningsShouldReturnStoredScreenings(){
        //Given
        NEW_SCREENING_START = "2021-03-14 16:00";
        EXISTING_SCREENING_START ="2021-03-14 18:00";
        Movie MOVIE_1 = new Movie(TITLE_1, GENRE_1, LENGTH_1);
        Room ROOM_1 = new Room(ROOMNAME1, rows1, cols1);
        ScreeningId SCREENINGID_1 = new ScreeningId(TITLE_1, ROOMNAME1, underTest.convertStringToDate(NEW_SCREENING_START));
        Screening SCREENING_1 = new Screening(SCREENINGID_1);
        ScreeningId SCREENINGID_2 = new ScreeningId(TITLE_1, ROOMNAME1, underTest.convertStringToDate(EXISTING_SCREENING_START));
        Screening SCREENING_2 = new Screening(SCREENINGID_2);

        when(screeningRepository.findAll()).thenReturn(List.of(SCREENING_1, SCREENING_2));

        //When
        underTest.getScreenings();

        //Then
        verify(screeningRepository).findAll();
    }

    @Test
    public void getScreeningsShouldReturnEmptyIfThereAreNoScreenings(){
        //Given
        when(screeningRepository.findAll()).thenReturn(List.of());

        //When
        underTest.getScreenings();

        //Then
        verify(screeningRepository).findAll();

    }

    //ez a jo
    @Test
    public void isTimeSlotFreeShouldReturnFalseIfNewMovieStartsBeforeExistingMovieStartsAndNewMovieEndsAfterExistingMovieStarts(){
        //Given
        NEW_SCREENING_START = "2021-03-14 16:00";
        EXISTING_SCREENING_START ="2021-03-14 16:30";
        Movie MOVIE_1 = new Movie(TITLE_1, GENRE_1, LENGTH_1);

        ScreeningId SCREENINGID_1 = new ScreeningId(TITLE_1, ROOMNAME1, underTest.convertStringToDate(EXISTING_SCREENING_START));
        Screening SCREENING_1 = new Screening(SCREENINGID_1);
        ScreeningId SCREENINGID_2 = new ScreeningId(TITLE_1, ROOMNAME1, underTest.convertStringToDate(NEW_SCREENING_START));
        Screening SCREENING_2 = new Screening(SCREENINGID_2);

        when(movieService.findMovieByTitle(SCREENING_1.getId().getMovieTitle())).thenReturn(MOVIE_1);
        when(movieService.findMovieByTitle(SCREENING_2.getId().getMovieTitle())).thenReturn(MOVIE_1);
        when(screeningRepository.findAll()).thenReturn(List.of(SCREENING_1));

        //Then
        assertFalse(underTest.isTimeSlotFree(underTest.convertStringToDate(NEW_SCREENING_START),
                                            movieService.findMovieByTitle(SCREENING_2.getId().getMovieTitle()).getLength(),
                                            SCREENING_2.getId().getRoomName()));
    }


    @Test
    public void isTimeSlotFreeShouldReturnFalseIfNewMovieStartsBeforeExistingMovieEndsAndNewMovieEndsAfterExistingMovieEnds(){
        //Given
        NEW_SCREENING_START = "2021-03-14 16:30";
        EXISTING_SCREENING_START ="2021-03-14 16:00";
        Movie MOVIE_1 = new Movie(TITLE_1, GENRE_1, LENGTH_1);

        ScreeningId SCREENINGID_1 = new ScreeningId(TITLE_1, ROOMNAME1, underTest.convertStringToDate(EXISTING_SCREENING_START));
        Screening SCREENING_1 = new Screening(SCREENINGID_1);
        ScreeningId SCREENINGID_2 = new ScreeningId(TITLE_1, ROOMNAME1, underTest.convertStringToDate(NEW_SCREENING_START));
        Screening SCREENING_2 = new Screening(SCREENINGID_2);

        when(movieService.findMovieByTitle(SCREENING_1.getId().getMovieTitle())).thenReturn(MOVIE_1);
        when(movieService.findMovieByTitle(SCREENING_2.getId().getMovieTitle())).thenReturn(MOVIE_1);
        when(screeningRepository.findAll()).thenReturn(List.of(SCREENING_1));

        //Then
        assertFalse(underTest.isTimeSlotFree(underTest.convertStringToDate(NEW_SCREENING_START),
                movieService.findMovieByTitle(SCREENING_2.getId().getMovieTitle()).getLength(),
                SCREENING_2.getId().getRoomName()));
    }

    @Test
    public void isTimeSlotFreeShouldReturnFalseIfNewMovieWouldStartInTheBreakPeriodOfAnExistingMovie(){
        //Given
        NEW_SCREENING_START = "2021-03-14 17:00";
        EXISTING_SCREENING_START ="2021-03-14 16:00";
        Movie MOVIE_1 = new Movie(TITLE_1, GENRE_1, LENGTH_1);

        ScreeningId SCREENINGID_1 = new ScreeningId(TITLE_1, ROOMNAME1, underTest.convertStringToDate(EXISTING_SCREENING_START));
        Screening SCREENING_1 = new Screening(SCREENINGID_1);
        ScreeningId SCREENINGID_2 = new ScreeningId(TITLE_1, ROOMNAME1, underTest.convertStringToDate(NEW_SCREENING_START));
        Screening SCREENING_2 = new Screening(SCREENINGID_2);

        when(movieService.findMovieByTitle(SCREENING_1.getId().getMovieTitle())).thenReturn(MOVIE_1);
        when(movieService.findMovieByTitle(SCREENING_2.getId().getMovieTitle())).thenReturn(MOVIE_1);
        when(screeningRepository.findAll()).thenReturn(List.of(SCREENING_1));


        //Then
        assertFalse(underTest.isTimeSlotFree(underTest.convertStringToDate(NEW_SCREENING_START),
                movieService.findMovieByTitle(SCREENING_2.getId().getMovieTitle()).getLength(),
                SCREENING_2.getId().getRoomName()));
    }


    @Test
    public void isTimeSlotFreeShouldReturnFalseIfTheBreakPeriodOfTheNewMovieWouldCollideWithAnExistingMovie(){
        //Given
        NEW_SCREENING_START = "2021-03-14 15:00";
        EXISTING_SCREENING_START ="2021-03-14 16:00";
        Movie MOVIE_1 = new Movie(TITLE_1, GENRE_1, LENGTH_1);

        ScreeningId SCREENINGID_1 = new ScreeningId(TITLE_1, ROOMNAME1, underTest.convertStringToDate(EXISTING_SCREENING_START));
        Screening SCREENING_1 = new Screening(SCREENINGID_1);
        ScreeningId SCREENINGID_2 = new ScreeningId(TITLE_1, ROOMNAME1, underTest.convertStringToDate(NEW_SCREENING_START));
        Screening SCREENING_2 = new Screening(SCREENINGID_2);

        when(movieService.findMovieByTitle(SCREENING_1.getId().getMovieTitle())).thenReturn(MOVIE_1);
        when(movieService.findMovieByTitle(SCREENING_2.getId().getMovieTitle())).thenReturn(MOVIE_1);
        when(screeningRepository.findAll()).thenReturn(List.of(SCREENING_1));


        //Then
        assertFalse(underTest.isTimeSlotFree(underTest.convertStringToDate(NEW_SCREENING_START),
                movieService.findMovieByTitle(SCREENING_2.getId().getMovieTitle()).getLength(),
                SCREENING_2.getId().getRoomName()));
    }

    @Test
    public void isTimeSlotFreeShouldReturnTrueIfItOnlyCollidesWithScreeningInDifferentRoom(){
        //Given
        NEW_SCREENING_START = "2021-03-14 16:00";
        EXISTING_SCREENING_START ="2021-03-14 16:00";
        Movie MOVIE_1 = new Movie(TITLE_1, GENRE_1, LENGTH_1);

        ScreeningId SCREENINGID_1 = new ScreeningId(TITLE_1, ROOMNAME2, underTest.convertStringToDate(EXISTING_SCREENING_START));
        Screening SCREENING_1 = new Screening(SCREENINGID_1);
        ScreeningId SCREENINGID_2 = new ScreeningId(TITLE_1, ROOMNAME1, underTest.convertStringToDate(NEW_SCREENING_START));
        Screening SCREENING_2 = new Screening(SCREENINGID_2);

        when(movieService.findMovieByTitle(SCREENING_1.getId().getMovieTitle())).thenReturn(MOVIE_1);
        when(movieService.findMovieByTitle(SCREENING_2.getId().getMovieTitle())).thenReturn(MOVIE_1);
        when(screeningRepository.findAll()).thenReturn(List.of(SCREENING_1));


        //Then
        assertTrue(underTest.isTimeSlotFree(underTest.convertStringToDate(NEW_SCREENING_START),
                movieService.findMovieByTitle(SCREENING_2.getId().getMovieTitle()).getLength(),
                SCREENING_2.getId().getRoomName()));
    }

    @Test
    public void isTimeSlotFreeShouldReturnTrueIfThereAreNoCollisionsInCurrentRoom(){
        //Given
        NEW_SCREENING_START = "2021-03-14 14:00";
        EXISTING_SCREENING_START ="2021-03-14 16:00";
        EXISTING_SCREENING_START_2 ="2021-03-14 18:00";
        Movie MOVIE_1 = new Movie(TITLE_1, GENRE_1, LENGTH_1);

        ScreeningId SCREENINGID_1 = new ScreeningId(TITLE_1, ROOMNAME1, underTest.convertStringToDate(EXISTING_SCREENING_START));
        Screening SCREENING_1 = new Screening(SCREENINGID_1);
        ScreeningId SCREENINGID_2 = new ScreeningId(TITLE_1, ROOMNAME1, underTest.convertStringToDate(NEW_SCREENING_START));
        Screening SCREENING_2 = new Screening(SCREENINGID_2);
        ScreeningId SCREENINGID_3 = new ScreeningId(TITLE_1, ROOMNAME1, underTest.convertStringToDate(EXISTING_SCREENING_START_2));
        Screening SCREENING_3 = new Screening(SCREENINGID_3);

        when(movieService.findMovieByTitle(SCREENING_1.getId().getMovieTitle())).thenReturn(MOVIE_1);
        when(movieService.findMovieByTitle(SCREENING_2.getId().getMovieTitle())).thenReturn(MOVIE_1);
        when(movieService.findMovieByTitle(SCREENING_3.getId().getMovieTitle())).thenReturn(MOVIE_1);
        when(screeningRepository.findAll()).thenReturn(List.of(SCREENING_1, SCREENING_3));


        //Then
        assertTrue(underTest.isTimeSlotFree(underTest.convertStringToDate(NEW_SCREENING_START),
                movieService.findMovieByTitle(SCREENING_2.getId().getMovieTitle()).getLength(),
                SCREENING_2.getId().getRoomName()));
    }

    @Test
    public void addScreeningShouldReturnFalseIfThereAreNoMatchingMovies(){
        //Given
        NEW_SCREENING_START = "2021-03-14 14:00";

        when(movieService.findMovieByTitle(TITLE_2)).thenReturn(null);

        //Then
        assertFalse(underTest.addScreening(TITLE_2, ROOMNAME2, NEW_SCREENING_START));
    }

    @Test
    public void addScreeningShouldReturnFalseIfThereAreNoMatchingRooms(){
        //Given
        NEW_SCREENING_START = "2021-03-14 14:00";
        Movie MOVIE_2 = new Movie(TITLE_2, GENRE_2, LENGTH_2);

        when(movieService.findMovieByTitle(TITLE_2)).thenReturn(MOVIE_2);
        when(roomService.findRoomByName(ROOMNAME2)).thenReturn(null);

        //Then
        assertFalse(underTest.addScreening(TITLE_2, ROOMNAME2, NEW_SCREENING_START));
    }

    @Test
    public void addScreeningShouldReturnTrueIfTimeSlotIsFree(){
        //Given
        NEW_SCREENING_START = "2021-03-14 14:00";
        Movie MOVIE_2 = new Movie(TITLE_2, GENRE_2, LENGTH_2);
        Room ROOM_2 = new Room(ROOMNAME2, rows2, cols2);

        when(movieService.findMovieByTitle(TITLE_2)).thenReturn(MOVIE_2);
        when(roomService.findRoomByName(ROOMNAME2)).thenReturn(ROOM_2);

       //Then
        assertTrue(underTest.addScreening(TITLE_2, ROOMNAME2, NEW_SCREENING_START));
    }

    @Test
    public void listScreeningsShouldReturnFalseIfThereAreNoScreeningsToList(){
        //Given
        when(screeningRepository.findAll()).thenReturn(List.of());

        //When
        underTest.listScreenings();

        //Then
        verify(screeningRepository).findAll();
    }

    @Test
    public void listScreeningsShouldReturnTrueAfterPrintingAllStoredScreenings(){
        //Given
        EXISTING_SCREENING_START = "2021-03-14 14:00";
        Movie MOVIE_1 = new Movie(TITLE_1, GENRE_1, LENGTH_1);
        Room ROOM_1 = new Room(ROOMNAME1, rows1, cols1);
        ScreeningId SCREENINGID_1 = new ScreeningId(TITLE_1, ROOMNAME1, underTest.convertStringToDate(EXISTING_SCREENING_START));
        Screening SCREENING_1 = new Screening(SCREENINGID_1);

        when(screeningRepository.findAll()).thenReturn(List.of(SCREENING_1));
        when(movieService.findMovieByTitle(TITLE_1)).thenReturn(MOVIE_1);
        when(roomService.findRoomByName(ROOMNAME1)).thenReturn(ROOM_1);

        //When
        underTest.listScreenings();

        //Then
        verify(screeningRepository).findAll();
    }



}