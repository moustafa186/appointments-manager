package com.outfittery.AppointmentManager.stylists;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController()
@RequestMapping("/stylists")
@AllArgsConstructor
@Api(value = "Stylist Endpoint", description = "Operations pertaining to stylist")
public class StylistController {

    @Autowired
    private StylistService stylistService;

    @PostMapping
    @ResponseBody
    @ResponseStatus(HttpStatus.CREATED)
    @ApiOperation(value = "Add a new stylist")
    public Stylist addStylist(@RequestBody Stylist stylist) {
        return stylistService.createStylist(stylist);
    }
}
