package com.vinsguru.events.shipping;

import com.vinsguru.dto.PaymentDto;
import com.vinsguru.events.Event;
import com.vinsguru.events.order.OrderConfirmedEvent;
import com.vinsguru.events.order.OrderStatus;
import com.vinsguru.events.payment.PaymentStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.UUID;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class ShippingEvent implements Event {

    private final UUID eventId = UUID.randomUUID();
    private final Date date = new Date();
    private UUID orderId;
    private OrderStatus orderStatus;
    private String message;

    public static ShippingEvent shippingConfirmed(OrderConfirmedEvent orderEvent) {
        return new ShippingEvent(orderEvent.getPurchaseOrder().getOrderId(), OrderStatus.SHIPPING_CONFIRMED, "confirmed");
    }

    public static ShippingEvent shippingError(OrderConfirmedEvent orderEvent) {
        return new ShippingEvent(orderEvent.getPurchaseOrder().getOrderId(), OrderStatus.SHIPPING_ERROR, "Invalid address");
    }
}
