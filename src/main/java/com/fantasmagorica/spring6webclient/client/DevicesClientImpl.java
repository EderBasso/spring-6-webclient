package com.fantasmagorica.spring6webclient.client;

import com.fasterxml.jackson.databind.JsonNode;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;

@Service
public class DevicesClientImpl implements DevicesClient {

    private final WebClient webClient;

    public static final String BEER_PATH = "https://api.restful-api.dev/objects";

    public DevicesClientImpl(WebClient.Builder clientBuilder) {
        this.webClient = clientBuilder.baseUrl(BEER_PATH).build();
    }

    @Override
    public Flux<JsonNode> getDevices() {
        return webClient.get()
                .retrieve().bodyToFlux(JsonNode.class);
    }
}
