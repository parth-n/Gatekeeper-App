package com.example.Gatekeeper_backend.Entity;

import com.example.Gatekeeper_backend.Enum.Role;
import com.example.Gatekeeper_backend.Enum.UserStatus;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class User implements UserDetails {

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

    @Column(nullable=false)
    private String password ;
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

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        List<GrantedAuthority> grantedAuthorityList = new ArrayList<>() ;
        grantedAuthorityList.add(new SimpleGrantedAuthority(role.name())) ;
        return grantedAuthorityList ;
    }

    @Override
    public String getUsername() {
        return email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true ;
    }
}
