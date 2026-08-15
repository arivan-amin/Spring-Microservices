package com.cinemayan.catalog.domain.studio.command;

import com.cinemayan.catalog.domain.studio.entity.Studio;
import com.cinemayan.catalog.domain.studio.exception.StudioAlreadyExistsException;
import com.cinemayan.catalog.domain.studio.exception.StudioErrorCode;
import com.cinemayan.catalog.domain.studio.persistence.StudioStorage;
import com.cinemayan.catalog.utility.StudioTestData;
import com.cinemayan.testing.architecture.bases.BaseUnitTest;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowableOfType;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;

@Slf4j
class CreateStudioCommandTest implements BaseUnitTest {

    @Mock
    private StudioStorage storage;

    @InjectMocks
    private CreateStudioCommand command;

    @Test
    void execute_shouldReturnSavedStudio_whenNameDoesNotExist () {
        // given
        Studio studio = StudioTestData.withDefaultName();
        CreateStudioCommand.Input input = new CreateStudioCommand.Input(studio);

        UUID studioId = UUID.randomUUID();
        Studio savedStudio = StudioTestData.withDefaultName();
        savedStudio.setId(studioId);

        given(storage.create(studio)).willReturn(savedStudio);

        // when
        CreateStudioCommand.Output output = command.execute(input);
        Studio result = output.getStudio();

        // then
        assertThat(result).usingRecursiveComparison()
            .isEqualTo(savedStudio);
    }

    @Test
    void execute_shouldPassParameterToStorage_whenNameDoesNotExist () {
        // given
        Studio studio = StudioTestData.withDefaultName();
        CreateStudioCommand.Input input = new CreateStudioCommand.Input(studio);

        // when
        CreateStudioCommand.Output output = command.execute(input);

        // then
        then(storage).should(times(1))
            .create(studio);
    }

    @Test
    void execute_shouldThrowStudioAlreadyExistsException_whenNameAlreadyExists () {
        // given
        String studioName = "amazon";
        Studio studio = StudioTestData.withName(studioName);
        CreateStudioCommand.Input input = new CreateStudioCommand.Input(studio);

        given(storage.findByName(studioName)).willReturn(Optional.of(studio));

        // when
        StudioAlreadyExistsException exception =
            catchThrowableOfType(StudioAlreadyExistsException.class, () -> command.execute(input));

        // then
        assertThat(exception.getStudioName()).isEqualTo(studioName);
        assertThat(exception.getErrorCode()).isEqualTo(StudioErrorCode.STUDIO_ALREADY_EXISTS);

        then(storage).should()
            .findByName(studioName);
        then(storage).should(never())
            .create(any());
    }
}
