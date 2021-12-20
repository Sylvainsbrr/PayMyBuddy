package com.paymybuddy.sylvain.service;

import com.paymybuddy.sylvain.dto.UserRegistrationDto;
import com.paymybuddy.sylvain.model.User;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface UserService extends UserDetailsService {
    User save(UserRegistrationDto userRegistrationDto);
}
