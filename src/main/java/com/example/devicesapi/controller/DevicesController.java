package com.example.devicesapi.controller;

import com.example.devicesapi.model.Device;
import com.example.devicesapi.service.DevicesService;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/api/devices")
@RequiredArgsConstructor
public class DevicesController {

    private final DevicesService devicesService;

    @PostMapping("/save")
    public ResponseEntity<Object> saveDevice (@RequestBody @NonNull Device device) {
        try {
            return new ResponseEntity<>(devicesService.saveDevice(device), HttpStatus.CREATED);
        } catch (Exception e) {
            log.error(e.getMessage());
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/update/{id}")
    public Device updateDevice(@PathVariable @NonNull String id, @RequestBody @NonNull Device device) {
        return devicesService.updateDevice(id, device);
    }

    @DeleteMapping("/delete/{id}")
    public void deleteDevice(@PathVariable("id") @NonNull String id) {
        devicesService.deleteDevice(id);
    }
}
