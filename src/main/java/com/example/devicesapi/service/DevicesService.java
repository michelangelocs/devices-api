package com.example.devicesapi.service;

import com.example.devicesapi.model.Device;
import com.example.devicesapi.model.DeviceDTO;
import com.example.devicesapi.model.DeviceToCreate;
import com.example.devicesapi.model.DeviceToUpdate;
import com.example.devicesapi.model.State;
import com.example.devicesapi.repositories.DevicesRepository;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

import static com.example.devicesapi.base.Constants.DEVICES_API_DELETE;
import static com.example.devicesapi.base.Constants.DEVICES_API_DELETE_WARN;
import static com.example.devicesapi.base.Constants.DEVICES_API_NOT_FOUND;
import static com.example.devicesapi.base.Constants.DEVICES_API_SAVE;
import static com.example.devicesapi.base.Constants.DEVICES_API_SEARCH;
import static com.example.devicesapi.base.Constants.DEVICES_API_UPDATE;
import static com.example.devicesapi.base.Constants.DEVICES_API_UPDATE_WARN;
import static com.example.devicesapi.base.Validator.validate;

@Slf4j
@Service
@RequiredArgsConstructor
public class DevicesService {

    private final DevicesRepository devicesRepository;

    /**
     * Before saving the device in the DB we check if name and brand are not null (I took this as a personal assumption)
     * We also check if the creationTime was preset, in which case we set it to the current time if not, and also the state is set to
     * AVAILABLE if not stated in the request body
     * */
    public Device saveDevice (@NonNull DeviceToCreate device) {
        validate(device);
        log.info(DEVICES_API_SAVE, device);
        DeviceDTO deviceDTO = devicesRepository.save(device.toDeviceDTO());
        return deviceDTO.toDevice();
    }

    /**
     * Since one of the restrictions was to NOT allow the creationTime to be updated, the update method simply ignores it
     * even if it is passed in the request body.
     * <p>
     * Another remark is regarding the update of name and brand, which only occurs when at the moment of loading the current
     * status of the device the state attribute is not IN_USE. This creates a situtation where sometimes it is needed to update the device
     * twice, to first change the state and then allow to change the other attributes (except id and creationTime)
     * <p>
     * This could be avoided by checking if the device is also being changed to either AVAILABLE or INACTIVE instead of checking the current
     * DB state
     * */
    public Device updateDevice(@NonNull String id, @NonNull DeviceToUpdate device) {
        return devicesRepository.findById(id).map(deviceToUpdate -> {
            if (deviceToUpdate.getState() != State.IN_USE) {
                Optional.ofNullable(device.getName()).ifPresent(deviceToUpdate::setName);
                Optional.ofNullable(device.getBrand()).ifPresent(deviceToUpdate::setBrand);
            } else {
                log.warn(DEVICES_API_UPDATE_WARN, id);
            }
            Optional.ofNullable(device.getState()).ifPresent(deviceToUpdate::setState);
            log.info(DEVICES_API_UPDATE, deviceToUpdate);
            DeviceDTO deviceDTO = devicesRepository.save(deviceToUpdate);
            return deviceDTO.toDevice();
        }).orElseGet(() -> {
            logDeviceNotFound(id);
            throw new IllegalStateException(DEVICES_API_NOT_FOUND);
        });
    }

    /**
     * This was more straightforward, as we only check if the device is being used before we delete it (or not),
     * as stated in the exercise document
     * */
    public void deleteDevice(@NonNull String id) {
        devicesRepository.findById(id).ifPresentOrElse(device -> {
            if (device.getState() == State.IN_USE) {
                log.warn(DEVICES_API_DELETE_WARN, id);
            } else {
                devicesRepository.deleteById(id);
                log.info(DEVICES_API_DELETE, id);
            }
        }, () -> {
            logDeviceNotFound(id);
            throw new IllegalStateException(DEVICES_API_NOT_FOUND);
        });
    }

    public Device getDeviceById(@NonNull String id) {
        log.info(DEVICES_API_SEARCH, id);
        try {
            return Objects.requireNonNull(devicesRepository.findById(id).orElse(null)).toDevice();
        } catch (NullPointerException e) {
            logDeviceNotFound(id);
            return null;
        }
    }

    public List<Device> getAllDevices() {
        return devicesRepository.findAll().stream()
                .map(DeviceDTO::toDevice)
                .toList();
    }

    public List<Device> getDevicesByBrand(@NonNull String brand) {
        return devicesRepository.findByBrand(brand).stream()
                .map(DeviceDTO::toDevice)
                .toList();
    }

    public List<Device> getDevicesByState(@NonNull State state) {
        return devicesRepository.findByState(state).stream()
                .map(DeviceDTO::toDevice)
                .toList();
    }

    private void logDeviceNotFound(@NonNull String id) {
        log.error(DEVICES_API_NOT_FOUND, id);
    }

}
