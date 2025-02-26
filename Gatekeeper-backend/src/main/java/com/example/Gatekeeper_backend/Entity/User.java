package com.example.Gatekeeper_backend.Entity;

import com.example.Gatekeeper_backend.Enum.Role;
import com.example.Gatekeeper_backend.Enum.UserStatus;
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
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id ;

    @Column(nullable = false)
    private String name ;

    @Column(nullable = false,unique = true)
    private String email ;

    private String phone ;

    @Column(nullable = false,unique = true)
    private String idNumber ;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private Role role ;

    @Enumerated(EnumType.STRING)
    private UserStatus userStatus ;

    @ManyToOne(fetch = FetchType.LAZY,cascade = CascadeType.PERSIST)//cascade will create address when visitor is created
    private Address address ;

    @ManyToOne(fetch = FetchType.LAZY)
    private Flat flat ;

    @CreationTimestamp
    private Date createdDate ;
    @UpdateTimestamp
    private Date updatedDate ;

}
