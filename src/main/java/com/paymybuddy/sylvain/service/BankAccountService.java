package com.paymybuddy.sylvain.service;

import com.paymybuddy.sylvain.dto.BankAccountDto;
import com.paymybuddy.sylvain.model.BankAccount;

import java.sql.SQLException;
import java.util.List;

public interface BankAccountService {

    List<BankAccount> findBankAccountByUser(String username);

    BankAccount addBankAccount(String emailOwner, BankAccountDto bankAccountDto) throws SQLException;

    Boolean deleteBankAccount(String iban);

}

