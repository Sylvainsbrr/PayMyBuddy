package com.paymybuddy.sylvain.controllers;

import com.paymybuddy.sylvain.controller.InternalTransferController;
import com.paymybuddy.sylvain.dto.InternalTransferDto;
import com.paymybuddy.sylvain.model.Relation;
import com.paymybuddy.sylvain.service.TransferService;
import com.paymybuddy.sylvain.service.UserService;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.ui.Model;
import java.util.Arrays;
import static org.mockito.Mockito.when;

@SpringBootTest
@AutoConfigureMockMvc
public class InternalTransferControllerTest {
    @Autowired
    InternalTransferController internalTransferController;
    @Mock
    private TransferService transferService;
    @Mock
    private UserService userService;
    @Mock
    UserDetails userDetails;
    @Mock
    Model model;

    @Test
    void transferPage() {
        when(userService.listEmailRelation("email1")).thenReturn(Arrays.asList(new Relation()));
        when(transferService.findInternalTransferByUser("email1")).thenReturn(Arrays.asList(new InternalTransferDto()));
        String result = internalTransferController.internalTransferPage(model, userDetails);
        Assertions.assertThat(result).isEqualTo("internalTransfer");
    }
}