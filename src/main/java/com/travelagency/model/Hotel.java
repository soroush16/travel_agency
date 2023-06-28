package com.travelagency.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.travelagency.slugify.Slugify;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.util.List;

@Entity
@NoArgsConstructor
@RequiredArgsConstructor
@Data

public class Hotel implements Slugify {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    @NonNull
    @NotBlank(message = "Can not be empty")
    private String name;
    @NonNull
    @NotBlank(message = "Can not be empty")
    @Column(columnDefinition = "text")
    private String description;
    @NonNull
    private String tag;
    @NonNull
    // no need for next line
    @JsonIgnoreProperties(value = {"hotels"}, allowSetters = true)
    //oneToOne
    @ManyToOne
    @NotNull(message = "please provide a country for this hotel")
    private Country country;
    @NonNull
    @ManyToOne
    @NotNull(message = "please provide a city for this hotel")
    private City city;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "hotel")
    private List<Variation> variations;

    private int counter ;
    @NonNull
    @NotBlank(message = "please provide image URL")
    private String imageUrl;


}