package com.outfittery.AppointmentManager.stylists;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StylistRespository extends CrudRepository<Stylist, Long> {
}
