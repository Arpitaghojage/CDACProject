package com.cdac.service;

import com.cdac.dto.SignupReqDTO;
import com.cdac.dto.UserReqDTO;
import com.cdac.dto.UserRespDTO;

import java.util.List;

public interface UserService {

    UserRespDTO signUp(SignupReqDTO dto);

    List<UserRespDTO> getAllUsers();
    UserRespDTO getUserById(Long id);
    UserRespDTO saveUser(UserReqDTO userDto);
    void deleteUser(Long id);
    UserRespDTO getUserByEmail(String email);
}
