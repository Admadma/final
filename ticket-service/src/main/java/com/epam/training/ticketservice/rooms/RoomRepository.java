package com.epam.training.ticketservice.rooms;

import org.springframework.data.repository.CrudRepository;

public interface RoomRepository extends CrudRepository<Room, String> {

    Room findRoomByName(String name);
}
