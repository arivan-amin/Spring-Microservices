package com.cinemayan.catalog.application.endpoints;

import com.cinemayan.catalog.application.config.cache.CategoryCacheList;
import com.cinemayan.catalog.application.request.studio.CreateStudioRequest;
import com.cinemayan.catalog.application.request.studio.UpdateStudioRequest;
import com.cinemayan.catalog.application.response.GetStudiosResponse;
import com.cinemayan.catalog.application.response.StudioResponse;
import com.cinemayan.catalog.application.urls.StudioApiURLs;
import com.cinemayan.catalog.domain.studio.command.*;
import com.cinemayan.catalog.domain.studio.persistence.GetStudiosParams;
import com.cinemayan.catalog.domain.studio.query.GetStudioByIdQuery;
import com.cinemayan.catalog.domain.studio.query.GetStudiosQuery;
import com.cinemayan.core.domain.pagination.PaginationCriteria;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.time.Instant;
import java.util.UUID;

import static com.cinemayan.core.domain.util.MappingUtility.mapIfNotNull;

@Tag (name = "Studio Controller")
@Validated
@RestController
@RequiredArgsConstructor
@Slf4j
class StudioController {

    private final GetStudiosQuery studiosQuery;
    private final GetStudioByIdQuery studioByIdQuery;
    private final CreateStudioCommand createCommand;
    private final UpdateStudioCommand updateCommand;
    private final DeleteStudioCommand deleteCommand;

    @GetMapping (StudioApiURLs.GET_STUDIOS_URL)
    @Cacheable (CategoryCacheList.ALL_STUDIOS)
    @Operation (summary = "Get a list of studios")
    @ResponseStatus (HttpStatus.OK)
    public GetStudiosResponse getAllStudios (
        @RequestParam (name = "search", required = false) String searchQuery,
        @RequestParam (required = false) Long startDate,
        @RequestParam (required = false) Long endDate, @Valid PaginationCriteria criteria) {
        GetStudiosParams params =
            new GetStudiosParams(searchQuery, mapIfNotNull(startDate, Instant::ofEpochMilli),
                mapIfNotNull(endDate, Instant::ofEpochMilli));

        GetStudiosQuery.Input input = new GetStudiosQuery.Input(params, criteria);
        GetStudiosQuery.Output output = studiosQuery.execute(input);
        return GetStudiosResponse.of(output.getStudios());
    }

    @GetMapping (StudioApiURLs.GET_STUDIO_BY_ID_URL)
    @Cacheable (CategoryCacheList.STUDIO_BY_ID)
    @Operation (summary = "Get a studio by ID")
    @ResponseStatus (HttpStatus.OK)
    public StudioResponse getStudioById (@PathVariable UUID id) {
        GetStudioByIdQuery.Input input = new GetStudioByIdQuery.Input(id);
        GetStudioByIdQuery.Output output = studioByIdQuery.execute(input);
        return StudioResponse.of(output.getStudio());
    }

    @PostMapping (StudioApiURLs.CREATE_STUDIO_URL)
    @Operation (summary = "Creates a studio")
    @CacheEvict (cacheNames = { CategoryCacheList.ALL_STUDIOS, CategoryCacheList.STUDIO_BY_ID },
        allEntries = true)
    @ResponseStatus (HttpStatus.CREATED)
    public StudioResponse createStudio (@RequestBody @Valid CreateStudioRequest request) {
        CreateStudioCommand.Output output = createCommand.execute(request.toInput());
        return StudioResponse.of(output.getStudio());
    }

    @PutMapping (StudioApiURLs.UPDATE_STUDIO_URL)
    @Operation (summary = "Updates a studio")
    @CacheEvict (cacheNames = { CategoryCacheList.ALL_STUDIOS, CategoryCacheList.STUDIO_BY_ID },
        allEntries = true)
    @ResponseStatus (HttpStatus.OK)
    public StudioResponse updateStudio (@PathVariable UUID id,
                                        @RequestBody @Valid UpdateStudioRequest request) {
        UpdateStudioCommand.Output output = updateCommand.execute(request.toInput(id));
        return StudioResponse.of(output.getStudio());
    }

    @DeleteMapping (StudioApiURLs.DELETE_STUDIO_URL)
    @Operation (summary = "Deletes a studio")
    @CacheEvict (cacheNames = { CategoryCacheList.ALL_STUDIOS, CategoryCacheList.STUDIO_BY_ID },
        allEntries = true)
    @ResponseStatus (HttpStatus.NO_CONTENT)
    public void deleteStudio (@PathVariable UUID id) {
        DeleteStudioCommand.Input input = new DeleteStudioCommand.Input(id);
        deleteCommand.execute(input);
    }
}
