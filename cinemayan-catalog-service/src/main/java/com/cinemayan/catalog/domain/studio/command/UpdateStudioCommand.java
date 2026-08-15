package com.cinemayan.catalog.domain.studio.command;

import com.cinemayan.catalog.domain.studio.entity.Studio;
import com.cinemayan.catalog.domain.studio.exception.StudioNotFoundException;
import com.cinemayan.catalog.domain.studio.persistence.StudioStorage;
import lombok.RequiredArgsConstructor;
import lombok.Value;
import lombok.extern.slf4j.Slf4j;

import java.util.UUID;

@RequiredArgsConstructor
@Slf4j
public class UpdateStudioCommand {

    private final StudioStorage storage;

    public Output execute (Input input) {
        UUID studioId = input.getStudio()
            .getId();
        if (isStudioNotFound(studioId)) {
            log.warn("Studio update rejected, studio id = {} not found", input.getStudio()
                .getId());
            throw new StudioNotFoundException(studioId);
        }
        Studio updated = storage.update(input.getStudio());
        log.info("Successfully updated studio = {}", updated);
        return new Output(updated);
    }

    private boolean isStudioNotFound (UUID studioId) {
        return storage.findById(studioId)
            .isEmpty();
    }

    @Value
    public static class Input {

        Studio studio;
    }

    @Value
    public static class Output {

        Studio studio;
    }
}
