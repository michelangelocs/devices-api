package com.example.devicesapi.controller;

import com.example.devicesapi.model.Device;
import com.example.devicesapi.model.DeviceDTO;
import com.example.devicesapi.model.DeviceToCreate;
import com.example.devicesapi.model.DeviceToUpdate;
import com.example.devicesapi.model.State;
import com.example.devicesapi.service.DevicesService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.List;

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
        when(devicesService.saveDevice(any(DeviceToCreate.class))).thenReturn(mock(Device.class));
        assertNotNull(devicesController.saveDevice(getDeviceToCreate(State.IN_USE)));
    }

    @Test
    void saveDevice_null() {
        assertThrows(NullPointerException.class, () -> devicesController.saveDevice(null));
    }

    @Test
    void updateDevice_success () {
        when(devicesService.updateDevice(anyString() ,any(DeviceToUpdate.class))).thenReturn(mock(Device.class));
        assertNotNull(devicesController.updateDevice("id" ,getDeviceToUpdate(State.AVAILABLE)));
    }

    @Test
    void updateDevice_null() {
        assertThrows(NullPointerException.class, () -> devicesController.updateDevice(null, getDeviceToUpdate(State.AVAILABLE)));
    }


    @Test
    void device_success() {
        when(devicesService.getDeviceById(anyString())).thenReturn(mock(Device.class));
        assertNotNull(devicesController.getDevice("id"));
    }

    @Test
    void device_null() {
        assertThrows(NullPointerException.class, () -> devicesController.getDevice(null));
    }

    @Test
    void devices_success_all () {
        when(devicesService.getAllDevices()).thenReturn(mock(List.class));
        assertNotNull(devicesController.getDeviceByParams(null, null));
    }

    @Test
    void devices_success_by_brand () {
        when(devicesService.getDevicesByBrand("brand")).thenReturn(mock(List.class));
        assertNotNull(devicesController.getDeviceByParams("brand", null));
    }

    @Test
    void devices_success_by_brand_and_state () {
        when(devicesService.getDevicesByBrand("brand")).thenReturn(mock(List.class));
        assertNotNull(devicesController.getDeviceByParams("brand", State.AVAILABLE));
    }

    @Test
    void devices_success_by_state () {
        when(devicesService.getDevicesByState(State.AVAILABLE)).thenReturn(mock(List.class));
        assertNotNull(devicesController.getDeviceByParams(null, State.AVAILABLE));
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



    private DeviceToCreate getDeviceToCreate (State state) {
        return DeviceToCreate.builder()
                .name("name")
                .brand("brand")
                .state(state)
                .creationTime(LocalDateTime.now())
                .build();
    }

    private DeviceToUpdate getDeviceToUpdate (State state) {
        return DeviceToUpdate.builder()
                .name("name")
                .brand("brand")
                .state(state)
                .build();
    }
}
