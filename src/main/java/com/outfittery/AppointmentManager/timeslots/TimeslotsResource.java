package com.outfittery.AppointmentManager.timeslots;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class TimeslotsResource {
    List<String> availableTimeslots;
}
