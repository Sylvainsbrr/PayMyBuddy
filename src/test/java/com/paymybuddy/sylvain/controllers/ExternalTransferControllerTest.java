package com.paymybuddy.sylvain.controllers;

import com.paymybuddy.sylvain.controller.ExternalTransferController;
import com.paymybuddy.sylvain.service.BankAccountService;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;

import static org.mockito.Mockito.when;

@SpringBootTest
@AutoConfigureMockMvc
class ExternalTransferControllerTest {

    @Autowired
    ExternalTransferController externalTransferController;
    @Mock
    BankAccountService bankAccountService;

    @Test
    void deleteBankAccount() {
        when(bankAccountService.deleteBankAccount("test")).thenReturn(true);
        String result = externalTransferController.deteleBankAccount("test");
        Assertions.assertThat(result).isEqualTo("redirect:/user/extransfer");
    }

}