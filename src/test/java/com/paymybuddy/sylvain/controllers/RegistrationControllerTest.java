package com.paymybuddy.sylvain.controllers;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import java.sql.SQLException;

import com.paymybuddy.sylvain.controller.RegistrationController;
import com.paymybuddy.sylvain.dto.UserRegistrationDto;
import com.paymybuddy.sylvain.model.User;
import com.paymybuddy.sylvain.service.UserService;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

@SpringBootTest
@AutoConfigureMockMvc
public class RegistrationControllerTest {

    @Autowired
    MockMvc mockMvc;
    @Autowired
    RegistrationController registrationController;
    @MockBean
    UserService userService;
    @Mock
    UserRegistrationDto userRegistrationDto;

    @Test
    void showRegistrationForm() {
        when(userService.deleteBuddy(1)).thenReturn(true);
        String result = registrationController.showRegistrationForm();
        Assertions.assertThat(result).isEqualTo("registration");
    }

    @Test
    void registerUserAccount() throws SQLException {
        when(userService.save(any())).thenReturn(new User());
        String result = registrationController.registerUserAccount(userRegistrationDto);
        Assertions.assertThat(result).isEqualTo("redirect:/registration?success");
    }
}