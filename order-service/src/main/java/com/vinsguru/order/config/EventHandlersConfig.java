package com.vinsguru.order.config;

import com.vinsguru.events.inventory.InventoryEvent;
import com.vinsguru.events.inventory.InventoryOutOfStockEvent;
import com.vinsguru.events.order.OrderStatus;
import com.vinsguru.events.payment.PaymentEvent;
import com.vinsguru.events.shipping.ShippingEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.function.Consumer;

@Configuration
public class EventHandlersConfig {

    @Autowired
    private OrderStatusUpdateEventHandler orderEventHandler;

    @Bean
    public Consumer<ShippingEvent> shippingEventConsumer(){
        return se -> {
            orderEventHandler.updateOrderShipping(se.getOrderId(), po -> {
                po.setOrderStatus(se.getOrderStatus());
            });
        };
    }

    @Bean
    public Consumer<InventoryOutOfStockEvent> inventoryOutOfStockEventConsumer(){
        return event -> {
            orderEventHandler.updateOrder(event.getInventory().getPurchaseOrder().getOrderId(), po -> {
                po.setOrderStatus(OrderStatus.CANCELLED);
            });
        };
    }
}
