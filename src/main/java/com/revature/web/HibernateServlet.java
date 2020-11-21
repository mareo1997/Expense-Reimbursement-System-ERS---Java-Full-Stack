package com.revature.web;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.revature.dao.UserDaoImpl;
import com.revature.model.Role;
import com.revature.model.User;

public class HibernateServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	public static UserDaoImpl userserv = new UserDaoImpl();

    public HibernateServlet() {
        // Here we will invoke the initialValues() method
    	initialValues();
    }

	private void initialValues() {
		//Instantiate a Hibernate Dao
		UserDaoImpl userserv = new UserDaoImpl();
						
		//Instantiate a BankUser Mapped to a Table, provide the primary key
		User user = new User("a","b", "c", "d", "e");
		Role role = new Role("EMPLOYEE");
		
				
		userserv.insert(user);
		userserv.insert(role);
		System.out.println("done saving user to db");
		
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	}

}
