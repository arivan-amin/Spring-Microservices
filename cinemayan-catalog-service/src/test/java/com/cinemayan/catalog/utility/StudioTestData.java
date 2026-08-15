package com.cinemayan.catalog.utility;

import com.cinemayan.catalog.domain.studio.entity.Studio;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.time.*;
import java.util.List;
import java.util.UUID;

@NoArgsConstructor (access = AccessLevel.PRIVATE)
public final class StudioTestData {

    public static List<Studio> studiosList () {
        return List.of(withName("20th Century Studios"), withName("Warner Bros. Pictures"),
            withName("New Line Cinema"));
    }

    public static Studio withName (String name) {
        Studio studio = new Studio();
        studio.setId(UUID.randomUUID());
        studio.setName(name);
        studio.setCountry("Italy");
        studio.setFoundedDate(LocalDate.of(2010, 5, 7));
        studio.setCreatedAt(Instant.now()
            .minus(Duration.ofDays(15)));
        studio.setUpdatedAt(Instant.now()
            .minus(Duration.ofDays(5)));
        return studio;
    }

    public static Studio withDefaultName () {
        return withName("Universal Pictures");
    }
}
