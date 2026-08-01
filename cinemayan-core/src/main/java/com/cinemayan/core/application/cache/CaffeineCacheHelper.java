package com.cinemayan.core.application.cache;

import com.github.benmanes.caffeine.cache.Caffeine;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor (access = AccessLevel.PRIVATE)
public final class CaffeineCacheHelper {

    public static Caffeine<Object, Object> toCaffeine (CacheDefinition cacheDefinition) {
        return Caffeine.newBuilder()
            .expireAfterWrite(cacheDefinition.getTtl())
            .maximumSize(cacheDefinition.getMaxSize())
            .recordStats();
    }
}
