package com.outfittery.AppointmentManager.stylists;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface StylistRespository extends CrudRepository<Stylist, Long> {
    @Query(value = "SELECT id FROM stylists " +
            "WHERE id NOT IN (SELECT stylistId FROM appointments WHERE date_time = :timeslot)")
    List<Long> findAvailableStylistsByTimeslot(@Param(value="timeslot") Date timeslot);
}
