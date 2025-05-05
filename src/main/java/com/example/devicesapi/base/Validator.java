package com.example.devicesapi.base;

import com.example.devicesapi.model.Device;
import com.example.devicesapi.model.State;

import java.time.LocalDateTime;
import java.util.Optional;

public class Validator {

    protected Validator () {
        throw new IllegalStateException("Validator class");
    }

    public static void validate(Device device) {
        device.setCreationTime(Optional.ofNullable(device.getCreationTime()).orElse(LocalDateTime.now()));
        device.setState(Optional.ofNullable(device.getState()).orElse(State.AVAILABLE));

        if (Optional.ofNullable(device.getName()).orElse("").isEmpty()) {
            throw new IllegalArgumentException("Device name is required");
        }
        if (Optional.ofNullable(device.getBrand()).orElse("").isEmpty()) {
            throw new IllegalArgumentException("Device brand is required");
        }
    }


}
