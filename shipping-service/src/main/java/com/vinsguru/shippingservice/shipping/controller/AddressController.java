package com.vinsguru.shippingservice.shipping.controller;

import com.vinsguru.shippingservice.shipping.domain.Address;
import com.vinsguru.shippingservice.shipping.repository.AddressRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("address")
public class AddressController {

    @Autowired
    private AddressRepository addressRepository;

    @GetMapping("/all")
    public List<Address> getAddresses(){
        return this.addressRepository.findAll();
    }

}
