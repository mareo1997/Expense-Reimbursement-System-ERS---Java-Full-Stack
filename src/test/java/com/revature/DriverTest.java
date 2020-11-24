package com.revature;

import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

import com.revature.services.LoginService;
import com.revature.services.ReimbursementServicesImpl;
import com.revature.services.UserServicesImpl;

public class DriverTest {

	private static final UserServicesImpl user = new UserServicesImpl();
	private static final ReimbursementServicesImpl reim = new ReimbursementServicesImpl();

	@Before
	public void setUp() throws Exception {
		// TODO: Make real unit tests using Mockito to mock DAOs for Service layer

	}

	@Test
	public void test() {
		assertTrue(true);
	}

	@Test
	public void login() { // HQL passed
		System.out.println(LoginService.confirm("mareo1997", "password"));
		/// assertTrue(LoginService.login("king","george"));
		// assertFalse(LoginService.login("a","a"));
	}

	/*
	 * @Test public void submit() { //User u = new User(1, "mareo1997", "password",
	 * "Mareo", "Yapp", "mareo1997@gmail.com"); User u = new User(2, "marwil",
	 * "william", "Marcia", "Williamson", "mother@gmail.com"); Type t = new
	 * Type("LODGE"); Status s = new Status("PENDING"); Reimbursement r = new
	 * Reimbursement();
	 * 
	 * reim.submit(u,r); }
	 * 
	 * @Test public void profile() { //User u = new User(1, "mareo1997", "password",
	 * "Mareo", "Yapp", "mareo1997@gmail.com"); User u = new User(2, "marwil",
	 * "william", "Marcia", "Williamson", "mother@gmail.com"); user.profile(u); }
	 * 
	 * @Test public void pending() { //User u = new User(1, "mareo1997", "password",
	 * "Mareo", "Yapp", "mareo1997@gmail.com"); User u = new User(2, "marwil",
	 * "william", "Marcia", "Williamson", "mother@gmail.com"); List<Reimbursement>
	 * reimburse = reim.pending(u); if (reimburse.size() > 0) { for (Reimbursement r
	 * : reimburse) { System.out.println(r); } }else {
	 * System.out.println(u.getUsername()+" has no pending requests\n"); } }
	 * 
	 * @Test public void resolved() { //User u = new User(1, "mareo1997",
	 * "password", "Mareo", "Yapp", "mareo1997@gmail.com"); User u = new User(2,
	 * "marwil", "william", "Marcia", "Williamson", "mother@gmail.com");
	 * reim.resolved(u); }
	 * 
	 * @Test public void resolve() { User u2 = new User(1, "mareo1997", "password",
	 * "Mareo", "Yapp", "mareo1997@gmail.com"); //User u2 = new User(2, "marwil",
	 * "william", "Marcia", "Williamson", "mother@gmail.com"); User u = new User(3,
	 * "king", "george", "Kingsley", "Yapp", "father@gmail.com");
	 * 
	 * reim.resolve(2, "APPROVED", u.getUserid()); reim.resolved(u2); }
	 */

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
