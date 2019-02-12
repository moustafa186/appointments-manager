package com.outfittery.AppointmentManager.appointments;

import lombok.Data;

@Data
public class AppointmentRequest {
    private Long customerId;
    private String timeslot;
}
