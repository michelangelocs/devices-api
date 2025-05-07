package com.example.devicesapi.controller;

import com.example.devicesapi.model.Device;
import com.example.devicesapi.model.DeviceDTO;
import com.example.devicesapi.model.State;
import com.example.devicesapi.service.DevicesService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class DevicesGraphQLControllerTest {

    @InjectMocks
    private DevicesGraphQLController devicesGraphQLController;

    @Mock
    private DevicesService devicesService;

    @Test
    void device_success() {
        when(devicesService.getDeviceById(anyString())).thenReturn(mock(Device.class));
        assertNotNull(devicesGraphQLController.device("id"));
    }

    @Test
    void device_null() {
        assertThrows(NullPointerException.class, () -> devicesGraphQLController.device(null));
    }

    @Test
    void devices_success_all () {
        when(devicesService.getAllDevices()).thenReturn(mock(List.class));
        assertNotNull(devicesGraphQLController.devices(null, null));
    }

    @Test
    void devices_success_by_brand () {
        when(devicesService.getDevicesByBrand("brand")).thenReturn(mock(List.class));
        assertNotNull(devicesGraphQLController.devices("brand", null));
    }

    @Test
    void devices_success_by_brand_and_state () {
        when(devicesService.getDevicesByBrand("brand")).thenReturn(mock(List.class));
        assertNotNull(devicesGraphQLController.devices("brand", State.AVAILABLE));
    }

    @Test
    void devices_success_by_state () {
        when(devicesService.getDevicesByState(State.AVAILABLE)).thenReturn(mock(List.class));
        assertNotNull(devicesGraphQLController.devices(null, State.AVAILABLE));
    }
}
