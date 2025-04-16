package com.example.Gatekeeper_backend.Controller;


import com.example.Gatekeeper_backend.DTO.AllPendingVisitsDTO;
import com.example.Gatekeeper_backend.DTO.VisitDTO;
import com.example.Gatekeeper_backend.Entity.User;
import com.example.Gatekeeper_backend.Enum.VisitStatus;
import com.example.Gatekeeper_backend.Service.ResidentService;
import jakarta.websocket.server.PathParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/resident")
public class ResidentController {

    @Autowired
    private ResidentService residentService ;

    @PutMapping("/actOnVisit/{id}")
    public ResponseEntity<String> actOnVisit (@PathVariable Long id, @RequestParam VisitStatus visitStatus){
        return ResponseEntity.ok(residentService.updateVisit(id,visitStatus)) ;
    }

    @GetMapping("/pendingVisits")
    public ResponseEntity<List<VisitDTO>> getPendingVisits(@AuthenticationPrincipal User user){
        return ResponseEntity.ok(residentService.getPendingVisits(user.getId())) ;
    }

    @GetMapping("/page-pendingVisits")
    public ResponseEntity<AllPendingVisitsDTO> getPagePendingVisits(@AuthenticationPrincipal User user, Integer pageNo, Integer pageSize){
        return ResponseEntity.ok(residentService.getPendingVisitByPage(user.getId(),pageNo,pageSize)) ;
    }

}
