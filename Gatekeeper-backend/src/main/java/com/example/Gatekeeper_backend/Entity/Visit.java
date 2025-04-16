package com.example.Gatekeeper_backend.Entity;

import com.example.Gatekeeper_backend.Enum.VisitStatus;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.util.Date;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Visit { // it will map visitor to flat
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id ;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private VisitStatus status ;

    @Column(nullable = false)
    private String purpose ;
    private Date inTime ;
    private Date outTime ;

    private String imageUrl ;

    @Column(nullable = false)
    private Integer noOfPeople ;


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "visitor_id")
    private Visitor visitor ;

    @ManyToOne
    @JoinColumn(name = "flat_id")
    private Flat flat ;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User approvedBy ;

    @CreationTimestamp
    private Date createdDate ;
    @UpdateTimestamp
    private Date updatedDate ;


}
