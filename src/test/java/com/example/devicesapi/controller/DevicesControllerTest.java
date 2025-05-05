package com.example.devicesapi.controller;

import com.example.devicesapi.model.Device;
import com.example.devicesapi.model.State;
import com.example.devicesapi.repositories.DevicesRepository;
import com.example.devicesapi.service.DevicesService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class DevicesControllerTest {

    @InjectMocks
    private DevicesController devicesController;

    @Mock
    private DevicesService devicesService;


    @Test
    void saveDevice_success() {
        when(devicesService.saveDevice(any(Device.class))).thenReturn(mock(Device.class));
        assertNotNull(devicesController.saveDevice(getDevice(State.IN_USE)));
    }

    @Test
    void saveDevice_null() {
        assertThrows(NullPointerException.class, () -> devicesController.saveDevice(null));
    }

    @Test
    void updateDevice_success () {
        when(devicesService.updateDevice(anyString() ,any(Device.class))).thenReturn(mock(Device.class));
        assertNotNull(devicesController.updateDevice("id" ,getDevice(State.AVAILABLE)));
    }

    @Test
    void updateDevice_null() {
        assertThrows(NullPointerException.class, () -> devicesController.updateDevice(null, getDevice(State.AVAILABLE)));
    }

    @Test
    void deleteDevice_success () {
        devicesController.deleteDevice("id");
        verify(devicesService, times(1)).deleteDevice(anyString());
    }

    @Test
    void deleteDevice_null() {
        assertThrows(NullPointerException.class, () -> devicesController.deleteDevice(null));
    }

    private Device getDevice(State state) {
        return Device.builder()
                .id("id")
                .name("name")
                .brand("brand")
                .state(state)
                .creationTime(LocalDateTime.now())
                .build();
    }


}
