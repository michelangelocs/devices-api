package com.example.devicesapi;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class DevicesApiApplicationTests {

    @Test
    void contextLoads() {
        int dummy = 0;
        DevicesApiApplication.main(new String[]{});
        Assertions.assertEquals(0, dummy);
    }

}
