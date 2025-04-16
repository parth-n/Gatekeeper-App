package com.example.Gatekeeper_backend.Service;

import com.example.Gatekeeper_backend.Entity.User;
import com.example.Gatekeeper_backend.Repo.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class DbBasedUserDetailsService implements UserDetailsService {

    @Autowired
    private UserRepo userRepo ;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepo.findByEmail(username) ;
        if(user==null){
            throw new UsernameNotFoundException("User not found") ;
        }
        return user ;
    }
}
