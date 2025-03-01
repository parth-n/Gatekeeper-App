package com.example.Gatekeeper_backend.Repo;

import com.example.Gatekeeper_backend.DTO.VisitorDTO;
import com.example.Gatekeeper_backend.Entity.Visitor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface VisitorRepo extends JpaRepository<Visitor,Long> {
    Visitor findByIdNumber(String idNumber) ;

}
