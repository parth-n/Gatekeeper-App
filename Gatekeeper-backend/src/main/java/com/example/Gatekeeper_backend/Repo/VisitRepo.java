package com.example.Gatekeeper_backend.Repo;

import com.example.Gatekeeper_backend.Entity.Visit;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface VisitRepo extends JpaRepository<Visit,Long> {
}
