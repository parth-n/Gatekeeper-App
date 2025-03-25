package com.example.Gatekeeper_backend.DTO;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
public class AllPendingVisitsDTO {
    private List<VisitDTO> visits ;

    private Long totalRows ;

    private Integer totalPages ;

}
