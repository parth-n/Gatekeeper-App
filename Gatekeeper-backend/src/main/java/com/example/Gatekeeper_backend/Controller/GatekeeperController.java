package com.example.Gatekeeper_backend.Controller;


import com.example.Gatekeeper_backend.DTO.VisitDTO;
import com.example.Gatekeeper_backend.DTO.VisitorDTO;
import com.example.Gatekeeper_backend.Service.GatekeeperService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/gatekeeper")
public class GatekeeperController {

    @Autowired
    private GatekeeperService gatekeeperService ;

    @GetMapping("/getVisitor")
    ResponseEntity<VisitorDTO> getVisitorById(@RequestParam String idNumber){
        VisitorDTO visitorDTO = gatekeeperService.getVisitor(idNumber) ;
        if(visitorDTO==null){
            return ResponseEntity.notFound().build() ;
        }
        return ResponseEntity.ok(visitorDTO) ;
    }

    @PostMapping("/createVisitor")
    ResponseEntity<Long> createVisitor(@RequestBody @Valid VisitorDTO visitorDTO){
        Long id = gatekeeperService.createVisitor(visitorDTO) ;
        return ResponseEntity.ok(id) ;
    }

    @PostMapping("/createVisitor")
    ResponseEntity<Long> createVisit(@RequestBody @Valid VisitDTO visitDTO){
        Long id = gatekeeperService.createVisit(visitDTO) ;
        return ResponseEntity.ok(id) ;
    }

    @PutMapping("/markEntry/{id}")
    ResponseEntity<String> markEntry(@PathVariable Long id){
        return ResponseEntity.ok(gatekeeperService.markEntry(id)) ;
    }

    @PutMapping("/markExit/{id}")
    ResponseEntity<String> markExit(@PathVariable Long id){
        return ResponseEntity.ok(gatekeeperService.markExit(id)) ;
    }

}
