package com.paymybuddy.sylvain.dao;

import com.paymybuddy.sylvain.model.InternalTransfer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface InternalTransferDAO extends JpaRepository<InternalTransfer,Integer> {
}
