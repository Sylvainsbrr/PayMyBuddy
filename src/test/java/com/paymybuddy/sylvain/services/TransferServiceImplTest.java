package com.paymybuddy.sylvain.services;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;

import com.paymybuddy.sylvain.dao.*;
import com.paymybuddy.sylvain.dto.ExternalTransferDto;
import com.paymybuddy.sylvain.dto.InternalTransferDto;
import com.paymybuddy.sylvain.dto.UserRegistrationDto;
import com.paymybuddy.sylvain.exception.DataNotFoundException;
import com.paymybuddy.sylvain.model.*;
import com.paymybuddy.sylvain.service.TransferServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.mockito.ArgumentMatchers.any;


@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
public class TransferServiceImplTest {

    @Autowired
    TransferServiceImpl transferService;
    @MockBean
    UserDAO userDao;
    @MockBean
    RelationDAO relationDao;
    @MockBean
    TransferDAO transferDao;
    @MockBean
    InternalTransferDAO internalTransferDao;
    @MockBean
    ExternalTransferDAO externalTransferDao;
    @MockBean
    BankAccountDAO bankAccountDao;

    User owner = new User(1, "barack", "obama", "a@a.com", "1234", BigDecimal.ZERO,
            Timestamp.valueOf(LocalDateTime.now()));
    User buddy = new User(2, "george", "bush", "b@b.com", "4321", BigDecimal.ZERO,
            Timestamp.valueOf(LocalDateTime.now()));

    Relation relation = new Relation(owner, buddy);

    Transfer transfer = new Transfer();

    Role role = new Role("USER");

    BankAccount bankAccount = new BankAccount("String iban", "String bic", "String bankName"," String accountName", owner);

    InternalTransferDto internalTransferDto = new InternalTransferDto();
    ExternalTransferDto externalTransferDto = new ExternalTransferDto("String ibanUser", BigDecimal.TEN, "String emailUser", "String description",
            BigDecimal.ONE);
    UserRegistrationDto userRegistrationDto = new UserRegistrationDto("barack", "obama", "a@a.com", "1234");
    InternalTransfer internalTransfer = new InternalTransfer();

    @Test
    void doInternalTransfer() throws SQLException {
        try {
            transferService.doInternalTransfer(internalTransferDto);
            verify(transferDao, times(0)).save(transfer);
            verify(userDao, times(0)).save(owner);
            verify(relationDao, times(1)).findByOwner_EmailAndBuddy_Email(any(), any());

        } catch (DataNotFoundException e) {
            assert (e.getMessage().contains("les 2 user ne sont pas amis"));
        }
    }

    @Test
    void doExternalTransfer() throws SQLException {
        when(bankAccountDao.findBankAccountByIbanAndUser_Email(any(), any())).thenReturn(bankAccount);
        transferService.doExternalTransfer(externalTransferDto);
        verify(transferDao, times(0)).save(transfer);
        verify(userDao, times(1)).save(owner);
        verify(relationDao, times(0)).findByOwner_EmailAndBuddy_Email(any(), any());
    }

    @Test
    void findInternalTransferByUser() {
    }

    @Test
    void findExternalTransferByUser() {
    }
}