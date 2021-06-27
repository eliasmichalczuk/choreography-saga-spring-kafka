package com.vinsguru.order.config;

import com.vinsguru.events.inventory.InventoryStatus;
import com.vinsguru.events.order.OrderStatus;
import com.vinsguru.events.payment.PaymentStatus;
import com.vinsguru.order.entity.PurchaseOrder;
import com.vinsguru.order.repository.PurchaseOrderRepository;
import com.vinsguru.order.service.OrderStatusPublisher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Objects;
import java.util.UUID;
import java.util.function.Consumer;

@Service
public class OrderStatusUpdateEventHandler {

    @Autowired
    private PurchaseOrderRepository repository;

    @Autowired
    private OrderStatusPublisher publisher;

    @Transactional
    public void updateOrder(final UUID id, Consumer<PurchaseOrder> consumer){
        this.repository
                .findById(id)
                .ifPresent(consumer.andThen(this::updateOrder));

    }

    private void updateOrder(PurchaseOrder purchaseOrder){
        if(Objects.isNull(purchaseOrder.getInventoryStatus()) || Objects.isNull(purchaseOrder.getPaymentStatus()))
            return;
        var isComplete = PaymentStatus.RESERVED.equals(purchaseOrder.getPaymentStatus()) && InventoryStatus.RESERVED.equals(purchaseOrder.getInventoryStatus());
        var orderStatus = isComplete ? OrderStatus.CONFIRMED : OrderStatus.CANCELLED;
        purchaseOrder.setOrderStatus(orderStatus);

        if (isComplete){
            this.publisher.raiseOrderConfirmedEvent(purchaseOrder, orderStatus);
        } else {
            this.publisher.raiseOrderEvent(purchaseOrder, orderStatus);
        }
    }

    @Transactional
    public void updateOrderShipping(final UUID id, Consumer<PurchaseOrder> consumer){
        this.repository
                .findById(id)
                .ifPresent(consumer.andThen(this::updateOrderShipping));

    }

    private void updateOrderShipping(PurchaseOrder purchaseOrder){
    }
}
