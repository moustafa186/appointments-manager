package com.outfittery.AppointmentManager.stylists;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity(name = "stylists")
@Data @NoArgsConstructor @AllArgsConstructor
@ApiModel(description = "All details about the Stylist.")
public class Stylist {
    @Id
    @SequenceGenerator(name = "stylist_seq")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "stylist_seq")
    @ApiModelProperty(notes = "The database generated stylist ID", readOnly = true)
    @Column(name = "id")
    private Long id;

    @ApiModelProperty(notes = "First Name")
    @Column(name = "firstName")
    private String firstName;

    @ApiModelProperty(notes = "Last Name")
    @Column(name = "lastName")
    private String lastName;

    @ApiModelProperty(notes = "Email address")
    @Column(name = "email")
    private String email;
}
