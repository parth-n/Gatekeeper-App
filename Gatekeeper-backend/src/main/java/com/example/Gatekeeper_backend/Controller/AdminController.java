package com.example.Gatekeeper_backend.Controller;

import com.example.Gatekeeper_backend.DTO.UserDTO;
import com.example.Gatekeeper_backend.Service.AdminService;
import jakarta.validation.Valid;
import org.apache.catalina.connector.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    private AdminService adminService ;

    @PostMapping("/create") //@valid is validating the dto object even before passing it
                            // to the controller layer which will save API calls.
                            // it will give bad request for invalid object
    ResponseEntity<Long> createUser(@RequestBody @Valid UserDTO userDTO){
        Long id = adminService.createUser(userDTO) ;
        return ResponseEntity.ok(id) ;
    }


}
