package com.vinsguru.shippingservice.shipping.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.UUID;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
public class Address {

    @Id
    private Long id;
    private Integer userId;
    private int code;
    private String street;
    private int number;
    private String city;

    public boolean isCool() {
        return code != 0 && street != null && number != 0 && city != null;
    }
}
