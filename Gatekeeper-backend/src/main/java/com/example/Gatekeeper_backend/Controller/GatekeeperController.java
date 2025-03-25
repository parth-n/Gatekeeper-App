package com.example.Gatekeeper_backend.Controller;


import com.example.Gatekeeper_backend.DTO.VisitDTO;
import com.example.Gatekeeper_backend.DTO.VisitorDTO;
import com.example.Gatekeeper_backend.Service.GatekeeperService;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

@RestController
@RequestMapping("/gatekeeper")
public class GatekeeperController {

    private Logger LOGGER = LoggerFactory.getLogger(GatekeeperController.class) ;

    @Autowired
    private GatekeeperService gatekeeperService ;

    @Value("${static.domain.name}")
    private String staticDomainName ;

    @Value("{image.upload.home}")
    private String imageUploadHome ;

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

    @PostMapping("/createVisit")
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
    @PostMapping("image-upload")
    public ResponseEntity<String> imageUpload(@RequestParam MultipartFile file){
        String fileName = UUID.randomUUID()+"_"+file.getOriginalFilename() ;
        String uploadPath = imageUploadHome+fileName ;
        String publicURL = staticDomainName+"content/"+fileName ;
            try {
                file.transferTo(new File(uploadPath));
            } catch (IOException e) {
                LOGGER.error("Exception while uploading image: {}", e);
                return ResponseEntity.ok("Exception while uploading image");
            }
        return ResponseEntity.ok(publicURL) ;
    }
}
