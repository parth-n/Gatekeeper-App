package com.example.Gatekeeper_backend.Service;

import com.example.Gatekeeper_backend.DTO.AddressDTO;
import com.example.Gatekeeper_backend.DTO.UserDTO;
import com.example.Gatekeeper_backend.Entity.Address;
import com.example.Gatekeeper_backend.Entity.Flat;
import com.example.Gatekeeper_backend.Entity.User;
import com.example.Gatekeeper_backend.Enum.UserStatus;
import com.example.Gatekeeper_backend.Repo.FlatRepo;
import com.example.Gatekeeper_backend.Repo.UserRepo;
import com.example.Gatekeeper_backend.utils.CommonUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AdminService {

    @Autowired
    private UserRepo userRepo ;

    @Autowired
    private FlatRepo flatRepo ;

    @Autowired
    private CommonUtil commonUtil ;

    public Long createUser(UserDTO userDTO){

        AddressDTO addressDTO = userDTO.getAddress() ;
        Address address =   commonUtil.convertAddressDTOToAddress(addressDTO) ;

        Flat flat = null ;
        if(userDTO.getFlatNo() != null){
            flat = flatRepo.findByNumber(userDTO.getFlatNo());
        }

        User user = User.builder()
                .name(userDTO.getName())
                .email(userDTO.getEmail())
                .phone(userDTO.getPhone())
                .role(userDTO.getRole())
                .idNumber(userDTO.getIdNumber())
                .flat(flat)
                .userStatus(UserStatus.ACTIVE)
                .address(address) //building address from address dto is necessary before this.
                .build() ;
        user = userRepo.save(user) ;
        return user.getId() ;
    }
}
