package com.paymybuddy.sylvain;

import com.paymybuddy.sylvain.dao.RelationDAO;
import com.paymybuddy.sylvain.dao.UserDAO;
import com.paymybuddy.sylvain.model.User;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

@SpringBootApplication
public class SylvainApplication {

	public static void main(String[] args) {
		ConfigurableApplicationContext context = SpringApplication.run(SylvainApplication.class, args);

		// Test la connexion JPA

//		UserDAO userDAO = context.getBean(UserDAO.class);
//		System.out.println(userDAO.findAll().get(0).getFirstname());

		//	Test relation

//		RelationDAO relationDAO = context.getBean(RelationDAO.class);
//		System.out.println(relationDAO.findAll().get(0).getBuddy().getFirstname());



	}

}
