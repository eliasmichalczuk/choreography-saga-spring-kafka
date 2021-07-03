package com.vinsguru.order;

import org.junit.jupiter.api.Test;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public class ReactorTesting {

    @Test
    public void shouldRun() {

        Flux<String> vals = Flux.just("str1", "elias");

        Flux.range(1, 4)
                .map(i -> {
                    if (i <= 3) return i;
                    throw new RuntimeException("Got to 4");
                }).subscribe(integer -> System.out.println(integer), (err) -> {
            System.out.println("error throw of " + err.getClass().getName());
        }, () -> {});

        vals.map(str -> str + ", HI!").subscribe(e -> {
            System.out.println(e);
        });
        Mono.just("a");
        Flux.range(1, 2);
    }
}
