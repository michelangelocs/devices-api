package com.example.devicesapi.base;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;


class ConstantsTest {

    @Test
    void testConstantsValues() {
        Assertions.assertEquals("DEVICES_API_SAVE: Saving device : {}", Constants.DEVICES_API_SAVE);
        Assertions.assertEquals("DEVICES_API_UPDATE: Updating device : {}", Constants.DEVICES_API_UPDATE);
        Assertions.assertEquals("DEVICES_API_DELETE: Device with id '{}' deleted", Constants.DEVICES_API_DELETE);
        Assertions.assertEquals("DEVICES_API_DELETE_WARN: Device with id '{}' is in use and cannot be deleted", Constants.DEVICES_API_DELETE_WARN);
        Assertions.assertEquals("DEVICES_API_NOT_FOUND: Device with id '{}' not found", Constants.DEVICES_API_NOT_FOUND);
    }

    @Test
    void testConstantsConstructor() {
        Assertions.assertThrows(IllegalStateException.class, Constants::new, "Constants class");
    }

}
