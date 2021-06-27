package com.vinsguru.shippingservice.shipping.repository;

import com.vinsguru.shippingservice.shipping.domain.Address;
import com.vinsguru.shippingservice.shipping.domain.OrderShipping;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface AddressRepository extends JpaRepository <Address, Long> {

    Address findByUserId(Integer id);
}
