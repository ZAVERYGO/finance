package com.kozich.finance.user_service.service.api;

import com.kozich.finance.user_service.core.dto.LoginDTO;
import com.kozich.finance.user_service.core.dto.RegistrationDTO;
import com.kozich.finance.user_service.core.dto.UserDTO;
import com.kozich.finance.user_service.model.UserEntity;

public interface CabinetService {

     UserEntity registerUser(RegistrationDTO registrationDTO);

     UserEntity verifyUser(String code, String mail);

     String loginUser(LoginDTO loginDTO);

     UserEntity getMyCabinet();
}
