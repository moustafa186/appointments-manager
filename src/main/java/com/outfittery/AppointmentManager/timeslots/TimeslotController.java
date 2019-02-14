package com.outfittery.AppointmentManager.timeslots;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.text.SimpleDateFormat;
import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.http.HttpStatus.OK;

@RestController()
@RequestMapping("/timeslots")
@AllArgsConstructor
@Api(value = "Appointments Endpoint", description = "Operations pertaining to appointments")
public class TimeslotController {

    @Autowired
    private TimeslotService timeslotService;

    @GetMapping
    @ResponseBody
    @ResponseStatus(OK)
    @ApiOperation(value = "View a list of available appointments", response = List.class)
    public ResponseEntity<TimeslotsResource> listAvailableTimeslots(@RequestParam(defaultValue = "7") int daysFromNow) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        List<String> availableTimeslots = timeslotService.listAvailableTimeslots(daysFromNow).stream()
                .sorted()
                .map(d -> dateFormat.format(d))
                .collect(Collectors.toList());

        return new ResponseEntity<>(new TimeslotsResource(availableTimeslots), OK);
    }

}
