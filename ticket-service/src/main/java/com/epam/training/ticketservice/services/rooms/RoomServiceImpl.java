package com.epam.training.ticketservice.services.rooms;

import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
public class RoomServiceImpl implements RoomServiceInterface {

    private RoomRepository roomRepository;

    public RoomServiceImpl(RoomRepository roomRepository) {
        this.roomRepository = roomRepository;
    }

    @Override
    public void addRoom(Room room) throws NullPointerException{
        roomRepository.save(room);
    }

    @Override
    public Iterable<Room> getRooms() {
        return roomRepository.findAll();
    }

    @Override
    public Room findRoomByName(String name) {
        return roomRepository.findRoomByName(name);
    }

    @Override
    public void deleteRoom(String name) {
        roomRepository.deleteById(name);
    }

    @Override
    public boolean updateRoom(String name, int rows, int cols){
        if(!Objects.isNull(findRoomByName(name))) {
            addRoom(new Room(name, rows, cols));
            return true;
        }
        else {
            System.out.println("There are no rooms called " + name);
            return false;
        }
    }
}
