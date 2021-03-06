package com.paymybuddy.sylvain.dao;

import com.paymybuddy.sylvain.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserDAO extends JpaRepository<User,Integer> {

    User findByEmail(String email);

}
