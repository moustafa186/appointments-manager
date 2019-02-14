package com.outfittery.AppointmentManager.timeslots;

import com.outfittery.AppointmentManager.stylists.Stylist;
import com.outfittery.AppointmentManager.stylists.StylistRespository;
import io.restassured.module.mockmvc.specification.MockMvcRequestSpecification;
import org.hamcrest.collection.IsEmptyIterable;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import static io.restassured.module.mockmvc.RestAssuredMockMvc.given;
import static org.hamcrest.CoreMatchers.equalTo;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class TimeslotControllerTest {

    @Autowired
    private TimeslotController timeslotController;
    @Autowired
    private StylistRespository stylistRespository;

    private MockMvcRequestSpecification spec;

    private Stylist stylistA, stylistB;
    private SimpleDateFormat dateFormat;
    private Calendar cal;

    @Before
    public void setUp(){
        dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        cal = Calendar.getInstance();
        cal.set(Calendar.HOUR_OF_DAY, 9);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MILLISECOND, 0);

        spec = given().
                standaloneSetup(timeslotController).
                contentType("application/json");

        stylistA = new Stylist().builder()
                .firstName("Malik")
                .lastName("Moustafa")
                .email("maloki@berlin.de")
                .build();

        stylistB = new Stylist().builder()
                .firstName("Ali")
                .lastName("Kamal")
                .email("ali.kamal@alex.eg")
                .build();
    }

    @After
    public void tearDown() {
        // just for simplicity, otherwise if needed a fresh database should be created every time
        stylistRespository.deleteAll();
    }

    @Test
    public void listAvailableTimeslotsWhenNoStylists() {
        spec.
        when().
            get("/timeslots").
        then().
            statusCode(200).
            body("availableTimeslots", IsEmptyIterable.emptyIterable());
    }

    @Test
    public void listAvailableTimeslotsWheOnlyOneStylistWithDefaultXdays() {
        stylistRespository.save(stylistA);

        String dateInRange = dateFormat.format(cal.getTime());

        cal.add(Calendar.DAY_OF_MONTH, 6);
        cal.set(Calendar.HOUR_OF_DAY, 16);
        cal.set(Calendar.MINUTE, 30);
        String lastDateInRange = dateFormat.format(cal.getTime());

        spec.
            when().
                get("/timeslots").
            then().
                statusCode(200).
                body("availableTimeslots.size()", equalTo(16*7)).
                body("availableTimeslots[0]", equalTo(dateInRange)).
                body("availableTimeslots[16*7 -1]", equalTo(lastDateInRange));
    }

    @Test
    public void listAvailableTimeslotsWheOnlyOneStylistWithGivenXdays() {
        stylistRespository.save(stylistA);

        String dateInRange = dateFormat.format(cal.getTime());

        cal.add(Calendar.DAY_OF_MONTH, 3);
        cal.set(Calendar.HOUR_OF_DAY, 16);
        cal.set(Calendar.MINUTE, 30);
        String lastDateInRange = dateFormat.format(cal.getTime());

        spec.
            when().
                get("/timeslots?daysFromNow=4").
            then().
                statusCode(200).
                body("availableTimeslots.size()", equalTo(16*4)).
                body("availableTimeslots[0]", equalTo(dateInRange)).
                body("availableTimeslots[16*4 -1]", equalTo(lastDateInRange));
    }

    @Test
    public void listAvailableTimeslotsWhenMoreThanOneStylistWithDefaultXdays() {
        stylistRespository.save(stylistA);
        stylistRespository.save(stylistB);

        String dateInRange = dateFormat.format(cal.getTime());

        cal.add(Calendar.DAY_OF_MONTH, 6);
        cal.set(Calendar.HOUR_OF_DAY, 16);
        cal.set(Calendar.MINUTE, 30);
        String lastDateInRange = dateFormat.format(cal.getTime());

        spec.
            when().
                get("/timeslots").
            then().
                statusCode(200).
                body("availableTimeslots.size()", equalTo(16*7)).
                body("availableTimeslots[0]", equalTo(dateInRange)).
                body("availableTimeslots[16*7 -1]", equalTo(lastDateInRange));
    }

    @Test
    public void listAvailableTimeslotsWhenMoreThanOneStylistWithGivenXdays() {
        stylistRespository.save(stylistA);
        stylistRespository.save(stylistB);

        String dateInRange = dateFormat.format(cal.getTime());

        cal.add(Calendar.DAY_OF_MONTH, 2);
        cal.set(Calendar.HOUR_OF_DAY, 16);
        cal.set(Calendar.MINUTE, 30);
        String lastDateInRange = dateFormat.format(cal.getTime());

        spec.
            when().
                get("/timeslots?daysFromNow=3").
            then().
                statusCode(200).
                body("availableTimeslots.size()", equalTo(16*3)).
                body("availableTimeslots[0]", equalTo(dateInRange)).
                body("availableTimeslots[16*3 -1]", equalTo(lastDateInRange));
    }

}