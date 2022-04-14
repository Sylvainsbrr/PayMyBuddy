package com.paymybuddy.sylvain.service;

import com.paymybuddy.sylvain.dao.RelationDAO;
import com.paymybuddy.sylvain.dao.RoleDAO;
import com.paymybuddy.sylvain.dao.UserDAO;
import com.paymybuddy.sylvain.dto.BuddyFormDto;
import com.paymybuddy.sylvain.dto.UserRegistrationDto;
import com.paymybuddy.sylvain.exception.DataAlreadyExistException;
import com.paymybuddy.sylvain.exception.DataNotFoundException;
import com.paymybuddy.sylvain.model.Relation;
import com.paymybuddy.sylvain.model.Role;
import com.paymybuddy.sylvain.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements  UserService {

    @Autowired
    private UserDAO userDao;

    @Autowired
    private RelationDAO relationDao;

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

    @Override
    public List<Relation> listEmailRelation(String emailOwner) {
        return relationDao.findAllByOwner_Email(emailOwner);
    }

    @Override
    public Boolean deleteBuddy(Integer id) {
        if(relationDao.existsById(id)) {
            relationDao.deleteById(id);
            return true;
        }
        return false;
    }

    @Override
    @Transactional
    public User addBuddy(BuddyFormDto buddyFormDto) {
        User owner = userDao.findByEmail(buddyFormDto.getOwner());
        // Si le owner a des relations
        if(owner.getRelations() != null && !owner.getRelations().isEmpty()) {
            // alors verifier si la relation existe deja
            if(owner.getRelations().stream().anyMatch(relation -> relation.getBuddy().getEmail().equals(buddyFormDto.getBuddy()))) {
                // Alors envoyer exception
                throw new DataAlreadyExistException("buddy already exist");
            }
        }

        User buddy = userDao.findByEmail(buddyFormDto.getBuddy());
        if(buddy == null) {
            throw  new DataNotFoundException("buddy doesnt exist");
        }
        Relation relation = new Relation();
        relation.setOwner(owner);
        relation.setBuddy(buddy);

        if(owner.getRelations() == null) {
            owner.setRelations(new ArrayList<>());
        }

        owner.getRelations().add(relation);
        return userDao.save(owner);
    }
}
