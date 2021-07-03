package com.vinsguru.inventory.config;

import com.vinsguru.events.inventory.InventoryEvent;
import com.vinsguru.events.inventory.InventoryOutOfStockEvent;
import com.vinsguru.events.order.OrderEvent;
import com.vinsguru.events.order.OrderStatus;
import com.vinsguru.events.payment.PaymentEvent;
import com.vinsguru.events.shipping.ShippingEvent;
import com.vinsguru.inventory.service.InventoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.publisher.Sinks;

import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;

@Configuration
public class InventoryConfig {

    @Autowired
    private InventoryService service;

//    @Bean
//    public Function<Flux<OrderEvent>, Flux<InventoryEvent>> inventoryProcessor() {
//        return flux -> flux.flatMap(this::processInventory);
//    }
//
//    private Mono<InventoryEvent> processInventory(OrderEvent event){
//        if(event.getOrderStatus().equals(OrderStatus.CREATED)){
//            return Mono.fromSupplier(() -> this.service.newOrderInventory(event));
//        }
//        return Mono.fromRunnable(() -> this.service.cancelOrderInventory(event));
//    }

    @Bean
    public Sinks.Many<InventoryEvent> inventorySink(){
        return Sinks.many().unicast().onBackpressureBuffer();
    }

    @Bean
    public Supplier<Flux<InventoryEvent>> inventorySupplier(Sinks.Many<InventoryEvent> sink){
        return sink::asFlux;
    }

    @Bean
    public Sinks.Many<InventoryOutOfStockEvent> InventoryOutOfStockSink(){
        return Sinks.many().unicast().onBackpressureBuffer();
    }

    @Bean
    public Supplier<Flux<InventoryOutOfStockEvent>> inventoryOutOfStockSupplier(Sinks.Many<InventoryOutOfStockEvent> sink){
        return sink::asFlux;
    }


    @Bean
    public Consumer<OrderEvent> inventoryConsumer(){
        return orderEvent -> Mono.fromRunnable(() -> this.service.newOrderInventory(orderEvent));
    }
}

