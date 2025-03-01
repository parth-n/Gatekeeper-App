package com.example.Gatekeeper_backend.Repo;

import com.example.Gatekeeper_backend.Entity.Flat;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FlatRepo extends JpaRepository<Flat,Long> {

    Flat findByNumber(String number) ;
}
