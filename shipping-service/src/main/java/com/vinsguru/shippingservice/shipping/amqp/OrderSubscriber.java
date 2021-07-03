package com.vinsguru.shippingservice.shipping.amqp;

import com.vinsguru.events.order.OrderConfirmedEvent;
import com.vinsguru.events.order.OrderEvent;
import com.vinsguru.events.order.OrderStatus;
import com.vinsguru.events.payment.PaymentEvent;
import com.vinsguru.events.shipping.ShippingEvent;
import com.vinsguru.shippingservice.shipping.service.OrderService;
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
public class OrderSubscriber {

    @Autowired
    private OrderService orderService;

//    @Bean
//    public Function<Flux<OrderConfirmedEvent>, Flux<ShippingEvent>> shippingProcessor() {
//        return flux -> flux.flatMap(this::processShipping);
//    }
//
//    private Mono<ShippingEvent> processShipping(OrderConfirmedEvent event){
//        if (OrderStatus.CONFIRMED.equals(event.getOrderStatus())){
//            return Mono.fromSupplier(() -> this.orderService.orderConfirmedEvent(event));
//        }
//        return Mono.fromSupplier(() -> this.orderService.orderNotConfirmed(event));
//    }

    @Bean
    public Sinks.Many<ShippingEvent> shippingSink(){
        return Sinks.many().unicast().onBackpressureBuffer();
    }

    @Bean
    public Supplier<Flux<ShippingEvent>> shippingSupplier(Sinks.Many<ShippingEvent> sink){
        return sink::asFlux;
    }

    @Bean
    public Consumer<PaymentEvent> paymentEventConsumer(){
        return paymentEvent -> orderService.shipIt(paymentEvent);
    }
}
