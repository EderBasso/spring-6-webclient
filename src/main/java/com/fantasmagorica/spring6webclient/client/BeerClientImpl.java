package com.fantasmagorica.spring6webclient.client;

import com.fantasmagorica.spring6webclient.model.BeerDTO;
import com.fasterxml.jackson.databind.JsonNode;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Map;

@Service
public class BeerClientImpl implements BeerClient {

    private final WebClient webClient;

    public static final String BEER_PATH = "/api/v3/beer/";

    public static final String BEER_PATH_ID = "/api/v3/beer/{beerId}";

    public BeerClientImpl(WebClient.Builder builder) {
        this.webClient = builder.build();
    }

    @Override
    public Flux<String> getBeers() {
        return webClient.get().uri(BEER_PATH, String.class)
                .retrieve().bodyToFlux(String.class);
    }

    @Override
    public Flux<Map> getBeersMap() {
        return webClient.get().uri(BEER_PATH, Map.class)
                .retrieve().bodyToFlux(Map.class);
    }

    @Override
    public Flux<JsonNode> getBeersJson() {
        return webClient.get().uri(BEER_PATH, JsonNode.class)
                .retrieve().bodyToFlux(JsonNode.class);
    }

    @Override
    public Flux<BeerDTO> getBeersDTO() {
        return webClient.get().uri(BEER_PATH, BeerDTO.class)
                .retrieve().bodyToFlux(BeerDTO.class);
    }

    @Override
    public Mono<BeerDTO> getBeerById(String id) {
        return webClient.get().uri(uriBuilder -> uriBuilder.path(BEER_PATH_ID).build(id))
                .retrieve().bodyToMono(BeerDTO.class);
    }

    @Override
    public Flux<BeerDTO> getBeersByStyle(String style) {
        return webClient.get().uri(uriBuilder -> uriBuilder.path(BEER_PATH).queryParam("beerStyle", style).build())
                .retrieve().bodyToFlux(BeerDTO.class);
    }

    @Override
    public Mono<BeerDTO> createBeer(BeerDTO beer) {
        return webClient.post().uri(BEER_PATH)
                .body(Mono.just(beer), BeerDTO.class)
                .retrieve()
                .toBodilessEntity()
                .flatMap(voidResponseEntity -> Mono.just(voidResponseEntity
                        .getHeaders()
                        .get("Location")
                        .get(0)))
                .map(path -> path.split("/")[path.split("/").length -1])
                .flatMap(this::getBeerById);
    }

    @Override
    public Mono<BeerDTO> updateBeer(BeerDTO beer) {
        return webClient.put().uri(uriBuilder -> uriBuilder.path(BEER_PATH_ID).build(beer.getId()))
                .body(Mono.just(beer), BeerDTO.class)
                .retrieve()
                .toBodilessEntity()
                .flatMap(voidResponseEntity -> getBeerById(beer.getId()));
    }

    @Override
    public Mono<Void> deleteBeer(String id) {
        return webClient.delete().uri(uriBuilder -> uriBuilder.path(BEER_PATH_ID).build(id))
                .retrieve()
                .toBodilessEntity()
                .then();
    }
}
