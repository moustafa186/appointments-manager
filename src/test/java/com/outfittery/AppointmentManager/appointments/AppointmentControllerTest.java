package com.outfittery.AppointmentManager.appointments;

import com.outfittery.AppointmentManager.stylists.Stylist;
import com.outfittery.AppointmentManager.stylists.StylistRespository;
import io.restassured.module.mockmvc.specification.MockMvcRequestSpecification;
import org.json.simple.JSONObject;
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

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class AppointmentControllerTest {

    @Autowired
    private AppointmentController appointmentController;

    @Autowired
    private StylistRespository stylistRespository;

    @Autowired
    private AppointmentRepository appointmentRepository;

    private MockMvcRequestSpecification spec;

    Stylist stylistA, stylistB;

    private JSONObject requestBody;

    private String dateInPast;
    private String dateInRange;
    private String anotherDateInRange;
    private String yetAnotherDateInRange;
    private String lastDateInRange;
    private String dateInRangeHourBeforeStart;
    private String dateInRangeHourAfterEnd;
    private String dateMoreThanOneMonth;

    @Before
    public void setUp(){
        stylistRespository.deleteAll();
        appointmentRepository.deleteAll();

        requestBody = new JSONObject();

        spec = given().
                standaloneSetup(appointmentController).
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

        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.HOUR_OF_DAY, 11);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MILLISECOND, 0);
        Date today = cal.getTime();

        cal.add(Calendar.DAY_OF_MONTH, -3);
        dateInPast = dateFormat.format(cal.getTime());

        cal.add(Calendar.DAY_OF_MONTH, 10);
        dateInRange = dateFormat.format(cal.getTime());

        cal.add(Calendar.MINUTE, 30);
        anotherDateInRange = dateFormat.format(cal.getTime());

        cal.add(Calendar.MINUTE, 30);
        yetAnotherDateInRange = dateFormat.format(cal.getTime());

        cal.set(Calendar.HOUR_OF_DAY, 8);
        dateInRangeHourBeforeStart = dateFormat.format(cal.getTime());

        cal.set(Calendar.HOUR_OF_DAY, 17);
        dateInRangeHourAfterEnd = dateFormat.format(cal.getTime());

        cal.setTime(today);
        cal.set(Calendar.HOUR_OF_DAY, 16);
        cal.set(Calendar.MINUTE, 30);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MILLISECOND, 0);

        cal.add(Calendar.DAY_OF_MONTH,29);
        lastDateInRange = dateFormat.format(cal.getTime());

        cal.add(Calendar.DAY_OF_MONTH,5);
        dateMoreThanOneMonth = dateFormat.format(cal.getTime());
    }

    @After
    public void tearDown() {
        // just for simplicity, otherwise if needed a fresh database should be created every time
        stylistRespository.deleteAll();
        appointmentRepository.deleteAll();
    }

    @Test
    public void bookAppointmentWhenNoStylistsShouldFail() {
        requestBody.put("customerId", "1");
        requestBody.put("timeslot", dateInRange);

        spec.
            body(requestBody.toJSONString()).
            when().
                post("/appointments").
            then().
                statusCode(404);
    }

    @Test
    public void bookAppointmentInThePastShouldfail() {
        stylistRespository.save(stylistA);
        requestBody.put("customerId", "1");
        requestBody.put("timeslot", dateInPast);

        spec.
                body(requestBody.toJSONString()).
                when().
                post("/appointments").
                then().
                statusCode(404);
    }

    @Test
    public void bookAppointmentWhenInMoreThanOneMonthShouldFail() {
        stylistRespository.save(stylistA);
        requestBody.put("customerId", "1");
        requestBody.put("timeslot", dateMoreThanOneMonth);

        spec.
            body(requestBody.toJSONString()).
            when().
                post("/appointments").
            then().
                statusCode(404);
    }

    @Test
    public void bookAppointmentWhenOneStylist() {
        stylistRespository.save(stylistA);

        requestBody.put("customerId", "1");
        requestBody.put("timeslot", anotherDateInRange);

        spec.
            body(requestBody.toJSONString()).
            when().
                post("/appointments").
            then().
                statusCode(200);
    }

    @Test
    public void bookAppointmentWhenOneStylistLastDateInRange() {
        stylistRespository.save(stylistA);

        requestBody.put("customerId", "1");
        requestBody.put("timeslot", lastDateInRange);

        spec.
                body(requestBody.toJSONString()).
                when().
                post("/appointments").
                then().
                statusCode(200);
    }

    @Test
    public void bookAppointmentWhenOneStylistHourBeforeStartShouldFail() {
        stylistRespository.save(stylistA);

        requestBody.put("customerId", "1");
        requestBody.put("timeslot", dateInRangeHourBeforeStart);

        spec.
                body(requestBody.toJSONString()).
                when().
                post("/appointments").
                then().
                statusCode(404);
    }

    @Test
    public void bookAppointmentWhenOneStylistHourAfterEndShouldFail() {
        stylistRespository.save(stylistA);

        requestBody.put("customerId", "1");
        requestBody.put("timeslot", dateInRangeHourAfterEnd);

        spec.
                body(requestBody.toJSONString()).
                when().
                post("/appointments").
                then().
                statusCode(404);
    }

    @Test
    public void bookTwoAppointmentsInSameSlotWhenOneStylistShouldFail() {
        stylistRespository.save(stylistA);

        requestBody.put("customerId", "1");
        requestBody.put("timeslot", dateInRange);

        spec.
            body(requestBody.toJSONString()).
            when().
                post("/appointments").
            then().
                statusCode(200);

        requestBody.put("customerId", "2");
        spec.
            body(requestBody.toJSONString()).
            when().
                post("/appointments").
            then().
                statusCode(404);
    }

    @Test
    public void bookTwoAppointmentsInSameSlotWhenTwoStylist() {
        stylistRespository.save(stylistA);
        stylistRespository.save(stylistB);

        requestBody.put("customerId", "1");
        requestBody.put("timeslot", yetAnotherDateInRange);

        spec.
            body(requestBody.toJSONString()).
            when().
                post("/appointments").
            then().
                statusCode(200);

        requestBody.put("customerId", "2");
        spec.
            body(requestBody.toJSONString()).
            when().
                post("/appointments").
            then().
                statusCode(200);
    }

    @Test
    public void bookThreeAppointmentsInSameSlotWhenTwoStylistShouldFail() {
        stylistRespository.save(stylistA);
        stylistRespository.save(stylistB);

        requestBody.put("customerId", "1");
        requestBody.put("timeslot", dateInRange);

        spec.
            body(requestBody.toJSONString()).
            when().
                post("/appointments").
            then().
                statusCode(200);

        requestBody.put("customerId", "2");
        spec.
            body(requestBody.toJSONString()).
            when().
                post("/appointments").
            then().
                statusCode(200);

        requestBody.put("customerId", "3");
        spec.
            body(requestBody.toJSONString()).
            when().
                post("/appointments").
            then().
                statusCode(404);
    }
}