package com.vinsguru.shippingservice.shipping.service;

import com.vinsguru.events.order.OrderConfirmedEvent;
import com.vinsguru.events.order.OrderStatus;
import com.vinsguru.events.shipping.ShippingEvent;
import com.vinsguru.shippingservice.shipping.domain.OrderShipping;
import com.vinsguru.shippingservice.shipping.repository.AddressRepository;
import com.vinsguru.shippingservice.shipping.repository.OrderShippingRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@AllArgsConstructor
public class OrderService {

    private AddressRepository addressRepository;
    private OrderShippingRepository orderShippingRepository;

    @Transactional
    public ShippingEvent orderConfirmedEvent(OrderConfirmedEvent orderEvent) {
        var userAddress = addressRepository.findByUserId(orderEvent.getPurchaseOrder().getUserId());
        if (userAddress.isValid()) {
            orderShippingRepository.save(OrderShipping.of(orderEvent.getPurchaseOrder().getUserId(), orderEvent.getPurchaseOrder().getOrderId()));
            return ShippingEvent.shippingConfirmed(orderEvent);
        } else {
            return ShippingEvent.shippingError(orderEvent);
        }
    }

    @Transactional
    public ShippingEvent orderNotConfirmed(OrderConfirmedEvent orderEvent) {
        return new ShippingEvent(orderEvent.getPurchaseOrder().getOrderId(), OrderStatus.SHIPPING_ERROR, "Cannot ship not confirmed order");
    }
}
