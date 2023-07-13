package com.travelagency.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import com.travelagency.constants.MealPlan;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.math.BigDecimal;
import java.util.Date;

@Entity
@RequiredArgsConstructor
@NoArgsConstructor
@Data
@ToString
public class Variation {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @NonNull
    @NotNull(message = "please choose a date")
    private Date startDate;
    @NonNull
    @NotNull(message = "please enter number of nights")
    private int numberOfNights;
    @NonNull
    @NotNull(message = "please choose meal plan type")
    @Enumerated(EnumType.STRING)
    private MealPlan plan;
    @JsonIgnoreProperties(value = {"variations"}, allowSetters = true)
    @ManyToOne
    @NonNull
    @NotNull(message = "hotel cant be empty")
    private Hotel hotel;
    @NonNull
    @NotNull(message = "please enter price for this variation")
    private BigDecimal price;
    @NotNull(message = "please enter available number of seats")
    @NonNull
    private int freeSeats;


}
