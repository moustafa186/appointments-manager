package com.outfittery.AppointmentManager.stylists;

import io.restassured.module.mockmvc.specification.MockMvcRequestSpecification;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.test.context.junit4.SpringRunner;

import static io.restassured.module.mockmvc.RestAssuredMockMvc.given;
import static org.hamcrest.CoreMatchers.equalTo;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class StylistControllerTest {

    @Autowired
    StylistController stylistController;

    private MockMvcRequestSpecification spec;

    @Before
    public void setUp(){
        spec = given().
                standaloneSetup(stylistController).
                contentType("application/json");
    }

    @Test
    public void addStylist() {
        Stylist stylistA = new Stylist().builder()
                .firstName("Malik")
                .lastName("Moustafa")
                .email("maloki@berlin.de")
                .build();

        spec.
                body(stylistA).
        when().
                post("/stylists").
        then().
                statusCode(201).
                body("id", equalTo(1)).
                body("firstName", equalTo("Malik")).
                body("lastName", equalTo("Moustafa")).
                body("email", equalTo("maloki@berlin.de"));

        Stylist stylistB = new Stylist().builder()
                .firstName("Ali")
                .lastName("Kamal")
                .email("ali.kamal@alex.eg")
                .build();

        spec.
                body(stylistB).
                when().
                post("/stylists").
                then().
                statusCode(201).
                body("id", equalTo(2)).
                body("firstName", equalTo("Ali")).
                body("lastName", equalTo("Kamal")).
                body("email", equalTo("ali.kamal@alex.eg"));
    }

}