package com.outfittery.AppointmentManager.timeslots;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.text.SimpleDateFormat;
import java.util.List;
import java.util.stream.Collectors;

@RestController()
@RequestMapping("/timeslots")
@AllArgsConstructor
@Api(value = "Appointments Endpoint", description = "Operations pertaining to appointments")
public class TimeslotController {

    @Autowired
    private TimeslotService timeslotService;

    @GetMapping
    @ResponseBody
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation(value = "View a list of available appointments", response = List.class)
    public List<String> listAvailableTimeslots(@RequestParam(defaultValue = "7") int daysFromNow) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        return timeslotService.listAvailableTimeslots(daysFromNow).stream()
                .sorted()
                .map(d -> dateFormat.format(d))
                .collect(Collectors.toList());
    }

}
