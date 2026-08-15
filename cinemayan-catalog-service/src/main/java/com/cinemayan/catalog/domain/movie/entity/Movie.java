package com.cinemayan.catalog.domain.movie.entity;

import com.cinemayan.catalog.domain.content.*;
import com.cinemayan.catalog.domain.studio.entity.Studio;
import lombok.*;

import java.time.LocalDate;
import java.util.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Movie {

    private UUID id;
    private String title;
    private String synopsis;
    private LocalDate releaseDate;
    private int runtime;
    private AgeRating ageRating;
    private Set<Genre> genres;
    private Set<ContentWarning> contentWarnings;
    private List<Studio> studios;
    private List<Cast> cast;
    private List<Crew> crew;
    private String imdbId;
}
