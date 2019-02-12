package com.outfittery.AppointmentManager.appointments;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

@RestController()
@RequestMapping("/appointments")
@AllArgsConstructor
@Api(value = "Appointments Endpoint", description = "Operations pertaining to appointments")
public class AppointmentController {

    @Autowired
    private AppointmentService appointmentService;

    @PostMapping
    @ResponseBody
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation(value = "Reserve an available appointment for given customer")
    public Appointment bookAppointmentAtTimeslot(@RequestBody AppointmentRequest appointmentRequest) throws ParseException {
        System.out.println("customerId: " + appointmentRequest.getCustomerId());
        System.out.println("timeslot: " + appointmentRequest.getTimeslot());
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        return appointmentService.bookAppointmentAtTimeslot(appointmentRequest.getCustomerId(),
                dateFormat.parse(appointmentRequest.getTimeslot()));
    }
}
