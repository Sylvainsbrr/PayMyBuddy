package com.paymybuddy.sylvain.controllers;

import com.paymybuddy.sylvain.controller.MainController;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
@AutoConfigureMockMvc
class MainControllerTest {

    @Autowired
    MainController mainController;

    @Test
    void login() {
        String result = mainController.login();
        Assertions.assertThat(result).isEqualTo("login");
    }

    @Test
    void home() {
        String result = mainController.home();
        Assertions.assertThat(result).isEqualTo("index");
    }
}
