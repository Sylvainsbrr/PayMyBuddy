package com.paymybuddy.sylvain.service;

import com.paymybuddy.sylvain.dao.RoleDAO;
import com.paymybuddy.sylvain.dao.UserDAO;
import com.paymybuddy.sylvain.dto.UserRegistrationDto;
import com.paymybuddy.sylvain.model.Role;
import com.paymybuddy.sylvain.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements  UserService {

    @Autowired
    private UserDAO userDao;

    @Autowired
    private RoleDAO roleDao;
    @Autowired
    static BCryptPasswordEncoder encoder = new BCryptPasswordEncoder(12);


    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = userDao.findByEmail(email);
        if(user == null){
            throw new UsernameNotFoundException("Invalid username or password.");
        }

        return new org.springframework.security.core.userdetails.User(user.getEmail(), user.getPassword(), mapRolesToAuthorities(user.getRoles()));

    }
    private Collection<? extends GrantedAuthority> mapRolesToAuthorities(Collection<Role> roles){
        System.out.println(roles);
        return roles.stream().map(role -> new SimpleGrantedAuthority(role.getName())).collect(Collectors.toList());
    }


    @Override
    public User save(UserRegistrationDto userRegistrationDto) {
        Role role = roleDao.findRoleByName("USER");
        User user = new User(userRegistrationDto.getFirstname(), userRegistrationDto.getLastname(), userRegistrationDto.getEmail(), encoder.encode(userRegistrationDto.getPassword()), BigDecimal.ZERO,
                new Date(), Arrays.asList(role));
        return userDao.save(user);
    }
}
