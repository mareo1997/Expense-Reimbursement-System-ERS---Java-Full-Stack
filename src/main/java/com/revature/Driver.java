
package com.revature;

import java.sql.Timestamp;

import com.revature.model.ERSReimbursement;
import com.revature.model.ERSStatus;
import com.revature.model.ERSType;
import com.revature.model.Role;
import com.revature.model.User;
import com.revature.services.ReimbursementServicesImpl;
import com.revature.services.UserServicesImpl;
import com.revature.util.HibernateUtil;

public class Driver {

	// Instantiate a Hibernate Dao
	public static UserServicesImpl userserv = new UserServicesImpl();
	public static ReimbursementServicesImpl reimserv = new ReimbursementServicesImpl();

	public static void main(String[] args) {

		//initialValues();
		//profile(); //done
		allEmpl();
		//submit(); //done
		HibernateUtil.closeSes();
	}
	
	public static void profile() {
		System.out.println(userserv.profile(3));
	}
	
	public static void allEmpl() {
		System.out.println(userserv.allEmp());
	}
	
	public static void submit() {
		Timestamp timestamp = new Timestamp(System.currentTimeMillis());
		ERSReimbursement reim = new ERSReimbursement(18.5, timestamp, "Test2", userserv.profile(3));
		ERSStatus s = new ERSStatus("PENDING", reim);
		ERSType t = new ERSType("LODGE", reim);
		
		/*reimserv.insert(reim);
		reimserv.insert(s);
		reimserv.insert(t);*/
		
		reimserv.submit(reim, s, t);
	}
	
	public static void initialValues() {

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
}
