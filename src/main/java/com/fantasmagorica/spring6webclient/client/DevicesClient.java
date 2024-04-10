package com.fantasmagorica.spring6webclient.client;

import com.fasterxml.jackson.databind.JsonNode;
import reactor.core.publisher.Flux;

public interface DevicesClient {

    Flux<JsonNode> getDevices();
}
