package com.travelagency.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.travelagency.constants.MealPlan;
import com.travelagency.model.City;
import com.travelagency.model.Country;
import com.travelagency.model.Hotel;
import com.travelagency.model.Variation;
import com.travelagency.repository.VariationRepository;
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
import java.util.Date;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.*;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.documentationConfiguration;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.put;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith({RestDocumentationExtension.class, SpringExtension.class})
@WebMvcTest(VariationController.class)
@AutoConfigureRestDocs(outputDir = "target/generated-snippets")
class VariationControllerTest {

    @MockBean
    private VariationRepository repository;

    private MockMvc mockMvc;


    @Autowired
    public VariationControllerTest(MockMvc mockMvc) {
        this.mockMvc = mockMvc;

    }

    @BeforeEach
    void setUp(WebApplicationContext webApplicationContext, RestDocumentationContextProvider restDocumentation) {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext)
                .apply(documentationConfiguration(restDocumentation))
                .build();
    }


    @Test
    void shouldCreateVariationAndReturnIsOkResponse() throws Exception {
        Country country = new Country("Turkey", "iiiii", "oopospaos", "kjksahhs");
        City city = new City("istanbul");
        Hotel hotel = new Hotel("hotel", "some hotel", "hhhggs", country, city, "iiii");
        Date date = new Date(2023, 01, 10);
        Variation variation = new Variation(date, 2, MealPlan.AI
                , hotel
                , new BigDecimal("200.00"), 4);
        when(repository.save(variation)).thenReturn(variation);


        mockMvc.perform(post("/api/variations")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(variation))).andDo(print())
                .andExpect(status().isCreated()).andDo(document("{methodName}", preprocessRequest(prettyPrint()), preprocessResponse(prettyPrint())));
    }

    @Test
    void shouldGetAllVariationsAndReturnIsOkResponse() throws Exception {
        Country country = new Country("Turkey", "iiiii", "oopospaos", "kjksahhs");
        City city = new City("istanbul");

        Hotel hotel = new Hotel("hotel", "some hotel", "hhhggs", country, city, "iiii");
        Variation variation = new Variation(new Date(2023 - 02 - 10), 2, MealPlan.AI, hotel, new BigDecimal("200.00"), 4);
        List<Variation> variations = new ArrayList<>();
        variations.add(variation);
        when(repository.findAll()).thenReturn(variations);

        mockMvc.perform(get("/api/variations").contentType(MediaType.APPLICATION_JSON).content(new ObjectMapper().writeValueAsString(variations))).andDo(print())
                .andExpect(status().isOk()).andDo(document("{methodName}", preprocessRequest(prettyPrint()), preprocessResponse(prettyPrint())));

    }

    @Test
    void shouldUpdateVariationAndReturnIsOkResponse() throws Exception {
        Country country = new Country("Turkey", "iiiii", "oopospaos", "kjksahhs");
        City city = new City("istanbul");


        Hotel hotel = new Hotel("hotel", "some hotel", "hhhggs", country, city, "iiii");
        Variation variation = new Variation(new Date(2023 - 02 - 10), 2, MealPlan.AI, hotel, new BigDecimal("200.00"), 4);
        Variation updatedVariation = new Variation(new Date(2023 - 02 - 10), 3, MealPlan.AI, hotel, new BigDecimal("200.00"), 4);
        hotel.setId(0l);
        when(repository.findById(hotel.getId())).thenReturn(Optional.of(variation));
        when(repository.save(any(Variation.class))).thenReturn(updatedVariation);

        mockMvc.perform(put("/api/variations/{id}", 0l)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(updatedVariation))).andDo(print())
                .andExpect(status().isOk()).andExpect(jsonPath("$.numberOfNights").value(3))
                .andDo(document("{methodName}", preprocessRequest(prettyPrint()), preprocessResponse(prettyPrint())));
    }

    @Test
    void shouldDeleteAllVariationsAndReturnNoContentResponse() throws Exception {
        doNothing().when(repository).deleteAll();

        mockMvc.perform(delete("/api/variations")).andDo(print()).andExpect(status().isNoContent())
                .andDo(document("{methodName}", preprocessRequest(prettyPrint()), preprocessResponse(prettyPrint())));
    }

    @Test
    void shouldDeleteVariationByIdAndReturnNoContentResponse() throws Exception {
        Long id = 0l;
        doNothing().when(repository).deleteById(id);

        mockMvc.perform(delete("/api/variations/{id}", id)).andDo(print()).andExpect(status().isNoContent())
                .andDo(document("{methodName}", preprocessRequest(prettyPrint()), preprocessResponse(prettyPrint())));
    }
}