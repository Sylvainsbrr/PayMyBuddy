package com.paymybuddy.sylvain.dao;

import com.paymybuddy.sylvain.model.ExternalTransfer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ExternalTransferDAO extends JpaRepository<ExternalTransfer,Integer> {
}
