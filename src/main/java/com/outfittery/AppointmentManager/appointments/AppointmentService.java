package com.outfittery.AppointmentManager.appointments;

import com.outfittery.AppointmentManager.stylists.StylistService;
import com.outfittery.AppointmentManager.timeslots.TimeslotService;
import lombok.AllArgsConstructor;
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
    public Appointment bookAppointmentAtTimeslot(Long customerId, Date timeslot) throws RuntimeException {
        System.out.println(timeslot.toString());
        boolean timeslotAvailable = timeslotService.isTimeslotAvailable(timeslot);
        if(timeslotAvailable) {
            Long availableStylistId = stylistService.getNextAvailableStylist(timeslot);
            Appointment appointmentToSave = Appointment.builder()
                    .customerId(customerId)
                    .stylistId(availableStylistId)
                    .date(timeslot)
                    .build();
            return appointmentRepository.save(appointmentToSave);
        } else {
            throw new RuntimeException("Sorry! The appointment is booked. Please choose another time. Sorry for inconvenience");
        }
    }
}
