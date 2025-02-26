package com.example.Gatekeeper_backend.Service;

import com.example.Gatekeeper_backend.DTO.AddressDTO;
import com.example.Gatekeeper_backend.DTO.UserDTO;
import com.example.Gatekeeper_backend.Entity.Address;
import com.example.Gatekeeper_backend.Entity.Flat;
import com.example.Gatekeeper_backend.Entity.User;
import com.example.Gatekeeper_backend.Repo.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AdminService {

    @Autowired
    private UserRepo userRepo ;

    public Long createUser(UserDTO userDTO){

        AddressDTO addressDTO = userDTO.getAddress() ;
        Address address = Address.builder()
                .line1(addressDTO.getLine1())
                .line2(addressDTO.getLine2())
                .city(addressDTO.getCity())
                .pincode(addressDTO.getPincode())
                .build() ;

        Flat flat = null ;
        if(userDTO.getFlatNo() != null){
            flat = userDTO.getFlatNo() ;
        }

        User user = User.builder()
                .name(userDTO.getName())
                .email(userDTO.getEmail())
                .phone(userDTO.getPhone())
                .role(userDTO.getRole())
                .idNumber(userDTO.getIdNumber())
                .flat(userDTO.getFlatNo())
                .address(address) //building address from address dto is necessary before this.
                .build() ;
        user = userRepo.save(user) ;
        return user.getId() ;
    }
}
