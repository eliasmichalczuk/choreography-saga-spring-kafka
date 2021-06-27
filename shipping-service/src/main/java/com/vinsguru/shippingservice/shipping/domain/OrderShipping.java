package com.vinsguru.shippingservice.shipping.domain;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.time.Instant;
import java.util.UUID;

@Entity
@AllArgsConstructor
@NoArgsConstructor
public class OrderShipping {

    @Id
    @GeneratedValue
    private Long id;
    private int userId;
    private UUID orderId;
    private Instant instant;

    public static OrderShipping of(int userId, UUID orderId) {
        return new OrderShipping(null, userId, orderId, Instant.now());
    }
}
