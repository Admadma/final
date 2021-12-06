package com.epam.training.ticketservice.rooms;

import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
public class RoomServiceImpl implements RoomServiceInterface{

    private RoomRepository roomRepository;

    public RoomServiceImpl(RoomRepository roomRepository) {
        this.roomRepository = roomRepository;
    }

    @Override
    public void addRoom(String name, int rows, int cols) throws NullPointerException{
        if(Objects.isNull(name))
            throw new NullPointerException();
        Room room = new Room();
        room.setName(name);
        room.setNumbOfRows(rows);
        room.setNumOfColumns(cols);
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
    public void updateRoom(String name, int rows, int cols) throws NullPointerException {
        if(!Objects.isNull(findRoomByName(name)))
            addRoom(name, rows, cols);
        else {
            System.out.println("There are no rooms called " + name);
            throw new NullPointerException();
        }
    }

    @Override
    public void listRooms() throws NullPointerException{
        Iterable<Room> rooms = roomRepository.findAll();
        if(!rooms.iterator().hasNext()) {
            System.out.println("There are no rooms at the moment");
            throw new NullPointerException();
        } else
            rooms.forEach(s -> System.out.println("Room " + s.getName() + " with "
                    + s.getNumbOfRows()*s.getNumOfColumns() + " seats, "
                    + s.getNumbOfRows() + " rows and "
                    + s.getNumOfColumns() + " columns"));
    }
}
