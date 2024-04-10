package com.fantasmagorica.spring6webclient.client;

import com.fantasmagorica.spring6webclient.model.BeerDTO;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;
import java.util.concurrent.atomic.AtomicBoolean;

import static org.awaitility.Awaitility.await;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class BeerClientImplTest {

    @Autowired
    BeerClient beerClient;

    @Test
    void getBeers() throws InterruptedException {
        AtomicBoolean atomicBoolean = new AtomicBoolean(false);

        beerClient.getBeers().subscribe(response -> {
            System.out.println(response);
            atomicBoolean.set(true);
        });

        await().untilTrue(atomicBoolean);

    }

    @Test
    void getBeersMap() throws InterruptedException {
        AtomicBoolean atomicBoolean = new AtomicBoolean(false);

        beerClient.getBeersMap().subscribe(response -> {
            System.out.println(response);
            atomicBoolean.set(true);
        });

        await().untilTrue(atomicBoolean);

    }

    @Test
    void getBeersJson() {
        AtomicBoolean atomicBoolean = new AtomicBoolean(false);

        beerClient.getBeersJson().subscribe(response -> {
            System.out.println(response.toPrettyString());
            atomicBoolean.set(true);
        });

        await().untilTrue(atomicBoolean);
    }

    @Test
    void getBeersDTO() {
        AtomicBoolean atomicBoolean = new AtomicBoolean(false);

        beerClient.getBeersDTO().subscribe(response -> {
            System.out.println(response.toString());
            atomicBoolean.set(true);
        });

        await().untilTrue(atomicBoolean);
    }

    @Test
    void getBeerById() {
        AtomicBoolean atomicBoolean = new AtomicBoolean(false);

        beerClient.getBeersDTO()
                .flatMap(dto -> beerClient.getBeerById(dto.getId()))
                .subscribe(response -> {
                    System.out.println(response.toString());
                    atomicBoolean.set(true);
                });

        await().untilTrue(atomicBoolean);
    }

    @Test
    void getBeersByStyle() {
        AtomicBoolean atomicBoolean = new AtomicBoolean(false);

        beerClient.getBeersByStyle("Pale Ale").subscribe(response -> {
            System.out.println(response.toString());
            atomicBoolean.set(true);
        });

        await().untilTrue(atomicBoolean);
    }

    @Test
    void createBeer(){
        AtomicBoolean atomicBoolean = new AtomicBoolean(false);

        BeerDTO beerDTO = BeerDTO.builder()
                .beerName("New Beer")
                .beerStyle("New Style")
                .upc("123456")
                .quantityOnHand(7)
                .price(BigDecimal.TEN)
                .build();

        beerClient.createBeer(beerDTO)
                .subscribe(dto -> {
                    System.out.println(dto.toString());
                    atomicBoolean.set(true);
                });

        await().untilTrue(atomicBoolean);
    }

    @Test
    void updateBeer(){
        AtomicBoolean atomicBoolean = new AtomicBoolean(false);

        final String newName = "New Name";

        beerClient.getBeersDTO()
                .next()
                .doOnNext(beerDTO -> beerDTO.setBeerName(newName))
                .flatMap(dto -> beerClient.updateBeer(dto))
                .subscribe(byIdDto -> {
                    System.out.println(byIdDto.toString());
                    atomicBoolean.set(true);
                });

        await().untilTrue(atomicBoolean);
    }

    @Test
    void deleteBeer(){
        AtomicBoolean atomicBoolean = new AtomicBoolean(false);

        beerClient.getBeersDTO()
                .next()
                .flatMap(dto -> beerClient.deleteBeer(dto.getId()))
                .doOnSuccess(unused -> atomicBoolean.set(true))
                .subscribe();

        await().untilTrue(atomicBoolean);
    }
}