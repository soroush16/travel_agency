package com.travelagency.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.travelagency.constants.MealPlan;
import com.travelagency.model.*;
import com.travelagency.service.TripServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.restdocs.RestDocumentationContextProvider;
import org.springframework.restdocs.RestDocumentationExtension;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.documentationConfiguration;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.post;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith({RestDocumentationExtension.class, SpringExtension.class})
@WebMvcTest(TripController.class)
@AutoConfigureRestDocs(outputDir = "target/generated-snippets")
public class TripControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private TripServiceImpl tripServiceImpl;

    @BeforeEach
    void setUp(WebApplicationContext webApplicationContext, RestDocumentationContextProvider restDocumentation) {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext)
                .apply(documentationConfiguration(restDocumentation))
                .build();
    }


    @Test
    void shouldCreateTrip() throws Exception {

        Trip myTrip = new Trip(new User(0L,"Ben", "Tamm", "Eku@mail.com"),
                new Variation(new Date(2023 - 01 - 10), 2, MealPlan.AI,
                        new Hotel("Grand Kolibri Prestige & Spa", "nice hotel",  "5231",
                                new Country("Türkiye", "", "Nice view", "Türkiye.img"),
                                new City("Payallar"), "Kolibri.img"), new BigDecimal("200.00"), 4),
                new Date(2023 - 01 - 20), new Date(2023 - 01 - 27), new Hotel("Grand Kolibri Prestige & Spa", "nice hotel",  "5231",
                new Country("Türkiye", "", "Nice view", "Türkiye.img"),
                new City("Payallar"), "Kolibri.img"), 2, 2);

        when(tripServiceImpl.saveTrip(myTrip)).thenReturn(myTrip);

        mockMvc.perform(post("/api/trips")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(myTrip)))
                .andExpect(status().isCreated())
                .andDo(print())
                .andDo(document("{methodName}", preprocessRequest(prettyPrint()), preprocessResponse(prettyPrint())));

    }

    @Test
    void shouldReturnTripById() throws Exception {
        long id = 1L;

        Trip myTrip = new Trip(new User(id,"Ben", "Tamm", "Eku@mail.com"),
                new Variation(new Date(2023 - 01 - 10), 2, MealPlan.AI,
                        new Hotel("Grand Kolibri Prestige & Spa", "nice hotel",  "5231",
                                new Country("Türkiye", "", "Nice view", "Türkiye.img"),
                                new City("Payallar"), "Kolibri.img"), new BigDecimal("200.00"), 4),
                new Date(2023 - 01 - 20), new Date(2023 - 01 - 27), new Hotel("Grand Kolibri Prestige & Spa", "nice hotel",  "5231",
                new Country("Türkiye", "", "Nice view", "Türkiye.img"),
                new City("Payallar"), "Kolibri.img"), 2, 2);

        when(tripServiceImpl.getTripById(id)).thenReturn(myTrip);
        mockMvc.perform(get("/api/trips/{id}", id)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.adults").value(2))
                .andDo(print())
                .andDo(document("{methodName}", preprocessRequest(prettyPrint()), preprocessResponse(prettyPrint())));
    }

    @Test
    void shouldReturnAllTrips() throws Exception {
        List<Trip> listOfTrips = new ArrayList<>(Arrays.asList(new Trip(new User(1L,"Ben", "Tamm", "Ben@gmail.com"),
                        new Variation(new Date(2023 - 01 - 10), 2, MealPlan.AI,
                                new Hotel("Grand Kolibri Prestige & Spa", "nice hotel",  "5231",
                                        new Country("Türkiye", "", "Nice view", "Türkiye.img"),
                                        new City("Payallar"), "Kolibri.img"), new BigDecimal("200.00"), 4),
                        new Date(2023 - 01 - 20), new Date(2023 - 01 - 27), new Hotel("Grand Kolibri Prestige & Spa", "nice hotel",  "5231",
                        new Country("Türkiye", "", "Nice view", "Türkiye.img"),
                        new City("Payallar"), "Kolibri.img"), 2, 2),
                new Trip(new User(2L,"Soroush", "ppp", "soroush@Gmail.com"),
                        new Variation(new Date(2023 - 01 - 10), 2, MealPlan.AI,
                                new Hotel("Grand Kolibri Prestige & Spa", "nice hotel",  "5231",
                                        new Country("Türkiye", "", "Nice view", "Türkiye.img"),
                                        new City("Payallar"), "Kolibri.img"), new BigDecimal("200.00"), 4),
                        new Date(2023 - 01 - 20), new Date(2023 - 01 - 27), new Hotel("Grand Kolibri Prestige & Spa", "nice hotel",  "5231",
                        new Country("Türkiye", "", "Nice view", "Türkiye.img"),
                        new City("Payallar"), "Kolibri.img"), 2, 2)));

        when(tripServiceImpl.getAllTrips()).thenReturn(listOfTrips);

        mockMvc.perform(get("/api/trips")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.size()").value(listOfTrips.size()))
                .andDo(print())
                .andDo(document("{methodName}",preprocessRequest(prettyPrint()),preprocessResponse(prettyPrint())));
    }

    @Test
    void shouldUpdateTripWithNewPassengerNumbers() throws Exception {

        Trip myTrip = new Trip(new User(1L,"Ben", "Tamm", "Eku@mail.com"),
                new Variation(new Date(2023 - 01 - 10), 2, MealPlan.AI,
                        new Hotel("Grand Kolibri Prestige & Spa", "nice hotel",  "5231",
                                new Country("Türkiye", "", "Nice view", "Türkiye.img"),
                                new City("Payallar"), "Kolibri.img"), new BigDecimal("200.00"), 4),
                new Date(2023 - 01 - 20), new Date(2023 - 01 - 27), new Hotel("Grand Kolibri Prestige & Spa", "nice hotel",  "5231",
                new Country("Türkiye", "", "Nice view", "Türkiye.img"),
                new City("Payallar"), "Kolibri.img"), 2, 2);

        Trip myUpdatedTrip = new Trip(new User(1L,"Ben", "Tamm", "Eku@mail.com"),
                new Variation(new Date(2023 - 01 - 10), 2, MealPlan.AI,
                        new Hotel("Grand Kolibri Prestige & Spa", "nice hotel",  "5231",
                                new Country("Türkiye", "", "Nice view", "Türkiye.img"),
                                new City("Payallar"), "Kolibri.img"), new BigDecimal("200.00"), 4),
                new Date(2023 - 01 - 20), new Date(2023 - 01 - 27), new Hotel("Grand Kolibri Prestige & Spa", "nice hotel",  "5231",
                new Country("Türkiye", "", "Nice view", "Türkiye.img"),
                new City("Payallar"), "Kolibri.img"), 4, 4);


        when(tripServiceImpl.getTripById(1L)).thenReturn(myTrip);
        when(tripServiceImpl.updateTrip(any(Trip.class))).thenReturn(myUpdatedTrip);


        mockMvc.perform(put("/api/trips")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(myUpdatedTrip)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.adults").value(4))
                .andExpect(jsonPath("$.children").value(4))
                .andDo(print())
                .andDo(document("{methodName}",preprocessRequest(prettyPrint()),preprocessResponse(prettyPrint())));

    }

    @Test
    void shouldDeleteTripById() throws Exception {
        long id = 1L;

        doNothing().when(tripServiceImpl).deleteTripById(id);
        mockMvc.perform(delete("/api/trips/{id}", id))
                .andExpect(status().isNoContent())
                .andDo(print())
                .andDo(document("{methodName}", preprocessRequest(prettyPrint()), preprocessResponse(prettyPrint())));
    }

    @Test
    void shouldDeleteAllHotels() throws Exception {

        doNothing().when(tripServiceImpl).deleteAllTrips();
        mockMvc.perform(delete("/api/trips"))
                .andExpect(status().isNoContent())
                .andDo(print())
                .andDo(document("{methodName}", preprocessRequest(prettyPrint()), preprocessResponse(prettyPrint())));
    }
}