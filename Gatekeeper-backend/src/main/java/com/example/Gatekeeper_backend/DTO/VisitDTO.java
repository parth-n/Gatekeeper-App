package com.example.Gatekeeper_backend.DTO;

import com.example.Gatekeeper_backend.Enum.VisitStatus;
import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.util.Date;

// it will map visitor and flat with visit status
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class VisitDTO {
    private VisitStatus status ;

    private Date inTime ;
    //intime and outtime are needed only if the visit request is approved.
    private Date outTime ;

    @NotNull//for validation and not a sql constraint
    @Size(max = 255)
    private String purpose ;

    @Size(max = 255)
    private String urlOfImage ;

    @NotNull
    private Integer noOfPeople ;

    @NotNull
    private String idNumber ;//id number of visitor

    @NotNull
    private String flatNumber ;

    private String visitorName ;
    private String visitorPhone ;

}
