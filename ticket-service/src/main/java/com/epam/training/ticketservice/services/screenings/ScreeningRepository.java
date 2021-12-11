package com.epam.training.ticketservice.services.screenings;

import org.springframework.data.repository.CrudRepository;
import org.springframework.transaction.annotation.Transactional;

public interface ScreeningRepository extends CrudRepository<Screening, String> {

    @Transactional
    void deleteScreeningById(ScreeningId id);
}
