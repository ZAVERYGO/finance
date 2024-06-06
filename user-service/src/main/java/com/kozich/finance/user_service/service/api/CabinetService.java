package com.kozich.finance.user_service.service.api;

import com.kozich.finance.user_service.core.dto.UserDTO;
import com.kozich.finance.user_service.model.UserEntity;

public interface CabinetService {

     UserEntity registerUser(UserDTO userDTO);

     void verifyUser(String code, String mail);

     String loginUser(UserDTO userDTO);
}
