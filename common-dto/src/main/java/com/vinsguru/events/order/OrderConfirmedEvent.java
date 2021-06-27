package com.vinsguru.events.order;

import com.vinsguru.dto.PurchaseOrderDto;
import com.vinsguru.events.Event;
import lombok.Data;

import java.util.Date;
import java.util.UUID;

@Data
public class OrderConfirmedEvent implements Event {

    private final UUID eventId = UUID.randomUUID();
    private final Date date = new Date();
    private PurchaseOrderDto purchaseOrder;
    private OrderStatus orderStatus;

    public OrderConfirmedEvent() {
    }

    public OrderConfirmedEvent(PurchaseOrderDto purchaseOrder, OrderStatus orderStatus) {
        this.purchaseOrder = purchaseOrder;
        this.orderStatus = orderStatus;
    }

    @Override
    public UUID getEventId() {
        return this.eventId;
    }

    @Override
    public Date getDate() {
        return this.date;
    }

    public PurchaseOrderDto getPurchaseOrder() {
        return purchaseOrder;
    }

    public OrderStatus getOrderStatus() {
        return orderStatus;
    }
}
