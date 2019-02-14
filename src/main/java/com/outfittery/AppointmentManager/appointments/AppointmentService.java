package com.outfittery.AppointmentManager.appointments;

import com.outfittery.AppointmentManager.stylists.NoStylistsAvailableException;
import com.outfittery.AppointmentManager.stylists.StylistService;
import com.outfittery.AppointmentManager.timeslots.InvalidTimeslotException;
import com.outfittery.AppointmentManager.timeslots.TimeslotService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

@Service
public class AppointmentService {

    private StylistService stylistService;

    private TimeslotService timeslotService;

    private AppointmentRepository appointmentRepository;

    @Autowired
    public AppointmentService(StylistService stylistService,
                              TimeslotService timeslotService,
                              AppointmentRepository appointmentRepository) {
        this.stylistService = stylistService;
        this.timeslotService = timeslotService;
        this.appointmentRepository = appointmentRepository;
    }

    @Transactional
    public Appointment bookAppointmentAtTimeslot(Long customerId, Date timeslot) throws AppointmentBookingException, NoStylistsAvailableException, InvalidTimeslotException {
        boolean timeslotAvailable = timeslotService.isTimeslotAvailable(timeslot);
        if(timeslotAvailable) {
            Long availableStylistId = stylistService.getNextAvailableStylist(timeslot);
            Appointment appointmentToSave = Appointment.builder()
                    .customerId(customerId)
                    .stylistId(availableStylistId)
                    .date(timeslot)
                    .build();
            timeslotService.bookTimeslot(timeslot);
            return appointmentRepository.save(appointmentToSave);
        } else {
            throw new AppointmentBookingException("Sorry! The appointment is booked. Please choose another time. Sorry for inconvenience");
        }
    }
}
