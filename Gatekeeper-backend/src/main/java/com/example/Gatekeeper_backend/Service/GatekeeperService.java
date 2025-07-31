package com.example.Gatekeeper_backend.Service;

import com.example.Gatekeeper_backend.DTO.AddressDTO;
import com.example.Gatekeeper_backend.DTO.VisitDTO;
import com.example.Gatekeeper_backend.DTO.VisitorDTO;
import com.example.Gatekeeper_backend.Entity.Address;
import com.example.Gatekeeper_backend.Entity.Flat;
import com.example.Gatekeeper_backend.Entity.Visit;
import com.example.Gatekeeper_backend.Entity.Visitor;
import com.example.Gatekeeper_backend.Enum.VisitStatus;
import com.example.Gatekeeper_backend.Exceptions.BadRequest;
import com.example.Gatekeeper_backend.Exceptions.NotFound;
import com.example.Gatekeeper_backend.Repo.FlatRepo;
import com.example.Gatekeeper_backend.Repo.VisitRepo;
import com.example.Gatekeeper_backend.Repo.VisitorRepo;
import com.example.Gatekeeper_backend.utils.CommonUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.Optional;
import java.util.concurrent.TimeUnit;

@Service
public class    GatekeeperService {

    @Autowired
    private VisitorRepo visitorRepo ;

    @Autowired
    private CommonUtil commonUtil ;

    @Autowired
    private FlatRepo flatRepo ;

    @Autowired
    private VisitRepo visitRepo ;

    @Autowired
    private RedisTemplate<String, VisitorDTO> redisTemplate ;

    public VisitorDTO getVisitor(String idNumber){
        //cache logic
        //key  : visitor{idNumber}
        //value : Object VisitorDTO
        String key = "visitor:"+idNumber ;

        VisitorDTO visitorDTO = redisTemplate.opsForValue().get(key) ;
        if(visitorDTO==null){
            Visitor visitor = visitorRepo.findByIdNumber(idNumber) ;
            if(visitor != null){ // if the visitor is not in the db, it will build the data
                visitorDTO = VisitorDTO.builder()
                        .name(visitor.getName())
                        .email(visitor.getEmail())
                        .phone(visitor.getPhone())
                        .idNumber(visitor.getIdNumber())
                        .build() ;
            }
            redisTemplate.opsForValue().set(key,visitorDTO,24, TimeUnit.HOURS) ;
        }


        return visitorDTO ;
    }

    public Long createVisitor(VisitorDTO visitorDTO){
        AddressDTO addressDTO = visitorDTO.getAddress() ;
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

    @Transactional
    public String markEntry(Long id){
        Optional<Visit> visitOptional = visitRepo.findById(id) ;
        if(visitOptional.isEmpty()){
            //exception
            throw new NotFound("Not found") ;
        }
        Visit visit = visitOptional.get() ;
        if(visit.getStatus().equals(VisitStatus.APPROVED)){
            visit.setInTime(new Date());
           // visitRepo.save(visit) ; without transactional
        }
        else{
            //exception
            throw new BadRequest("Invalid state transition") ;
        }
        return "Done" ;
    }

    @Transactional
    public String markExit(Long id){
        Visit visit = visitRepo.findById(id).get() ;
        if(visit==null){
            //exception
            throw new NotFound("Visit not found !") ;
        }
        if(visit.getStatus().equals(VisitStatus.APPROVED) && visit.getInTime()!=null){
            visit.setOutTime(new Date());
            visit.setStatus(VisitStatus.COMPLETED);
        }
        else{
            //exception
            throw new BadRequest("Invalid state transition") ;
        }
        return "Done" ;
    }

}
