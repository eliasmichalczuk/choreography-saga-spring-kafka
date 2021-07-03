package com.vinsguru.payment.service;

import com.vinsguru.events.inventory.InventoryOutOfStockEvent;
import com.vinsguru.events.payment.PaymentEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Sinks;

@Service
public class PaymentPublisher {

    @Autowired
    private Sinks.Many<PaymentEvent> paymentSink;

    public void emit(PaymentEvent event){
        this.paymentSink.tryEmitNext(event);
    }
}
