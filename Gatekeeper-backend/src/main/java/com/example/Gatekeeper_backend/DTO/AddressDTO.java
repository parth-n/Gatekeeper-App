package com.example.Gatekeeper_backend.DTO;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AddressDTO {

    private String line1 ;

    private String line2 ;

    private String city ;

    private String pincode ;
}
