package com.outfittery.AppointmentManager.appointments;

import com.outfittery.AppointmentManager.stylists.NoStylistsAvailableException;
import com.outfittery.AppointmentManager.timeslots.InvalidTimeslotException;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import static org.springframework.http.HttpStatus.*;

@RestController()
@RequestMapping("/appointments")
@AllArgsConstructor
@Api(value = "Appointments Endpoint", description = "Operations pertaining to appointments")
public class AppointmentController {

    @Autowired
    private AppointmentService appointmentService;

    @PostMapping
    @ResponseStatus(OK)
    @ResponseBody
    @ApiOperation(value = "Book an available appointment for given customer")
    public ResponseEntity bookAppointmentAtTimeslot(@RequestBody AppointmentRequest appointmentRequest, HttpServletResponse response) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        ResponseEntity appointmentResponseEntity = null;
        try {
            Long customerId = appointmentRequest.getCustomerId();
            Date timeslot = dateFormat.parse(appointmentRequest.getTimeslot());

            Appointment appointment = appointmentService.bookAppointmentAtTimeslot(customerId, timeslot);

            appointmentResponseEntity = new ResponseEntity<>(appointment, OK);
        } catch (ParseException e) {
            appointmentResponseEntity = new ResponseEntity<>(e.getMessage(), BAD_REQUEST);
        } catch ( AppointmentBookingException |
                NoStylistsAvailableException |
                InvalidTimeslotException e) {
            appointmentResponseEntity = new ResponseEntity<>(e.getMessage(), NOT_FOUND);
        }
        return appointmentResponseEntity;
    }
}
