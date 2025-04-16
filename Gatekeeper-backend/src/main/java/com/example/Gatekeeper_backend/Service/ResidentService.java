package com.example.Gatekeeper_backend.Service;

import com.example.Gatekeeper_backend.DTO.AllPendingVisitsDTO;
import com.example.Gatekeeper_backend.DTO.VisitDTO;
import com.example.Gatekeeper_backend.Entity.Flat;
import com.example.Gatekeeper_backend.Entity.User;
import com.example.Gatekeeper_backend.Entity.Visit;
import com.example.Gatekeeper_backend.Entity.Visitor;
import com.example.Gatekeeper_backend.Enum.VisitStatus;
import com.example.Gatekeeper_backend.Exceptions.BadRequest;
import com.example.Gatekeeper_backend.Exceptions.NotFound;
import com.example.Gatekeeper_backend.Repo.FlatRepo;
import com.example.Gatekeeper_backend.Repo.UserRepo;
import com.example.Gatekeeper_backend.Repo.VisitRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service

public class ResidentService {

    @Autowired
    private UserRepo userRepo ;

    @Autowired
    private VisitRepo visitRepo ;

    @Autowired
    private FlatRepo flatRepo ;

    public String updateVisit(Long id, VisitStatus visitStatus){
        if(visitStatus != VisitStatus.REJECTED && visitStatus != VisitStatus.APPROVED){
            //exception
            throw new BadRequest("Invalid state transition") ;
        }
        Visit visit = visitRepo.findById(id).get() ;
        if(visit==null){
            //exception
            throw new NotFound("Visitor not found") ;
        }
        User user = (User)SecurityContextHolder.getContext().getAuthentication().getPrincipal() ;
        if(user.getFlat() != visit.getFlat()){
            throw new BadRequest("Invalid visit id ") ;
        }
        if(VisitStatus.WAITING.equals((visit.getStatus()))){
            visit.setStatus(visitStatus);
            visit.setApprovedBy(user);
            visitRepo.save(visit) ;
        }
        else{
            //exception
            throw new BadRequest("Invalid state transition") ;
        }
        return "Done" ;
    }

    public List<VisitDTO> getPendingVisits(Long userId){
        User user = userRepo.findById(userId).get() ;
        Flat flat = user.getFlat() ;

        List<Visit> visitList = visitRepo.findByStatusAndFlat(VisitStatus.WAITING, flat) ;

        List<VisitDTO> visitDTOList = new ArrayList<>() ;

        for(Visit visit : visitList){
            Visitor visitor = visit.getVisitor() ;
            VisitDTO visitDTO = VisitDTO.builder()
                    .flatNumber(flat.getNumber())
                    .purpose(visit.getPurpose())
                    .noOfPeople(visit.getNoOfPeople())
                    .urlOfImage(visit.getImageUrl())
                    .visitorName(visitor.getName())
                    .visitorName(visitor.getPhone())
                    .status(visit.getStatus())
                    .build();

            visitDTOList.add(visitDTO) ;
        }

        return visitDTOList ;
    }

    public AllPendingVisitsDTO getPendingVisitByPage(Long userId, Integer pageNo, Integer pageSize){
        AllPendingVisitsDTO allPendingVisitsDTO = new AllPendingVisitsDTO() ;

        Pageable pageable = Pageable.ofSize(pageSize)
                .withPage(pageNo) ;
        User user = userRepo.findById(userId).get() ;
        Flat flat = user.getFlat() ;
        Page<Visit> visitPage = visitRepo.findByStatusAndFlat(VisitStatus.WAITING,flat,pageable);
        List<Visit> visitList = visitPage.stream().toList();
        List<VisitDTO> visitDTOList = new ArrayList<>() ;
        for(Visit visit : visitList){
            Visitor visitor = visit.getVisitor() ;
            VisitDTO visitDTO = VisitDTO.builder()
                    .flatNumber(flat.getNumber())
                    .purpose(visit.getPurpose())
                    .noOfPeople(visit.getNoOfPeople())
                    .urlOfImage(visit.getImageUrl())
                    .visitorName(visitor.getName())
                    .visitorName(visitor.getPhone())
                    .status(visit.getStatus())
                    .build();

            visitDTOList.add(visitDTO) ;
        }

        allPendingVisitsDTO.setVisits(visitDTOList);
        allPendingVisitsDTO.setTotalPages(visitPage.getTotalPages());
        allPendingVisitsDTO.setTotalRows(visitPage.getTotalElements());
        return allPendingVisitsDTO ;
    }
}
