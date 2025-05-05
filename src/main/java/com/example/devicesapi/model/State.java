package com.example.devicesapi.model;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum State {
    AVAILABLE,
    IN_USE,
    INACTIVE
}
