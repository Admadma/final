package com.epam.training.ticketservice.services.rooms;

import org.springframework.data.repository.CrudRepository;

public interface RoomRepository extends CrudRepository<Room, String> {

    Room findRoomByName(String name);
}
