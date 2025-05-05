package com.example.devicesapi.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties("mongodb")
public record DatabaseProperties(
    int ttlSeconds,
    int quoteTtlSeconds,
    String collectionName) {
}