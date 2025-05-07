package com.example.devicesapi.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DeviceToCreate {

    @JsonProperty("name")
    private String name;

    @JsonProperty("brand")
    private String brand;

    @JsonProperty("state")
    private State state;

    @JsonProperty("creation-time")
    private LocalDateTime creationTime;

    public DeviceDTO toDeviceDTO() {
        return DeviceDTO.builder()
                .name(this.getName())
                .brand(this.getBrand())
                .state(this.getState())
                .creationTime(this.getCreationTime())
                .build();
    }

}
