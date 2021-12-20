package com.paymybuddy.sylvain.dao;

import com.paymybuddy.sylvain.model.Transfer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TransferDAO extends JpaRepository<Transfer,Integer> {
}
