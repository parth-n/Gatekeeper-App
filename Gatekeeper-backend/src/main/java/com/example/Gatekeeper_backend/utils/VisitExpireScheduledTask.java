package com.example.Gatekeeper_backend.utils;

import com.example.Gatekeeper_backend.Entity.Visit;
import com.example.Gatekeeper_backend.Enum.VisitStatus;
import com.example.Gatekeeper_backend.Repo.VisitRepo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.Scheduled;

import java.util.Date;
import java.util.List;

@Configuration
public class VisitExpireScheduledTask {

    private Logger LOGGER = LoggerFactory.getLogger(VisitExpireScheduledTask.class) ;

    @Autowired
    private VisitRepo visitRepo ;

    @Scheduled(fixedDelay = 5000)
    public void markVisitAsExpired(){
        LOGGER.info("marking visit as expired") ;

        Date date = new Date() ;
        date.setMinutes(date.getMinutes()-30);
        List<Visit> visitList = visitRepo.findByStatusAndCreatedDateLessThanEqual(VisitStatus.WAITING,date) ;
        for(Visit visit : visitList){
            visit.setStatus(VisitStatus.EXPIRED);
        }
        visitRepo.saveAll(visitList);
    }

}
