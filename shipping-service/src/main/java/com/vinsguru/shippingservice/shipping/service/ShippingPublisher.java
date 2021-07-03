package com.vinsguru.shippingservice.shipping.service;

import com.vinsguru.events.inventory.InventoryOutOfStockEvent;
import com.vinsguru.events.payment.PaymentEvent;
import com.vinsguru.events.shipping.ShippingEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Sinks;

@Service
public class ShippingPublisher {

    @Autowired
    private Sinks.Many<ShippingEvent> shippingSink;

    public void emit(ShippingEvent event){
        this.shippingSink.tryEmitNext(event);
    }
}
