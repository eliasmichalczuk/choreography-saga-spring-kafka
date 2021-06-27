package com.vinsguru.shippingservice.shipping.repository;

import com.vinsguru.shippingservice.shipping.domain.OrderShipping;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface OrderShippingRepository extends JpaRepository <OrderShipping, Long> {
}
