package com.outfittery.AppointmentManager.appointments;


import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "appointments")
@Data
@NoArgsConstructor
@AllArgsConstructor
@ApiModel(description = "All details about the Appointments.")
public class Appointment {
    @Id
    @GeneratedValue
    @ApiModelProperty(notes = "The database generated appointment ID", readOnly = true)
    @Column(name = "id")
    private Long id;

    @ApiModelProperty(notes = "Customer Id")
    @Column(name = "customerId")
    private Long customerId;

    @ApiModelProperty(notes = "Stylist Id")
    @Column(name = "stylist")
    private Long stylistId;

    @ApiModelProperty(notes = "Time slot: Date and Time")
    @Column(name = "timeSlot")
    @Temporal(TemporalType.TIMESTAMP)
    private Date timeSlot;

}
