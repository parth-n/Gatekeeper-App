package com.example.Gatekeeper_backend.DTO;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class VisitorDTO {

    @NotNull
    @Size(max=255)
    private String name ;

    @NonNull //data validation
    @Size(max = 255)
    private String email ;

    @NonNull //data validation
    @Size(max = 10,min = 10)
    private String phone ;

    @NotNull
    private String idNumber ;

    private AddressDTO address ;
}
