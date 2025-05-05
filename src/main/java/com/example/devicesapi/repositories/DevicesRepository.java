package com.example.devicesapi.repositories;

import com.example.devicesapi.model.Device;
import com.example.devicesapi.model.State;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface DevicesRepository extends MongoRepository<Device, String> {
    Device save (Device device);
    Optional<Device> findById (String id);
    List<Device> findByState (State state);
    List<Device> findByBrand (String brand);
}
