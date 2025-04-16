package com.example.Gatekeeper_backend.Repo;

import com.example.Gatekeeper_backend.Entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepo extends JpaRepository<User,Long> {
    User findByEmail(String email) ;
}
