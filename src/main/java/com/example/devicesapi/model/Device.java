package com.example.devicesapi.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.time.LocalDateTime;

@Builder
@Data
@EqualsAndHashCode(callSuper = false)
@Document(collection = "#{@'mongodb-com.example.devicesapi.properties.DatabaseProperties'.collectionName()}")
@NoArgsConstructor
@AllArgsConstructor
public class Device {

    @Id
    @JsonProperty("id")
    private String id;

    @JsonProperty("name")
    @Field(name = "name")
    private String name;

    @JsonProperty("brand")
    @Field(name = "brand")
    private String brand;

    @JsonProperty("state")
    @Field(name = "state")
    private State state;

    @JsonProperty("creation-time")
    @Field(name = "creation-time")
    private LocalDateTime creationTime;

}
