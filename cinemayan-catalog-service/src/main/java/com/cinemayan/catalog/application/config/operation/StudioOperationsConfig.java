package com.cinemayan.catalog.application.config.operation;

import com.cinemayan.catalog.domain.studio.command.*;
import com.cinemayan.catalog.domain.studio.persistence.StudioStorage;
import com.cinemayan.catalog.domain.studio.query.GetStudioByIdQuery;
import com.cinemayan.catalog.domain.studio.query.GetStudiosQuery;
import com.cinemayan.catalog.infrastructure.storage.studio.StudioJpaStorage;
import com.cinemayan.catalog.infrastructure.storage.studio.StudioRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
class StudioOperationsConfig {

    @Bean
    public StudioStorage studioStorage (StudioRepository repository) {
        return new StudioJpaStorage(repository);
    }

    @Bean
    public GetStudiosQuery getStudiosQuery (StudioStorage storage) {
        return new GetStudiosQuery(storage);
    }

    @Bean
    public GetStudioByIdQuery getStudioByIdQuery (StudioStorage storage) {
        return new GetStudioByIdQuery(storage);
    }

    @Bean
    public CreateStudioCommand createStudioCommand (StudioStorage storage) {
        return new CreateStudioCommand(storage);
    }

    @Bean
    public UpdateStudioCommand updateStudioCommand (StudioStorage storage) {
        return new UpdateStudioCommand(storage);
    }

    @Bean
    public DeleteStudioCommand deleteStudioCommand (StudioStorage storage) {
        return new DeleteStudioCommand(storage);
    }
}
