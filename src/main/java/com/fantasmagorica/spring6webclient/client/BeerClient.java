package com.fantasmagorica.spring6webclient.client;

import com.fantasmagorica.spring6webclient.model.BeerDTO;
import com.fasterxml.jackson.databind.JsonNode;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Map;

public interface BeerClient {

    Flux<String> getBeers();

    Flux<Map> getBeersMap();

    Flux<JsonNode> getBeersJson();

    Flux<BeerDTO> getBeersDTO();

    Mono<BeerDTO> getBeerById(String id);

    Flux<BeerDTO> getBeersByStyle(String style);

    Mono<BeerDTO> createBeer(BeerDTO beer);

    Mono<BeerDTO> updateBeer(BeerDTO beer);

    Mono<Void> deleteBeer(String id);
}
