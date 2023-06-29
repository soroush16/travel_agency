package com.travelagency.model;

import com.travelagency.slugify.Slugify;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

import java.util.List;

@Entity
@NoArgsConstructor
@RequiredArgsConstructor
@Data

public class Country implements Slugify {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @NonNull
    @NotBlank(message = "Can not be empty")
    private String name;
    @NonNull
    private String tag;
    @OneToMany(cascade = CascadeType.ALL)
    private List<Hotel> hotels;
    @NonNull
    @NotBlank(message = "Can not be empty")
    private String description;
    private int counter;
    @NonNull
    @NotBlank(message = "Can not be empty")
    private String imageUrl;

}
