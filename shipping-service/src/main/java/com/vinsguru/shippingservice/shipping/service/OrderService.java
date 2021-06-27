package com.vinsguru.shippingservice.shipping.service;

import com.vinsguru.events.order.OrderConfirmedEvent;
import com.vinsguru.events.order.OrderEvent;
import com.vinsguru.events.order.OrderStatus;
import com.vinsguru.events.shipping.ShippingEvent;
import com.vinsguru.shippingservice.shipping.domain.OrderShipping;
import com.vinsguru.shippingservice.shipping.repository.AddressRepository;
import com.vinsguru.shippingservice.shipping.repository.OrderShippingRepository;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
public class OrderService {

    private AddressRepository addressRepository;
    private OrderShippingRepository orderShippingRepository;

    @Transactional
    public ShippingEvent orderConfirmedEvent(OrderConfirmedEvent orderEvent) {
        var userAddress = addressRepository.findByUserId(orderEvent.getPurchaseOrder().getUserId());
        if (userAddress.isCool()) {
            orderShippingRepository.save(OrderShipping.of(orderEvent.getPurchaseOrder().getUserId(), orderEvent.getPurchaseOrder().getOrderId()));
            return new ShippingEvent(orderEvent.getEventId(), OrderStatus.SHIPPING_CONFIRMED);
        } else {
            return new ShippingEvent(orderEvent.getEventId(), OrderStatus.SHIPPING_ERROR);
        }
    }
}
