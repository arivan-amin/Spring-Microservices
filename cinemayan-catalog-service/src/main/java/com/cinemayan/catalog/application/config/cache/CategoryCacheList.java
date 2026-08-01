package com.cinemayan.catalog.application.config.cache;

import com.cinemayan.core.application.cache.CacheDefinition;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor (access = AccessLevel.PRIVATE)
public final class CategoryCacheList {

    public static final String ALL_STUDIOS = "category.allStudios";
    public static final String STUDIO_BY_ID = "category.studioById";

    public static List<CacheDefinition> getCaches () {
        List<CacheDefinition> list = new ArrayList<>();
        list.add(new CacheDefinition(ALL_STUDIOS, Duration.ofMinutes(10), 1000));
        list.add(new CacheDefinition(STUDIO_BY_ID, Duration.ofMinutes(10), 5000));
        return list;
    }
}
