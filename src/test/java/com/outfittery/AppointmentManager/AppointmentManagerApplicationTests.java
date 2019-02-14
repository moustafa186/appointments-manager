package com.outfittery.AppointmentManager;

import static org.assertj.core.api.Assertions.assertThat;

import com.outfittery.AppointmentManager.appointments.AppointmentController;
import com.outfittery.AppointmentManager.stylists.StylistController;
import com.outfittery.AppointmentManager.timeslots.TimeslotController;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;


@RunWith(SpringRunner.class)
@SpringBootTest
public class AppointmentManagerApplicationTests {

	@Autowired
	AppointmentController appointmentController;

	@Autowired
	StylistController stylistController;

	@Autowired
	TimeslotController timeslotController;


	@Test
	public void contextLoads() {
		assertThat(appointmentController).isNotNull();
		assertThat(stylistController).isNotNull();
		assertThat(timeslotController).isNotNull();
	}

}

