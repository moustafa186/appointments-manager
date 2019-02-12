package com.outfittery.AppointmentManager.stylists;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
}
