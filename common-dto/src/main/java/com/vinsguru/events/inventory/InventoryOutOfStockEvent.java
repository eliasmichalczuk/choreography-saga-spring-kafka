package com.vinsguru.events.inventory;

import com.vinsguru.dto.InventoryDto;
import com.vinsguru.dto.PurchaseOrderDto;
import com.vinsguru.events.Event;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Date;
import java.util.UUID;

@Data
public class InventoryOutOfStockEvent implements Event {

    private final UUID eventId = UUID.randomUUID();
    private final Date date = new Date();
    private InventoryDto inventory;
    private InventoryStatus status;

    public InventoryOutOfStockEvent() {
    }

    public InventoryOutOfStockEvent(InventoryDto dto, InventoryStatus status) {
        this.inventory = dto;
        this.status = status;
    }

    @Override
    public UUID getEventId() {
        return this.eventId;
    }

    @Override
    public Date getDate() {
        return this.date;
    }

    public InventoryDto getInventory() {
        return inventory;
    }

    public InventoryStatus getStatus() {
        return status;
    }

}
