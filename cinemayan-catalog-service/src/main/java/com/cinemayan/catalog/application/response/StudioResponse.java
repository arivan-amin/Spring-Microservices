package com.cinemayan.catalog.application.response;

import com.cinemayan.catalog.domain.studio.entity.Studio;
import lombok.Value;

import java.time.LocalDate;
import java.util.UUID;

@Value
public class StudioResponse {

    UUID id;
    String name;
    String country;
    LocalDate foundedDate;

    public static StudioResponse of (Studio studio) {
        return new StudioResponse(studio.getId(), studio.getName(), studio.getCountry(),
            studio.getFoundedDate());
    }
}
