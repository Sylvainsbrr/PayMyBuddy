package com.paymybuddy.sylvain.service;

import com.paymybuddy.sylvain.dao.*;
import com.paymybuddy.sylvain.dto.ExternalTransferDto;
import com.paymybuddy.sylvain.dto.InternalTransferDto;
import com.paymybuddy.sylvain.exception.DataNotExisteException;
import com.paymybuddy.sylvain.exception.DataNotFoundException;
import com.paymybuddy.sylvain.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class TransferServiceImpl implements TransferService{

    @Autowired
    private TransferDAO transferDao;
    @Autowired
    private InternalTransferDAO internalTransferDao;
    @Autowired
    private RelationDAO relationDao;
    @Autowired
    private UserDAO userDao;
    @Autowired
    private ExternalTransferDAO externalTransferDao;
    @Autowired
    private BankAccountDAO bankAccountDao;

    @Override
    public List<InternalTransferDto> findInternalTransferByUser(String emailOwner) {
        List<InternalTransferDto> internalTransferDtos = new ArrayList<>();
        for (InternalTransfer internalTransfer : internalTransferDao
                .findAllByUserSender_EmailOrderByTransactionDateDesc(emailOwner)) {
            InternalTransferDto dto = new InternalTransferDto();
            dto.setEmailSender(internalTransfer.getUserSender().getEmail());
            dto.setEmailReceiver(internalTransfer.getUserReceiver().getEmail());
            dto.setAmount(internalTransfer.getAmount());
            dto.setId(internalTransfer.getId());
            dto.setDescription(internalTransfer.getDescription());
            internalTransferDtos.add(dto);
        }
        return internalTransferDtos;
    }

    @Transactional
    @Override
    public InternalTransferDto doInternalTransfer(InternalTransferDto internalTransferDto) {
        Relation relation = relationDao.findByOwner_EmailAndBuddy_Email(internalTransferDto.getEmailSender(),
                internalTransferDto.getEmailReceiver());
        // Verification de la relation
        if (relation == null) {
            throw new DataNotFoundException("les 2 user ne sont pas amis");
        }
        // Verification du montant minimum pour la transaction
        if (internalTransferDto.getAmount().compareTo(relation.getOwner().getBalance()) > 0) {
            throw new DataNotExisteException("fonds insuffisants");
        }
        InternalTransfer internalTransfer = new InternalTransfer();
        internalTransfer.setUserSender(relation.getOwner());
        internalTransfer.setUserReceiver(relation.getBuddy());
        internalTransfer.setAmount(internalTransferDto.getAmount());
        internalTransfer.setDescription(internalTransferDto.getDescription());
        internalTransfer.setTransactionDate(Timestamp.valueOf(LocalDateTime.now()));

        transferDao.save(internalTransfer);

        internalTransferDto.setId(internalTransfer.getId());
        // On met a jour la balance des 2 UTILISATEURS

        relation.getOwner().setBalance(relation.getOwner().getBalance().subtract(internalTransferDto.getAmount()));
        relation.getBuddy().setBalance(relation.getBuddy().getBalance().add(internalTransferDto.getAmount()));
        userDao.save(relation.getOwner());
        userDao.save(relation.getBuddy());

        return internalTransferDto;
    }

    @Override
    public List<ExternalTransferDto> findExternalTransferByUser(String emailOwner) {
        List<ExternalTransferDto> externalTransferDtos = new ArrayList<>();
        for (ExternalTransfer externalTransfer : externalTransferDao
                .findAllByBankAccount_User_EmailOrderByTransactionDateDesc(emailOwner)) {
            ExternalTransferDto dto = new ExternalTransferDto();
            dto.setIbanUser(externalTransfer.getBankAccount().getIban());
            dto.setDescription(externalTransfer.getDescription());
            dto.setAmountUser(externalTransfer.getAmount());
            dto.setFees(externalTransfer.getFees());
            externalTransferDtos.add(dto);
        }
        return externalTransferDtos;
    }

    @Override
    public ExternalTransferDto doExternalTransfer(ExternalTransferDto externalTransferDto) {
        // Recuperation bankaccount en fontion de l'iban et de l'email du user
        BankAccount bankAccount = bankAccountDao.findBankAccountByIbanAndUser_Email(externalTransferDto.getIbanUser(),
                externalTransferDto.getEmailUser());
        User user = bankAccount.getUser();
        // On attribut le dernier iban ajouté.
        // Fees
        BigDecimal fee = externalTransferDto.getAmountUser().multiply(BigDecimal.valueOf(0.005));

        ExternalTransfer externalTransfer = new ExternalTransfer();
        externalTransfer.setAmount(externalTransferDto.getAmountUser());
        externalTransfer.setDescription(externalTransferDto.getDescription());
        externalTransfer.setTransactionDate(Timestamp.valueOf(LocalDateTime.now()));
        externalTransfer.setFees(fee);
        externalTransfer.setBankAccount(bankAccount);

        transferDao.save(externalTransfer);

        externalTransferDto.setId(externalTransfer.getId());
        user.setBalance(user.getBalance().add(externalTransfer.getAmount().subtract(fee)));

        userDao.save(user);

        return externalTransferDto;
    }
}
