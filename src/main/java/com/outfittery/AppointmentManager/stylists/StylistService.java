package com.outfittery.AppointmentManager.stylists;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class StylistService {

    private StylistRespository stylistRespository;

    @Autowired
    public StylistService(StylistRespository stylistRespository) {
        this.stylistRespository = stylistRespository;
    }

    public Stylist createStylist(Stylist stylist) {
        return stylistRespository.save(stylist);
    }

    public Long getNextAvailableStylist(Date timeslot) {
        // TODO change to get stylist with least appointments
        List<Long> availableStylists = stylistRespository.findAvailableStylistsByTimeslot(timeslot);
        return availableStylists.get(0);
    }
}
