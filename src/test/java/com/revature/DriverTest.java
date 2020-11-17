package com.revature;

import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

import com.revature.services.LoginService;

public class DriverTest {


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
		assertTrue(LoginService.login("marwil","william"));
	}

}
