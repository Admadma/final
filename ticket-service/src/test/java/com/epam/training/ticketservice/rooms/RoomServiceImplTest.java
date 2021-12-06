package com.epam.training.ticketservice.rooms;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class RoomServiceImplTest {

    private final String NAME1 = "Pedersoli";
    private final Room ROOM1 = new Room(NAME1, 10, 10);

    private final RoomRepository roomRepository = mock(RoomRepository.class);
    private RoomServiceImpl underTest = new RoomServiceImpl(roomRepository);

    @Test
    void addRoomShouldThrowNullPointerExceptionWhenNameIsNull(){
        assertThrows(NullPointerException.class, () -> underTest.addRoom(null, 10, 10));
    }

    @Test
    void getRoomsShouldReturnEmptyIterableWhenThereAreNoRooms(){
        assertFalse(underTest.getRooms().iterator().hasNext());
    }

    @Test
    void findRoomByNameShouldReturnNullIfTheRoomNotExists(){
        assertNull(underTest.findRoomByName("Nonexistent room"));
    }

    @Test
    void findRoomByTitleShouldReturnTheStoredMovieWithThatName(){

        when(roomRepository.save(ROOM1)).thenReturn(ROOM1);

        underTest.findRoomByName(NAME1);

        verify(roomRepository).findRoomByName(NAME1);
    }

    @Test
    void deleteRoomShouldDeleteIt(){
        when(roomRepository.save(ROOM1)).thenReturn(ROOM1);

        underTest.deleteRoom(NAME1);

        verify(roomRepository).deleteById(NAME1);
    }

    @Test
    void updateRoomShouldThrowNullPointerExceptionWhenRoomWasNotFound(){
        assertThrows(NullPointerException.class, () -> underTest.updateRoom(NAME1, 20, 20));
    }

    @Test
    void listRoomsShouldThrowNullPointerExceptionIfThereAreNoMovies(){
        assertThrows(NullPointerException.class, () -> underTest.listRooms());
    }
}