package com.epam.training.ticketservice.services.rooms;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class RoomServiceImplTest {

    private static final String NAME1 = "Pedersoli";
    private static final String NAME2 = "Girotti";
    private static final int rows1 = 10;
    private static final int rows2 = 20;
    private static final int cols1 = 15;
    private static final int cols2 = 25;

    private final RoomRepository roomRepository = mock(RoomRepository.class);
    private RoomServiceImpl underTest = new RoomServiceImpl(roomRepository);

    @BeforeEach
    public void setUp(){
        MockitoAnnotations.openMocks(this);
    }


    @Test
    public void addRoomShouldAddItToRooms(){
        //Given
        Room ROOM1 = new Room(NAME1, rows1, cols1);
        when(roomRepository.save(ROOM1)).thenReturn(ROOM1);

        //When
        underTest.addRoom(ROOM1);

        //Then
        verify(roomRepository).save(ROOM1);
    }

    @Test
    public void getRoomsShouldReturnStoredRooms(){
        //Given
        Room ROOM1 = new Room(NAME1, rows1, cols1);
        Room ROOM2 = new Room(NAME2, rows2, cols2);
        when(roomRepository.findAll()).thenReturn(List.of(ROOM1, ROOM2));

        //When
        underTest.getRooms();

        //Then
        verify(roomRepository).findAll();
    }

    @Test
    public void getRoomsShouldReturnEmptyIfThereAreNoStoredRooms(){
        //Given
        when(roomRepository.findAll()).thenReturn(List.of());

        //When
        underTest.getRooms();

        //Then
        verify(roomRepository).findAll();
    }

    @Test
    public void findRoomByNameShouldReturnNullIfThereAreNoMatchingRooms(){
        assertNull(underTest.findRoomByName(NAME1));
    }

    @Test
    public void findRoomByNameShouldReturnTheRoomIfItExists(){
        //Given
        Room ROOM1 = new Room(NAME1, rows1, cols1);
        when(roomRepository.findRoomByName(NAME1)).thenReturn(ROOM1);

        //When
        underTest.findRoomByName(NAME1);

        //Then
        verify(roomRepository).findRoomByName(NAME1);
    }

    @Test
    public void deleteRoomShouldRemoveIt(){
        //Given
        Room ROOM1 = new Room(NAME1, rows1, cols1);
        when(roomRepository.save(ROOM1)).thenReturn(ROOM1);

        //When
        underTest.deleteRoom(NAME1);

        //Then
        verify(roomRepository).deleteById(NAME1);
    }

    @Test
    public void updateRoomShouldReturnTrueIfTheRoomExistsAndUpdated(){
        //Given
        Room ROOM1 = new Room(NAME1, rows1, cols1);
        Room ROOM2 = new Room(NAME1, rows2, cols2); //Room with same name but different rows and cols
        when(roomRepository.findRoomByName(NAME1)).thenReturn(ROOM1);

        //Then
        assertTrue(underTest.updateRoom(ROOM2.getName(), ROOM2.getNumbOfRows(), ROOM2.getNumOfColumns()));
    }

    @Test
    public void updateRoomShouldReturnFalseIfRoomNotExists(){
        //Given
        Room ROOM1 = new Room(NAME1, rows1, cols1);
        Room ROOM2 = new Room(NAME1, rows2, cols2); //Room with same name but different rows and cols
        when(roomRepository.findRoomByName(NAME1)).thenReturn(null);

        //Then
        assertFalse(underTest.updateRoom(ROOM2.getName(), ROOM2.getNumbOfRows(), ROOM2.getNumOfColumns()));
    }
}