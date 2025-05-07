package com.example.devicesapi.controller;


import com.example.devicesapi.model.Device;
import com.example.devicesapi.model.DeviceDTO;
import com.example.devicesapi.model.State;
import com.example.devicesapi.service.DevicesService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@Slf4j
public class DevicesGraphQLController {

    private final DevicesService devicesService;

    /**
     * Since it was stated that we would only filter by brand and state, I thought of using GRAPHQL to perform all the
     * possible combinations in one query, which searches based on what is included in the query variables.
     * */
    @Operation
    @QueryMapping
    public List<Device> devices (@Argument String brand, @Argument State state) {
        if (brand == null && state == null) {
            return devicesService.getAllDevices();
        }

        if (brand != null) {
            List<Device> list = devicesService.getDevicesByBrand(brand);
            return state != null ? list.stream().filter(device -> device.getState().equals(state)).toList() : list;
        }

        return devicesService.getDevicesByState(state);
    }

    /**
     * This one performs the fetching of a single device, but it could have also been integrated in the previous query
     * I assumed that there would be a reason to specify a query for a single device and a query for multiple devices based on search criteria.
     * */
    @Operation
    @QueryMapping
    public Device device (@Argument @NonNull String id) {
        return devicesService.getDeviceById(id);
    }

}
