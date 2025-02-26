package com.example.Gatekeeper_backend.Entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.util.Date;
import java.util.Set;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Flat {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id ;

    @Column(unique = true)
    private String number ;

    @OneToMany(mappedBy = "flat")//mapped with flat attribute in User entity
    private Set<User> userSet ;

    @CreationTimestamp
    private Date createdDate ;
    @UpdateTimestamp
    private Date updatedDate ;
}
