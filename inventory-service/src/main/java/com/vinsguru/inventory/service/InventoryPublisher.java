package com.vinsguru.inventory.service;

import com.vinsguru.events.inventory.InventoryEvent;
import com.vinsguru.events.inventory.InventoryOutOfStockEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Sinks;

@Service
public class InventoryPublisher {

    @Autowired
    private Sinks.Many<InventoryEvent> inventorySink;

    @Autowired
    private Sinks.Many<InventoryOutOfStockEvent> InventoryOutOfStockSink;

    public void emit(InventoryEvent event){
        this.inventorySink.tryEmitNext(event);
    }

    public void emit(InventoryOutOfStockEvent event){
        this.InventoryOutOfStockSink.tryEmitNext(event);
    }
}
