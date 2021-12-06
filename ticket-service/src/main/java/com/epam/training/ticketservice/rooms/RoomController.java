package com.epam.training.ticketservice.rooms;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class RoomController {

    @Autowired
    RoomRepository roomRepository;

    public void addRoom(String name, int rows, int cols){
        Room room = new Room();
        room.setName(name);
        room.setNumbOfRows(rows);
        room.setNumOfColumns(cols);
        roomRepository.save(room);
    }

    public Iterable<Room> getRooms(){
        return roomRepository.findAll();
    }

    public Room findRoomByName(String name){
        return roomRepository.findRoomByName(name);
    }

    public void deleteRoom(String id){
        roomRepository.deleteById(id);
    }
}
