package com.example.devicesapi.base;

public class Constants {
    protected Constants() {
        throw new IllegalStateException("Constants class");
    }

    public static final String DEVICES_API_SAVE= "DEVICES_API_SAVE: Saving device : {}";
    public static final String DEVICES_API_UPDATE= "DEVICES_API_UPDATE: Updating device : {}";
    public static final String DEVICES_API_UPDATE_WARN= "DEVICES_API_UPDATE_WARN: Device with id '{}' is in use and cannot update 'name' nor 'brand'";
    public static final String DEVICES_API_DELETE= "DEVICES_API_DELETE: Device with id '{}' deleted";
    public static final String DEVICES_API_DELETE_WARN= "DEVICES_API_DELETE_WARN: Device with id '{}' is in use and cannot be deleted";
    public static final String DEVICES_API_NOT_FOUND= "DEVICES_API_NOT_FOUND: Device with id '{}' not found";
    public static final String DEVICES_API_REQUIRED_NAME= "DEVICES_API_REQUIRED_NAME: Device name is required";
    public static final String DEVICES_API_REQUIRED_BRAND= "DEVICES_API_REQUIRED_BRAND: Device brand is required";
    public static final String DEVICES_API_SEARCH = "DEVICES_API_SEARCH: Searching for device with id '{}'";



}
