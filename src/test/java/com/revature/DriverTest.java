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
