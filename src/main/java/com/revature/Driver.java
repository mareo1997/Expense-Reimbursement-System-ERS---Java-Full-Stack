package com.revature;

import java.sql.Timestamp;

import com.revature.dao.UserDaoImpl;
import com.revature.model.ERSReimbursement;
import com.revature.model.ERSStatus;
import com.revature.model.ERSType;
import com.revature.model.Role;
import com.revature.model.User;
import com.revature.util.HibernateUtil;

public class Driver {
	
	public static void main(String[] args) {
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());

		//Instantiate a Hibernate Dao
		UserDaoImpl userserv = new UserDaoImpl();
						
		//Instantiate a BankUser Mapped to a Table, provide the primary key
		User user = new User("11","12", "13", "14", "15");
		userserv.insert(user);
		
		Role role = new Role("EMPLOYEE",user);				
		userserv.insert(role);
				
		ERSReimbursement reim = new ERSReimbursement(5.5,timestamp,"d",user);
		userserv.insert(reim);
		
		ERSStatus s = new ERSStatus("TEST", reim);
		userserv.insert(s);
		
		ERSType t = new ERSType("Test", reim);
		userserv.insert(t);
				
		System.out.println("done saving user to db");
		HibernateUtil.closeSes();

	}
}
