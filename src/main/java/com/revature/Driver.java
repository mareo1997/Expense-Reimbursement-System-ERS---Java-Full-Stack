
package com.revature;

import java.sql.Timestamp;

import com.revature.model.Reimbursement;
import com.revature.model.Role;
import com.revature.model.Status;
import com.revature.model.Type;
import com.revature.model.User;
import com.revature.services.LoginService;
import com.revature.services.ReimbursementServicesImpl;
import com.revature.services.UserServicesImpl;
import com.revature.util.HibernateUtil;

public class Driver {

	// Instantiate a Hibernate Dao
	public static UserServicesImpl userserv = new UserServicesImpl();
	public static ReimbursementServicesImpl reimserv = new ReimbursementServicesImpl();

	public static void main(String[] args) {

		//initialValues();
		// profile(); //done
		// allEmpl();
		 submit(); //done
		// pending();

		// resolved();
		HibernateUtil.closeSes();
	}

	/*public static void pending() {
		List<Reimbursement> pending = reimserv.pendingHQL(userserv.profileHQL(3));
		System.out.println(pending);
	}

	public static void resolved() {
		List<Reimbursement> resolved = reimserv.resolvedHQL(userserv.profileHQL(3));
		System.out.println(resolved);
	}

	public static void profile() {
		System.out.println(userserv.profileHQL(3));
	}*/

	public static void allEmpl() {
		System.out.println(userserv.allEmplHQL());
	}

	public static void submit() {
		// Timestamp timestamp = new Timestamp(System.currentTimeMillis());
		// Reimbursement reim = new Reimbursement(1000, timestamp, "HILTON INN",
		// userserv.profile(3));

		/*
		 * reimserv.insert(reim); reimserv.insert(s); reimserv.insert(t);
		 */
		Role role1 = new Role(1, "EMPLOYEE");
		User user = new User("u", "p", "f", "l", "@", role1);
		userserv.insert(user);
	}

	public static void initialValues() {
		Timestamp timestamp = new Timestamp(System.currentTimeMillis());

		Role role1 = new Role(1, "EMPLOYEE");
		userserv.insert(role1);
		Role role2 = new Role(2, "MANAGER");
		userserv.insert(role2);

		System.out.println("done saving user to db");

		// Instantiate a BankUser Mapped to a Table, provide the primary key
		User user = new User("mareo1997", "password", "Mareo", "Yapp", "mareo1997@gmail.com", role1);
		User mareo = user;
		userserv.insert(user);

		user = new User("marwil", "william", "Marcia", "Williamson", "mother@gmail.com", role1);
		userserv.insert(user);

		user = new User("king", "george", "Kingsley", "Yapp", "father@gmail.com", role2);
		userserv.insert(user);

		System.out.println("done saving user to db");

		Status status = new Status(1, "PENDING");
		reimserv.insert(status);
		status = new Status(2, "APPROVED");
		Status approved = status;
		reimserv.insert(status);
		status = new Status(3, "DENIED");
		reimserv.insert(status);

		System.out.println("done saving user to db");

		Type type = new Type(1, "LODGE");
		Type lodge = type;
		reimserv.insert(type);
		type = new Type(2, "TRAVEL");
		reimserv.insert(type);
		type = new Type(3, "FOOD");
		reimserv.insert(type);
		type = new Type(4, "OTHER");
		reimserv.insert(type);

		System.out.println("done saving user to db");

		Reimbursement reim = new Reimbursement(mareo, 1897, "HILTON INN", timestamp, approved, lodge);
		reimserv.insert(reim);

		System.out.println("done saving user to db");

		User login = LoginService.confirm(mareo.getUsername(), mareo.getPassword());
		System.out.println("login " + login);

	}
}
