package com.example.Gatekeeper_backend.Service;

import com.example.Gatekeeper_backend.DTO.AddressDTO;
import com.example.Gatekeeper_backend.DTO.VisitDTO;
import com.example.Gatekeeper_backend.DTO.VisitorDTO;
import com.example.Gatekeeper_backend.Entity.Address;
import com.example.Gatekeeper_backend.Entity.Flat;
import com.example.Gatekeeper_backend.Entity.Visit;
import com.example.Gatekeeper_backend.Entity.Visitor;
import com.example.Gatekeeper_backend.Enum.VisitStatus;
import com.example.Gatekeeper_backend.Repo.FlatRepo;
import com.example.Gatekeeper_backend.Repo.VisitRepo;
import com.example.Gatekeeper_backend.Repo.VisitorRepo;
import com.example.Gatekeeper_backend.utils.CommonUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class GatekeeperService {

    @Autowired
    private VisitorRepo visitorRepo ;

    @Autowired
    private CommonUtil commonUtil ;

    @Autowired
    private FlatRepo flatRepo ;

    @Autowired
    private VisitRepo visitRepo ;

    public VisitorDTO getVisitor(String idNumber){
        Visitor visitor = visitorRepo.findByIdNumber(idNumber) ;
        VisitorDTO visitorDTO = null ;
        if(visitor != null){ // if the visitor is not in the db, it will build the data
            visitorDTO = VisitorDTO.builder()
                    .name(visitor.getName())
                    .email(visitor.getEmail())
                    .phone(visitor.getPhone())
                    .idNumber(visitor.getIdNumber())
                    .build() ;
        }

        return visitorDTO ;
    }

    public Long createVisitor(VisitorDTO visitorDTO){
        AddressDTO addressDTO = visitorDTO.getAddressDTO() ;
        Address address =   commonUtil.convertAddressDTOToAddress(addressDTO) ;
        Visitor visitor = Visitor.builder()
                .address(address)
                .name(visitorDTO.getName())
                .email(visitorDTO.getEmail())
                .phone(visitorDTO.getPhone())
                .idNumber((visitorDTO.getIdNumber()))
                .build() ;
        visitor = visitorRepo.save(visitor) ;
        return visitor.getId() ;
    }

    public Long createVisit(VisitDTO visitDTO){

        Flat flat = flatRepo.findByNumber(visitDTO.getFlatNumber()) ;
        Visitor visitor = visitorRepo.findByIdNumber(visitDTO.getIdNumber()) ;

        Visit visit = Visit.builder()
                .flat(flat)
                .imageUrl(visitDTO.getUrlOfImage())
                .purpose(visitDTO.getPurpose())
                .noOfPeople(visitDTO.getNoOfPeople())
                .status(VisitStatus.WAITING)
                .visitor(visitor)
                .build() ;

        visit = visitRepo.save(visit) ;
        return visit.getId() ;
    }

    public String markEntry(Long id){
        Visit visit = visitRepo.findById(id).get() ;
        if(visit==null){
            //exception
        }
        if(visit.getStatus().equals(VisitStatus.APPROVED)){
            visit.setInTime(new Date());
        }
        else{
            //exception
        }
        return "Done" ;
    }

    public String markExit(Long id){
        Visit visit = visitRepo.findById(id).get() ;
        if(visit==null){
            //exception
        }
        if(visit.getStatus().equals(VisitStatus.APPROVED) && visit.getInTime()!=null){
            visit.setOutTime(new Date());
            visit.setStatus(VisitStatus.COMPLETED);
        }
        else{
            //exception
        }
        return "Done" ;
    }

}
