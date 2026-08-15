package com.cinemayan.catalog.infrastructure.storage.studio;

import com.cinemayan.catalog.domain.studio.entity.Studio;
import com.cinemayan.catalog.domain.studio.persistence.GetStudiosParams;
import com.cinemayan.catalog.domain.studio.persistence.StudioStorage;
import com.cinemayan.core.domain.pagination.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.*;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@SuppressWarnings ("PMD.TooManyMethods")
@RequiredArgsConstructor
@Slf4j
public class StudioJpaStorage implements StudioStorage {

    private static final String DEFAULT_SORT_FIELD = "foundedDate";

    private final StudioRepository repository;

    @Transactional (readOnly = true)
    @Override
    public PaginatedResponse<Studio> findAll (GetStudiosParams params,
                                              PaginationCriteria criteria) {
        log.debug("Fetching studios with params = {}, criteria = {}", params, criteria);

        StudioSpecification specification = buildSpecification(params);
        PageRequest pageable = buildPageRequest(criteria);
        log.debug("Created specification = {}, pageable = {}", specification, pageable);

        Page<StudioEntity> resultPage = repository.findAll(specification, pageable);
        List<Studio> studios = mapEntitiesToDomain(resultPage.getContent());
        PageData pageData = extractPageData(resultPage);
        log.info("Found {} studios out of {} total", studios.size(), pageData.totalElements());

        return PaginatedResponse.of(pageData, studios);
    }

    private static StudioSpecification buildSpecification (GetStudiosParams params) {
        return new StudioSpecification(params.getSearchQuery(), params.getStartDate(),
            params.getEndDate());
    }

    private static PageRequest buildPageRequest (PaginationCriteria criteria) {
        Sort sort = buildSort(criteria);
        return PageRequest.of(criteria.getPage(), criteria.getSize(), sort);
    }

    private static List<Studio> mapEntitiesToDomain (List<StudioEntity> entities) {
        return entities.stream()
            .map(StudioMapper::toDomain)
            .toList();
    }

    private static PageData extractPageData (Page<?> page) {
        return PageData.of(page.getNumber(), page.getTotalPages(), page.getSize(),
            page.getTotalElements());
    }

    private static Sort buildSort (PaginationCriteria criteria) {
        String field = resolveEffectiveSortField(criteria.getSortField());
        Sort.Direction direction = resolveEffectiveSortDirection(criteria.getSortDirection());
        return Sort.by(direction, field);
    }

    private static String resolveEffectiveSortField (String requestedField) {
        return (requestedField != null && !requestedField.isBlank()) ? requestedField :
            DEFAULT_SORT_FIELD;
    }

    private static Sort.Direction resolveEffectiveSortDirection (Direction direction) {
        if (direction == null) {
            return Sort.Direction.DESC;
        }
        return switch (direction) {
            case ASC -> Sort.Direction.ASC;
            case DESC -> Sort.Direction.DESC;
        };
    }

    @Transactional (readOnly = true)
    @Override
    public Optional<Studio> findById (UUID id) {
        return repository.findById(id)
            .map(StudioMapper::toDomain);
    }

    @Transactional (readOnly = true)
    @Override
    public Optional<Studio> findByName (String name) {
        log.info("Finding studio by name = {}", name);
        return repository.findByName(name)
            .map(StudioMapper::toDomain);
    }

    @Transactional
    @Override
    public Studio create (Studio studio) {
        StudioEntity savedEntity = repository.save(StudioMapper.fromDomain(studio));
        return StudioMapper.toDomain(savedEntity);
    }

    @Transactional
    @Override
    public Studio update (Studio studio) {
        StudioEntity entity = repository.findById(studio.getId())
            .orElseThrow();

        entity.setName(studio.getName());
        entity.setCountry(studio.getCountry());
        entity.setFoundedDate(studio.getFoundedDate());

        StudioEntity updatedEntity = repository.save(entity);
        return StudioMapper.toDomain(updatedEntity);
    }

    @Transactional
    @Override
    public void delete (UUID id) {
        repository.deleteById(id);
    }
}
