package com.cinemayan.catalog.infrastructure.storage.studio;

import com.cinemayan.catalog.domain.studio.entity.Studio;
import com.cinemayan.catalog.utility.StudioTestData;
import com.cinemayan.testing.architecture.bases.BaseUnitTest;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

@Slf4j
class StudioMapperTest implements BaseUnitTest {

    @Test
    void fromDomain_shouldMapAllFields_whenStudioIsValid () {
        // given
        Studio studio = StudioTestData.withName("Some studio");
        studio.setId(UUID.randomUUID());

        // when
        StudioEntity result = StudioMapper.fromDomain(studio);

        // then
        assertThat(result).usingRecursiveComparison()
            .isEqualTo(studio);
    }

    @Test
    void toDomain_shouldMapAllFields_whenStudioEntityIsValid () {
        // given
        StudioEntity entity = StudioMapper.fromDomain(StudioTestData.withName("mgm"));
        entity.setId(UUID.randomUUID());

        // when
        Studio result = StudioMapper.toDomain(entity);

        // then
        assertThat(result).usingRecursiveComparison()
            .isEqualTo(entity);
    }

    @Test
    void roundTrip_shouldPreserveAllFields_whenMappingToDomainAndBack () {
        // given
        Studio studio = StudioTestData.withDefaultName();
        studio.setId(UUID.randomUUID());

        // when
        StudioEntity entity = StudioMapper.fromDomain(studio);
        Studio result = StudioMapper.toDomain(entity);

        // then
        assertThat(result).usingRecursiveComparison()
            .isEqualTo(studio);
    }
}
