package com.paymybuddy.sylvain.dao;

import com.paymybuddy.sylvain.model.BankAccount;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BankAccountDAO extends JpaRepository<BankAccount,String> {

}
