package com.example.devicesapi.model;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Device {

    private String id;
    private String name;
    private String brand;
    private State state;
    private LocalDateTime creationTime;

}
