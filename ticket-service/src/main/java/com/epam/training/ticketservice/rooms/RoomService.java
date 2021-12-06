package com.epam.training.ticketservice.rooms;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RoomService {

    @Autowired
    RoomController roomController;

    public void createRoom(String[] params){
        roomController.addRoom(params[1], Integer.valueOf(params[2]), Integer.valueOf(params[3]));
    }

    public void deleteRoom(String id){
        roomController.deleteRoom(id);
    }

    public void updateRoom(String[] params){
        roomController.addRoom(params[1], Integer.valueOf(params[2]), Integer.valueOf(params[3]));
    }

    public void listRooms(){
        Iterable<Room> rooms = roomController.getRooms();
        if(!rooms.iterator().hasNext())
            System.out.println("There are no rooms at the moment");
        else
            rooms.forEach(s -> System.out.println("Room " + s.getName() + " with "
                    + s.getNumbOfRows()*s.getNumOfColumns() + " seats, "
                    + s.getNumbOfRows() + " rows and "
                    + s.getNumOfColumns() + " columns"));
    }


}
