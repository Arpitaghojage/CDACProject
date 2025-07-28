package com.cdac.service;

import com.cdac.dto.SignUpDTOReqDTO;
import com.cdac.dto.UserReqDTO;
import com.cdac.dto.UserRespDTO;
import com.cdac.entities.User;

import java.util.List;

public interface UserService {

    UserRespDTO signUp(SignUpDTOReqDTO dto);


    List<UserRespDTO> getAllUsers();
    UserRespDTO getUserById(Long id);
    UserRespDTO saveUser(UserReqDTO userDto);
    void deleteUser(Long id);
    UserRespDTO getUserByEmail(String email);
}
