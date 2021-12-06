package com.epam.training.ticketservice.screenings;

import com.epam.training.ticketservice.movies.MovieRepository;
import com.epam.training.ticketservice.movies.MovieServiceImpl;
import com.epam.training.ticketservice.rooms.RoomRepository;
import com.epam.training.ticketservice.rooms.RoomServiceImpl;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ScreeningServiceImplTest {

    private static final String TITLE_1 = "Spirited Away";
    private static final String GENRE_1 = "animation";
    private static final int LENGTH_1 = 125;
    private final String ROOM_1 = "Pedersoli";
    private final String START_TIME_AS_STRING = "2021-03-14 16:00";

    private final MovieRepository movieRepository = mock(MovieRepository.class);
    private final MovieServiceImpl movieService = new MovieServiceImpl(movieRepository);

    private final RoomRepository roomRepository = mock(RoomRepository.class);
    private RoomServiceImpl roomService = new RoomServiceImpl(roomRepository);

    private final ScreeningRepository screeningRepository = mock(ScreeningRepository.class);
    private ScreeningServiceImpl underTest = new ScreeningServiceImpl(screeningRepository, movieService, roomService);

    @Test
    void isTimeSlotfreeShouldReturnTrueIfScreeningsDontIntersect() {
        underTest.isTimeSlotfree(underTest.convertStringToDate(START_TIME_AS_STRING), LENGTH_1, ROOM_1);
    }

    @Test
    void addScreeningShouldThrowNullPointerExceptionIfGivenMovieDoesntExists() {
        assertThrows(NullPointerException.class, () -> underTest.addScreening(TITLE_1, ROOM_1, START_TIME_AS_STRING));
    }

    @Test
    void addScreeningShouldThrowNullPointerExceptionIfGivenRoomDoesntExists() {
        assertThrows(NullPointerException.class, () -> underTest.addScreening(TITLE_1, ROOM_1, START_TIME_AS_STRING));
    }

    @Test
    void getScreeningsShouldReturnEmptyIterableWhenThereAreNoScreenings() {
        assertFalse(underTest.getScreenings().iterator().hasNext());
    }

    @Test
    void listScreeningsShouldThrowNullPointerExceptionIfThereAreNoScreenins() {
        assertThrows(NullPointerException.class, () -> underTest.listScreenings());
    }
}