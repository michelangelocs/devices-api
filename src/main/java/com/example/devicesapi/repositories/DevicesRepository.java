package com.example.devicesapi.repositories;

import com.example.devicesapi.model.DeviceDTO;
import com.example.devicesapi.model.State;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface DevicesRepository extends MongoRepository<DeviceDTO, String> {
    DeviceDTO save (DeviceDTO device);
    Optional<DeviceDTO> findById (String id);
    List<DeviceDTO> findByState (State state);
    List<DeviceDTO> findByBrand (String brand);
}
