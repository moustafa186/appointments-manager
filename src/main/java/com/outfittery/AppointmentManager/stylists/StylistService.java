package com.outfittery.AppointmentManager.stylists;

import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class StylistService {

    @Autowired
    private StylistRespository stylistRespository;

    public Stylist createStylist(Stylist stylist) {
        return stylistRespository.save(stylist);
    }
}
