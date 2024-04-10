package com.fantasmagorica.spring6webclient.client;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.concurrent.atomic.AtomicBoolean;

import static org.awaitility.Awaitility.await;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class DevicesClientImplTest {

    @Autowired
    DevicesClient devicesClient;

    @Test
    void getDevices() {
        AtomicBoolean atomicBoolean = new AtomicBoolean(false);

        devicesClient.getDevices().subscribe(response -> {
            System.out.println(response.toPrettyString());
            atomicBoolean.set(true);
        });

        await().untilTrue(atomicBoolean);
    }
}