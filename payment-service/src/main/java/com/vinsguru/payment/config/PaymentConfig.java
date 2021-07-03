package com.vinsguru.payment.config;

import com.vinsguru.events.inventory.InventoryEvent;
import com.vinsguru.events.order.OrderEvent;
import com.vinsguru.events.order.OrderStatus;
import com.vinsguru.events.payment.PaymentEvent;
import com.vinsguru.events.shipping.ShippingEvent;
import com.vinsguru.payment.service.PaymentService;
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
public class PaymentConfig {

    @Autowired
    private PaymentService service;

    // payment service não consome mais diretamento o do order service,
    // após o investario ser reservado, o inventory service envia o evento,
    // e esse é consumido pelo payment service, que envia outro evento para o shipping service,
    // em caso de rollback, faz o rollback inicialmente para o invetory service

//    @Bean
//    public Function<Flux<OrderEvent>, Flux<PaymentEvent>> paymentProcessor() {
//        return flux -> flux.flatMap(this::processPayment);
//    }
//
//    private Mono<PaymentEvent> processPayment(OrderEvent event){
//        if(event.getOrderStatus().equals(OrderStatus.CREATED)){
//            return Mono.fromSupplier(() -> this.service.newOrderEvent(event));
//        }else{
//            return Mono.fromRunnable(() -> this.service.cancelOrderEvent(event));
//        }
//    }

    @Bean
    public Sinks.Many<PaymentEvent> paymentSink(){
        return Sinks.many().unicast().onBackpressureBuffer();
    }

    @Bean
    public Supplier<Flux<PaymentEvent>> paymentSupplier(Sinks.Many<PaymentEvent> sink){
        return sink::asFlux;
    }

    @Bean
    public Consumer<InventoryEvent> inventoryEventConsumer(){
        return inventoryEvent -> service.newOrderEvent(inventoryEvent);
    }
}
