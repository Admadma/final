package com.epam.training.ticketservice.rooms;

public interface RoomServiceInterface {

    void addRoom(String name, int rows, int cols);

     Iterable<Room> getRooms();

     Room findRoomByName(String name);

     void deleteRoom(String name);

     void updateRoom(String name, int rows, int cols);

     void listRooms();

}
