package com.example.Gatekeeper_backend.utils;

import com.example.Gatekeeper_backend.DTO.AddressDTO;
import com.example.Gatekeeper_backend.Entity.Address;
import org.springframework.stereotype.Component;

@Component
public class CommonUtil {
    public Address convertAddressDTOToAddress(AddressDTO addressDTO) {
        Address address = Address.builder()
                .line1(addressDTO.getLine1())
                .line2(addressDTO.getLine2())
                .city(addressDTO.getCity())
                .pincode(addressDTO.getPincode())
                .build() ;
        return address ;
    }
}
