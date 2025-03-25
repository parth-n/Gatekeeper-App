package com.example.Gatekeeper_backend.Controller;

import com.example.Gatekeeper_backend.DTO.AddressDTO;
import com.example.Gatekeeper_backend.DTO.UserDTO;
import com.example.Gatekeeper_backend.Enum.Role;
import com.example.Gatekeeper_backend.Service.AdminService;
import jakarta.validation.Valid;
import org.apache.catalina.connector.InputBuffer;
import org.apache.catalina.connector.Response;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/admin")
public class AdminController {

    private Logger LOGGER = LoggerFactory.getLogger(AdminController.class) ;

    @Autowired
    private AdminService adminService ;

    @PostMapping("/create") //@valid is validating the dto object even before passing it
                            // to the controller layer which will save API calls.
                            // it will give bad request for invalid object
    ResponseEntity<Long> createUser(@RequestBody @Valid UserDTO userDTO){
        Long id = adminService.createUser(userDTO) ;
        return ResponseEntity.ok(id) ;
    }

    @PostMapping("/create-bulk-user")
    public ResponseEntity<List<String>> createBulkUser(@RequestParam MultipartFile file){
        LOGGER.info("File" + file.getOriginalFilename()) ;
        List<String> response = new ArrayList<>() ;
        String currIdNumber = "" ;

        try {
            BufferedReader fileReader = new BufferedReader(new InputStreamReader(file.getInputStream())) ;
            CSVParser csvParser = new CSVParser(fileReader, CSVFormat.DEFAULT.withFirstRecordAsHeader().withIgnoreHeaderCase().withTrim()) ;
            List<CSVRecord> csvRecords = csvParser.getRecords() ;//capture the rows
            for(CSVRecord csvRecord : csvRecords){

                try{
                    AddressDTO addressDTO = AddressDTO.builder()
                            .line1(csvRecord.get("line1"))
                            .line2(csvRecord.get("line2"))
                            .city(csvRecord.get("city"))
                            .pincode(csvRecord.get("pincode"))
                            .build();

                    UserDTO userDTO = UserDTO.builder()
                            .name(csvRecord.get("name"))
                            .email(csvRecord.get("email"))
                            .phone(csvRecord.get("phone"))
                            .flatNo(csvRecord.get("flatNo"))
                            .role(Role.valueOf(csvRecord.get("role")))
                            .idNumber(csvRecord.get("idNumber"))
                            .address(addressDTO)
                            .build();

                    currIdNumber = userDTO.getIdNumber();
                    Long userId = adminService.createUser(userDTO);
                    response.add("Created User : " + userDTO.getName() + " with id: " + userId);
                }
                catch (RuntimeException e) {
                    response.add("Got User exception while creating Id : "+currIdNumber);
                    //throw new RuntimeException(e);
                }
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }


        return ResponseEntity.ok(response) ;
    }

}
