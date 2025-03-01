package com.example.Gatekeeper_backend.Service;

import com.example.Gatekeeper_backend.Entity.Visit;
import com.example.Gatekeeper_backend.Enum.VisitStatus;
import com.example.Gatekeeper_backend.Repo.VisitRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service

public class ResidentService {

    @Autowired
    private VisitRepo visitRepo ;

    public String updateVisit(Long id, VisitStatus visitStatus){
        if(visitStatus != VisitStatus.REJECTED && visitStatus != VisitStatus.APPROVED){
            //exception
        }
        Visit visit = visitRepo.findById(id).get() ;
        if(visit==null){
            //exception
        }
        if(VisitStatus.WAITING.equals((visit.getStatus()))){
            visit.setStatus(visitStatus);
            visitRepo.save(visit) ;
        }
        else{
            //exception
        }
    }
}
