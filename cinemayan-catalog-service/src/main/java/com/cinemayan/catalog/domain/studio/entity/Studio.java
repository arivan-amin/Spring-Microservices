package com.cinemayan.catalog.domain.studio.entity;

import lombok.*;

import java.time.Instant;
import java.time.LocalDate;
import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Studio {

    private UUID id;
    private String name;
    private String country;
    private LocalDate foundedDate;
    private Instant createdAt;
    private Instant updatedAt;
}
