package com.paymybuddy.sylvain.controllers;

import static org.mockito.Mockito.when;

import java.sql.SQLException;
import java.util.Arrays;

import com.paymybuddy.sylvain.controller.RelationController;
import com.paymybuddy.sylvain.dto.BuddyFormDto;
import com.paymybuddy.sylvain.model.Relation;
import com.paymybuddy.sylvain.model.User;
import com.paymybuddy.sylvain.service.UserService;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.ui.Model;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@SpringBootTest
@AutoConfigureMockMvc
public class RelationControllerTest {
    @Autowired
    MockMvc mockMvc;
    @Autowired
    RelationController relationController;
    @MockBean
    UserService userService;
    @Mock
    BuddyFormDto buddyFormDto;
    @Mock
    UserDetails userDetails;

    @Mock
    RedirectAttributes redirectAttributes;
    @Mock
    Model model;

    @Test
    void relationPage() {
        when(userService.listEmailRelation("email1")).thenReturn(Arrays.asList(new Relation()));
        String result = relationController.relation(model, userDetails);
        Assertions.assertThat(result).isEqualTo("relation");
    }

    @Test
    void addRelation() throws SQLException {
        when(userService.addBuddy(buddyFormDto)).thenReturn(new User());
        String result = relationController.addBuddy(buddyFormDto, userDetails, redirectAttributes);
        Assertions.assertThat(result).isEqualTo("redirect:/user/relation?success");
    }

    @Test
    void deleteRelation() throws Exception {
        when(userService.deleteBuddy(1)).thenReturn(true);
        String result = relationController.deleteBuddy(1);
        Assertions.assertThat(result).isEqualTo("redirect:/user/relation");
    }
}
