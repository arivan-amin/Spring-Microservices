package com.cinemayan.core.application.cache;

import lombok.Value;

import java.time.Duration;

@Value
public class CacheDefinition {

    String name;
    Duration ttl;
    int maxSize;
}
