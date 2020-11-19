package com.revature;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

import com.revature.dao.ReimbursmentDaoImpl;
import com.revature.dao.UserDaoImpl;
import com.revature.model.ERSReimbursement;
import com.revature.model.ERSStatus;
import com.revature.model.ERSType;
import com.revature.model.User;
import com.revature.services.LoginService;

public class DriverTest {
	
	private static final UserDaoImpl user = new UserDaoImpl();
	private static final ReimbursmentDaoImpl reim = new ReimbursmentDaoImpl();


	@Before
	public void setUp() throws Exception {
		// TODO: Make real unit tests using Mockito to mock DAOs for Service layer

	}

	@Test
	public void test() {
		assertTrue(true);
	}
	
	@Test
	public void login() {
		assertTrue(LoginService.login("king","george"));
		assertFalse(LoginService.login("a","a"));
	}
	
	@Test
	public void submit() {
		//User u = new User(1, "mareo1997", "password", "Mareo", "Yapp", "mareo1997@gmail.com");
		User u = new User(2, "marwil", "william", "Marcia", "Williamson", "mother@gmail.com");
		ERSType t = new ERSType("LODGE");
		ERSStatus s = new ERSStatus("PENDING");
		ERSReimbursement r = new ERSReimbursement(u.getUserid(),"TESTING STATUS AND TYPE",5.5,s,t);
				
		reim.submit(u,r);
	}
	
	@Test
	public void profile() {
		//User u = new User(1, "mareo1997", "password", "Mareo", "Yapp", "mareo1997@gmail.com");
		User u = new User(2, "marwil", "william", "Marcia", "Williamson", "mother@gmail.com");
		user.profile(u);
	}
	
	@Test
	public void pending() {
		User u = new User(1, "mareo1997", "password", "Mareo", "Yapp", "mareo1997@gmail.com");
		//User u = new User(2, "marwil", "william", "Marcia", "Williamson", "mother@gmail.com");
		reim.pending(u);
	}
	
	@Test
	public void resolved() {
		//User u = new User(1, "mareo1997", "password", "Mareo", "Yapp", "mareo1997@gmail.com");
		User u = new User(2, "marwil", "william", "Marcia", "Williamson", "mother@gmail.com");
		reim.resolved(u);
	}

	@Test
	public void resolve() {
		User u = new User(1, "mareo1997", "password", "Mareo", "Yapp", "mareo1997@gmail.com");
		//User u = new User(2, "marwil", "william", "Marcia", "Williamson", "mother@gmail.com");

		reim.resolve(2, "APPROVED", 3);
		reim.resolved(u);
	}
	
	@Test
	public void allEmployees() {
		user.allEmpl();
	}

	@Test
	public void requests() {
		reim.requests(2);
	}
	
	@Test
	public void resolvedrequests() {
		reim.resolvedrequests();
	}
	
	@Test
	public void pendingrequests() {
		reim.pendingrequests();
	}

}
