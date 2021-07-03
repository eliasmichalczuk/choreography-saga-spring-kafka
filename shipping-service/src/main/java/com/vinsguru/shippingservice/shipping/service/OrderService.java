package com.vinsguru.shippingservice.shipping.service;

import com.vinsguru.events.order.OrderConfirmedEvent;
import com.vinsguru.events.order.OrderEvent;
import com.vinsguru.events.order.OrderStatus;
import com.vinsguru.events.payment.PaymentEvent;
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
    private ShippingPublisher shippingPublisher;

    @Transactional
    public void shipIt(PaymentEvent paymentEvent) {
        var userAddress = addressRepository.findByUserId(paymentEvent.getPayment().getUserId());
        if (userAddress.isValid()) {
            orderShippingRepository.save(OrderShipping.of(paymentEvent.getPayment().getUserId(), paymentEvent.getPayment().getOrderId()));
            shippingPublisher.emit(ShippingEvent.shippingConfirmed(paymentEvent));
        } else {
//            return ShippingEvent.shippingError(orderEvent);
        }
    }

    @Transactional
    public ShippingEvent orderNotConfirmed(OrderConfirmedEvent orderEvent) {
        return new ShippingEvent(orderEvent.getPurchaseOrder().getOrderId(), OrderStatus.SHIPPING_ERROR, "Cannot ship not confirmed order");
    }
}
