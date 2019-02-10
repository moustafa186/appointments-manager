package com.outfittery.AppointmentManager.appointments;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController()
@RequestMapping("/appointments")
@AllArgsConstructor
@Api(value = "Appointments Endpoint", description = "Operations pertaining to appointments")
public class AppointmentController {

    @Autowired
    private AppointmentService appointmentService;

    @GetMapping
    @ResponseBody
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation(value = "View a list of available appointments", response = List.class)
    public Iterable<Appointment> listAvailableAppointments() {
        return appointmentService.findAll();
    }

    @PatchMapping
    @ResponseBody
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation(value = "Reserve an available appointment")
    public Appointment reserveAppointment(@RequestParam Long appointmentId, @RequestParam Long customerId) {
        return appointmentService.reserveAppointment(appointmentId, customerId);
    }
}
