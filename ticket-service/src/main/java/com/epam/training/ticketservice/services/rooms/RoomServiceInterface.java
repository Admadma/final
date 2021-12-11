package com.epam.training.ticketservice.services.rooms;

public interface RoomServiceInterface {

    void addRoom(Room room);

    Iterable<Room> getRooms();

    Room findRoomByName(String name);

    void deleteRoom(String name);

    boolean updateRoom(String name, int rows, int cols);

}
