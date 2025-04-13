package com.example.Gatekeeper_backend.Repo;

import com.example.Gatekeeper_backend.Entity.Flat;
import com.example.Gatekeeper_backend.Entity.Visit;
import com.example.Gatekeeper_backend.Enum.VisitStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface VisitRepo extends JpaRepository<Visit,Long> {

    List<Visit> findByStatusAndFlat(VisitStatus visitStatus, Flat flat) ;

    Page<Visit>findByStatusAndFlat(VisitStatus status, Flat flat, Pageable pageable);

    List<Visit> findByStatusAndCreatedDateLessThanEqual(VisitStatus visitStatus, Date date) ;
}
