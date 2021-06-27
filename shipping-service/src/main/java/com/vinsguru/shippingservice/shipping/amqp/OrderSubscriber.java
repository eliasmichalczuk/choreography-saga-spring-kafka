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

import java.util.function.Function;

@Configuration
public class OrderSubscriber {

    @Autowired
    private OrderService orderService;

    @Bean
    public Function<Flux<OrderConfirmedEvent>, Flux<ShippingEvent>> shippingProcessor() {
        return flux -> flux.flatMap(this::processShipping);
    }

    private Mono<ShippingEvent> processShipping(OrderConfirmedEvent event){
        if (OrderStatus.CONFIRMED.equals(event.getOrderStatus())){
            return Mono.fromSupplier(() -> this.orderService.orderConfirmedEvent(event));
        }
        return Mono.fromSupplier(() -> this.orderService.orderNotConfirmed(event));
    }
}
