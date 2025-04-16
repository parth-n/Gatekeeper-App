package com.example.Gatekeeper_backend.DTO;

import com.example.Gatekeeper_backend.Entity.Flat;
import com.example.Gatekeeper_backend.Enum.Role;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserDTO {

    @NonNull //data validation
    @Size(max = 255)
    private String name ;

    @NonNull //data validation
    @Size(max = 255)
    private String email ;

    @NonNull //data validation
    @Size(max = 10,min = 10)
    private String phone ;

    @NotNull
    private String idNumber ;

    private String password ;
    private Role role ;

    private String flatNo ;

    private AddressDTO address ;
}
