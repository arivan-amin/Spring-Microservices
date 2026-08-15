package com.cinemayan.catalog.infrastructure.storage.studio;

import com.cinemayan.catalog.domain.studio.entity.Studio;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor (access = AccessLevel.PRIVATE)
public final class StudioMapper {

    public static StudioEntity fromDomain (Studio studio) {
        return StudioEntity.builder()
            .id(studio.getId())
            .name(studio.getName())
            .country(studio.getCountry())
            .foundedDate(studio.getFoundedDate())
            .createdAt(studio.getCreatedAt())
            .updatedAt(studio.getUpdatedAt())
            .build();
    }

    public static Studio toDomain (StudioEntity entity) {
        return Studio.builder()
            .id(entity.getId())
            .name(entity.getName())
            .country(entity.getCountry())
            .foundedDate(entity.getFoundedDate())
            .createdAt(entity.getCreatedAt())
            .updatedAt(entity.getUpdatedAt())
            .build();
    }
}
