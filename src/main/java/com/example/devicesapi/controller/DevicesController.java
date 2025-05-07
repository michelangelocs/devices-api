package com.example.devicesapi.controller;

import com.example.devicesapi.model.Device;
import com.example.devicesapi.model.DeviceDTO;
import com.example.devicesapi.model.DeviceToCreate;
import com.example.devicesapi.model.DeviceToUpdate;
import com.example.devicesapi.model.State;
import com.example.devicesapi.service.DevicesService;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/devices")
@RequiredArgsConstructor
public class DevicesController {

    private final DevicesService devicesService;

    @PostMapping("/save")
    public ResponseEntity<Device> saveDevice (@RequestBody @NonNull DeviceToCreate device) {
        try {
            log.info("Received device {}", device);
            return new ResponseEntity<>(devicesService.saveDevice(device), HttpStatus.CREATED);
        } catch (Exception e) {
            log.error(e.getMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<Device> updateDevice(@PathVariable @NonNull String id, @RequestBody @NonNull DeviceToUpdate device) {
        try {
            return new ResponseEntity<>(devicesService.updateDevice(id, device), HttpStatus.OK);
        } catch (Exception e) {
            log.error(e.getMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("get/{id}")
    public ResponseEntity<Device> getDevice(@PathVariable @NonNull String id) {
        try {
            return new ResponseEntity<>(devicesService.getDeviceById(id), HttpStatus.OK);
        } catch (Exception e) {
            log.error(e.getMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/get")
    public ResponseEntity<List<Device>> getDeviceByParams(
            @RequestParam(value = "brand", required = false) String brand,
            @RequestParam(value = "state", required = false) State state) {
        try {
            if (brand == null && state == null) {
                return new ResponseEntity<>(devicesService.getAllDevices(), HttpStatus.OK);
            }

            if (brand != null) {
                List<Device> list = devicesService.getDevicesByBrand(brand);
                return state != null ?
                        new ResponseEntity<>(list.stream().filter(device -> device.getState().equals(state)).toList(), HttpStatus.OK)
                        : new ResponseEntity<>(list, HttpStatus.OK);
            }
            return new ResponseEntity<>(devicesService.getDevicesByState(state), HttpStatus.OK);
        } catch (Exception e) {
            log.error(e.getMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteDevice(@PathVariable("id") @NonNull String id) {
        try {
            devicesService.deleteDevice(id);
        } catch (Exception e) {
            log.error(e.getMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return null;
    }
}
