package com.paymybuddy.sylvain.service;

import com.paymybuddy.sylvain.dto.BuddyFormDto;
import com.paymybuddy.sylvain.dto.UserRegistrationDto;
import com.paymybuddy.sylvain.model.Relation;
import com.paymybuddy.sylvain.model.User;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.List;

public interface UserService extends UserDetailsService {
    User save(UserRegistrationDto userRegistrationDto);
    User addBuddy(BuddyFormDto buddyFormDto);
    List<Relation> listEmailRelation(String username);
    Boolean deleteBuddy(Integer id);
}
