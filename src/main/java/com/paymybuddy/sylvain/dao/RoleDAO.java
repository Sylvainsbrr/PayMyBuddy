package com.paymybuddy.sylvain.dao;

import com.paymybuddy.sylvain.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleDAO extends JpaRepository<Role,Integer> {
}
