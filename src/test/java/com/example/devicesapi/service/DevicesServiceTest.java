package com.example.devicesapi.service;

import ch.qos.logback.classic.Level;
import ch.qos.logback.classic.Logger;
import ch.qos.logback.classic.spi.LoggingEvent;
import ch.qos.logback.core.Appender;
import com.example.devicesapi.model.Device;
import com.example.devicesapi.model.DeviceDTO;
import com.example.devicesapi.model.DeviceToCreate;
import com.example.devicesapi.model.DeviceToUpdate;
import com.example.devicesapi.model.State;
import com.example.devicesapi.repositories.DevicesRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.slf4j.LoggerFactory;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class DevicesServiceTest {

    @InjectMocks
    private DevicesService devicesService;

    @Mock
    private DevicesRepository devicesRepository;

    @Mock
    private Appender appenderMock;

    @Captor
    private ArgumentCaptor<LoggingEvent> logEvenCaptor;

    @BeforeEach
    void setUp() {
        final Logger root = (Logger) LoggerFactory.getLogger(DevicesService.class);
        root.addAppender(this.appenderMock);
        root.setLevel(Level.ALL);
    }


    @Test
    void saveDevice_success () {
        when(devicesRepository.save(any(DeviceDTO.class))).thenReturn(getDevice(State.AVAILABLE));
        devicesService.saveDevice(DeviceToCreate.builder().name("name").brand("brand").build());
        ArgumentCaptor<DeviceDTO> deviceArgumentCaptor = ArgumentCaptor.forClass(DeviceDTO.class);
        verify(devicesRepository).save(deviceArgumentCaptor.capture());
        DeviceDTO savedDevice = deviceArgumentCaptor.getValue();

        assertNotNull(savedDevice);
    }

    @Test
    void saveDevice_failure () {
        assertThrows(IllegalArgumentException.class, ()-> devicesService.saveDevice(new DeviceToCreate()));
    }

    @Test
    void saveDevice_failure2 () {
        assertThrows(IllegalArgumentException.class, ()-> devicesService.saveDevice(DeviceToCreate.builder().name("name").build()));
    }

    @Test
    void saveDevice_null () {
        assertThrows(NullPointerException.class, () -> devicesService.saveDevice(null));
    }

    @Test
    void updateDevice_success () {
        when(devicesRepository.findById(any(String.class))).thenReturn(Optional.ofNullable(getDevice(State.AVAILABLE)));
        when(devicesRepository.save(any(DeviceDTO.class))).thenReturn(getDevice(State.IN_USE));
        assertNotNull(devicesService.updateDevice("id", getDeviceToUpdate(State.IN_USE)));
    }

    @Test
    void updateDevice_success2 () {
        when(devicesRepository.findById(any(String.class))).thenReturn(Optional.ofNullable(getDevice(State.IN_USE)));
        when(devicesRepository.save(any(DeviceDTO.class))).thenReturn(getDevice(State.INACTIVE));

        assertNotNull(devicesService.updateDevice("id", getDeviceToUpdate(State.INACTIVE)));

        verify(this.appenderMock, times(2)).doAppend(this.logEvenCaptor.capture());
        assertEquals(Level.WARN, logEvenCaptor.getAllValues().getFirst().getLevel());
        assertTrue(logEvenCaptor.getAllValues().getFirst().getFormattedMessage().contains("DEVICES_API_UPDATE_WARN"));
    }

    @Test
    void updateDevice_failure () {
        when(devicesRepository.findById(any(String.class))).thenReturn(Optional.empty());
        assertThrows(IllegalStateException.class, () -> devicesService.updateDevice("id", getDeviceToUpdate(State.INACTIVE)));

        verify(this.appenderMock, times(1)).doAppend(this.logEvenCaptor.capture());
        assertEquals(Level.ERROR, logEvenCaptor.getAllValues().getFirst().getLevel());
        assertTrue(logEvenCaptor.getAllValues().getFirst().getFormattedMessage().contains("DEVICES_API_NOT_FOUND"));
    }

    @Test
    void updateDevice_null () {
        assertThrows(NullPointerException.class, () -> devicesService.updateDevice("id", null));
    }

    @Test
    void updateDevice_null2 () {
        assertThrows(NullPointerException.class, () -> devicesService.updateDevice(null, new DeviceToUpdate()));
    }

    @Test
    void deleteDevice_success () {
        when(devicesRepository.findById(any(String.class))).thenReturn(Optional.ofNullable(getDevice(State.AVAILABLE)));
        devicesService.deleteDevice("id");

        verify(this.appenderMock, times(1)).doAppend(this.logEvenCaptor.capture());
        assertEquals(Level.INFO, logEvenCaptor.getAllValues().getFirst().getLevel());
        assertTrue(logEvenCaptor.getAllValues().getFirst().getFormattedMessage().contains("DEVICES_API_DELETE"));
    }

    @Test
    void deleteDevice_success2 () {
        when(devicesRepository.findById(any(String.class))).thenReturn(Optional.ofNullable(getDevice(State.IN_USE)));
        devicesService.deleteDevice("id");

        verify(this.appenderMock, times(1)).doAppend(this.logEvenCaptor.capture());
        assertEquals(Level.WARN, logEvenCaptor.getAllValues().getFirst().getLevel());
        assertTrue(logEvenCaptor.getAllValues().getFirst().getFormattedMessage().contains("DEVICES_API_DELETE_WARN"));
    }

    @Test
    void deleteDevice_failure () {
        when(devicesRepository.findById(any(String.class))).thenReturn(Optional.empty());
        assertThrows(IllegalStateException.class, () -> devicesService.deleteDevice("id"));

        verify(this.appenderMock, times(1)).doAppend(this.logEvenCaptor.capture());
        assertEquals(Level.ERROR, logEvenCaptor.getAllValues().getFirst().getLevel());
        assertTrue(logEvenCaptor.getAllValues().getFirst().getFormattedMessage().contains("DEVICES_API_NOT_FOUND"));
    }

    @Test
    void deleteDevice_null () {
        assertThrows(NullPointerException.class, () -> devicesService.deleteDevice( null));
    }

    @Test
    void getDeviceById_success () {
        when(devicesRepository.findById(any(String.class))).thenReturn(Optional.ofNullable(getDevice(State.AVAILABLE)));
        assertNotNull(devicesService.getDeviceById("id"));
    }

    @Test
    void getDeviceById_null () {
        assertThrows(NullPointerException.class, () -> devicesService.getDeviceById( null));
    }

    @Test
    void getAllDevices_success () {
        when(devicesRepository.findAll()).thenReturn(List.of(getDevice(State.AVAILABLE), getDevice(State.INACTIVE), getDevice(State.IN_USE)));
        List<Device> devices = devicesService.getAllDevices();
        assertNotNull(devices);
        assertEquals(3, devices.size());
    }


    private DeviceDTO getDevice (State state) {
        return DeviceDTO.builder()
                .id("id")
                .name("name")
                .brand("brand")
                .state(state)
                .creationTime(LocalDateTime.now())
                .build();
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
