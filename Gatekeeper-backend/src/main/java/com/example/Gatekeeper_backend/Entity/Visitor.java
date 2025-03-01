package com.example.Gatekeeper_backend.Entity;

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
public class Visitor {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id ;

    @Column(nullable = false)
    private String name ;
    @Column(unique = true, nullable = false)
    private String email ;
    private String phone ;
    @Column(unique = true, nullable = false)
    private String idNumber ;

    @ManyToOne(fetch = FetchType.LAZY,cascade = CascadeType.PERSIST)//cascade will create address when visitor is created
    private Address address ;

    @CreationTimestamp
    private Date createdDate ;
    @UpdateTimestamp
    private Date updatedDate ;
}
