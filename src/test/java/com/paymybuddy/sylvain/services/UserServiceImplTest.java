package com.paymybuddy.sylvain.services;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Arrays;

import com.paymybuddy.sylvain.dao.RelationDAO;
import com.paymybuddy.sylvain.dao.RoleDAO;
import com.paymybuddy.sylvain.dao.UserDAO;
import com.paymybuddy.sylvain.dto.BuddyFormDto;
import com.paymybuddy.sylvain.dto.UserRegistrationDto;
import com.paymybuddy.sylvain.exception.DataNotFoundException;
import com.paymybuddy.sylvain.model.Relation;
import com.paymybuddy.sylvain.model.Role;
import com.paymybuddy.sylvain.model.User;
import com.paymybuddy.sylvain.service.UserServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
public class UserServiceImplTest {

    @Autowired
    UserServiceImpl userService;
    @MockBean
    UserDAO userDao;
    @MockBean
    RelationDAO relationDao;
    @MockBean
    RoleDAO roleDao;

    User owner = new User(1, "john", "doe", "john@gmail.com", "1234", BigDecimal.ZERO,
            Timestamp.valueOf(LocalDateTime.now()));
    User buddy = new User(2, "tom", "poe", "tom@gmail.com", "4321", BigDecimal.ZERO,
            Timestamp.valueOf(LocalDateTime.now()));

    Relation relation = new Relation(owner, buddy);
    Role role = new Role("USER");

    UserRegistrationDto userRegistrationDto = new UserRegistrationDto("john", "doe", "john@gmail.com", "1234");

    @Test
    void listEmailRelation() {
        when(relationDao.findAllByOwner_Email(any())).thenReturn(Arrays.asList(relation));
        userService.listEmailRelation(any());
        verify(relationDao, times(1)).findAllByOwner_Email(any());
    }

    @Test
    void addBuddy() {
        BuddyFormDto buddyFormDto = new BuddyFormDto();
        buddyFormDto.setBuddy("mail1");
        buddyFormDto.setOwner("email2");
        try {
            when(userDao.findByEmail(buddyFormDto.getOwner())).thenReturn(owner);
            when(userDao.findByEmail(buddyFormDto.getBuddy())).thenReturn(buddy);
            when(userDao.save(owner)).thenReturn(owner);

            userService.addBuddy(buddyFormDto);
            verify(userDao, times(2)).findByEmail(any());
            verify(relationDao, times(0)).save(any());
            verify(userDao, times(1)).save(any());

        } catch (DataNotFoundException e) {
            assert (e.getMessage().contains("user doesnt exist"));
        }
    }

    @Test
    void deleteRelation() {
        when(relationDao.existsById(any())).thenReturn(true);
        userService.deleteBuddy(owner.getId());
        verify(relationDao, times(1)).deleteById(any());
    }

    @Test
    void save() throws SQLException {
        when(roleDao.findRoleByName("USER")).thenReturn(role);
        userService.save(userRegistrationDto);
        verify(roleDao, times(1)).findRoleByName(any());
    }
}
