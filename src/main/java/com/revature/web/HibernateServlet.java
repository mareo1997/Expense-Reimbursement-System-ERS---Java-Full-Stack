package com.revature.web;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.revature.dao.UserDaoImpl;
import com.revature.model.Role;
import com.revature.model.User;
import com.revature.services.ReimbursementServicesImpl;
import com.revature.util.HibernateUtil;

public class HibernateServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	public static UserDaoImpl userserv = new UserDaoImpl();
	public static ReimbursementServicesImpl reimserv = new ReimbursementServicesImpl();

    public HibernateServlet() {
        // Here we will invoke the initialValues() method
    	initialValues();

    }

	private void initialValues() {
		// Instantiate a BankUser Mapped to a Table, provide the primary key
		User user = new User("mareo1997", "password", "Mareo", "Yapp", "mareo1997@gmail.com");
		user.getRole().getRole();
		userserv.insert(user);
		Role role = new Role("EMPLOYEE", user);
		userserv.insert(role);
		
		user = new User("marwil", "william", "Marcia", "Williamson", "mother@gmail.com");
		userserv.insert(user);
		role = new Role("EMPLOYEE", user);
		userserv.insert(role);

		user = new User("king", "george", "Kingsley", "Yapp", "father@gmail.com");
		userserv.insert(user);
		role = new Role("MANAGER", user);
		userserv.insert(role);
		
		System.out.println("done saving user to db");
		
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		System.out.println(userserv.profile(3));
		System.out.println(userserv.allEmp());
		HibernateUtil.closeSes();
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	}

}
