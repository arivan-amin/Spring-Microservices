package com.cinemayan.catalog.infrastructure.storage.studio;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.UuidGenerator;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.Instant;
import java.time.LocalDate;
import java.util.UUID;

@Entity
@Table (schema = "catalog", name = "studios")
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@EntityListeners (AuditingEntityListener.class)
@ToString
public class StudioEntity {

    @Id
    @UuidGenerator
    @Column (name = "id")
    private UUID id;

    @Column (name = "name", nullable = false)
    private String name;

    @Column (name = "country", nullable = false)
    private String country;

    @Column (name = "founded_date", nullable = false)
    private LocalDate foundedDate;

    @CreatedDate
    @Column (name = "created_at", nullable = false, updatable = false)
    private Instant createdAt;

    @LastModifiedDate
    @Column (name = "updated_at", nullable = false)
    private Instant updatedAt;
}
