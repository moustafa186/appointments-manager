package com.outfittery.AppointmentManager.appointments;


import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;


@Entity(name = "appointments")
@Data @NoArgsConstructor @AllArgsConstructor
@Builder
@ApiModel(description = "All details about the Appointments.")
public class Appointment {
    @Id
    @SequenceGenerator(name = "appointments_seq")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "appointments_seq")
    @ApiModelProperty(notes = "The database generated appointment ID", readOnly = true)
    @Column(name = "id")
    private Long id;

    @ApiModelProperty(notes = "Customer Id")
    @Column(name = "customerId")
    private Long customerId;

    @ApiModelProperty(notes = "Stylist Id")
    @Column(name = "stylistId")
    private Long stylistId;

    @ApiModelProperty(notes = "Appointment date and time")
    @Column(name = "dateTime")
    @Temporal(TemporalType.TIMESTAMP)
    private Date date;
}
