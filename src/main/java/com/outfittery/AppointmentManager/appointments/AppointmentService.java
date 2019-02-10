package com.outfittery.AppointmentManager.appointments;

import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@AllArgsConstructor
public class AppointmentService {

    @Autowired
    private AppointmentRepository appointmentRepository;

    public Appointment reserveAppointment(Long appointmentId, Long customerId) {
        // TODO complete implementation
        Optional<Appointment> appointment = appointmentRepository.findById(appointmentId);
        return null;
    }

    public Iterable<Appointment> findAll() {
        return appointmentRepository.findAll();
    }
}
