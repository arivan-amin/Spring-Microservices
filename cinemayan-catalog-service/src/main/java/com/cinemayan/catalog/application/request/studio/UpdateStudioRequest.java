package com.cinemayan.catalog.application.request.studio;

import com.cinemayan.catalog.domain.studio.command.UpdateStudioCommand;
import com.cinemayan.catalog.domain.studio.entity.Studio;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Past;
import lombok.Value;

import java.time.LocalDate;
import java.util.UUID;

@Value
public class UpdateStudioRequest {

    UUID id;

    @NotBlank
    String name;

    @NotBlank
    String country;

    @Past
    LocalDate foundedDate;

    public UpdateStudioCommand.Input toInput (UUID id) {
        Studio studio = new Studio();
        studio.setId(id);
        studio.setName(name);
        studio.setCountry(country);
        studio.setFoundedDate(foundedDate);
        return new UpdateStudioCommand.Input(studio);
    }
}
