package com.revature;

import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.List;

import org.apache.log4j.BasicConfigurator;
import org.apache.log4j.Logger;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import com.revature.dao.UserDaoImpl;
import com.revature.model.Role;
import com.revature.model.User;
import com.revature.services.UserServicesImpl;

public class UserTest {

	private static Logger log = Logger.getLogger(UserTest.class);

	@InjectMocks
	private UserServicesImpl userserv;

	@Mock
	private UserDaoImpl userdao;
	
	@Before
	public void setUp() throws Exception {
		// TODO: Make real unit tests using Mockito to mock DAOs for Service layer
		BasicConfigurator.configure();
		userserv = new UserServicesImpl();
		MockitoAnnotations.initMocks(this);
		System.out.println("Set up Mockito\n");		
	}
	
	@Test
	public void profilePass() {
		log.info("Profile test\n");
		User u = userserv.useridHQL(1);
		when(userserv.profileHQL(u)).thenReturn(u);
		verify(userdao, times(1)).profileHQL(u);
	}
	
	@Test
	public void profileFail() {
		log.info("Profile test\n");
		User u = userserv.useridHQL(0);
		when(userserv.profileHQL(u)).thenReturn(null);
		verify(userdao, times(0)).profileHQL(u);

	}
	
	@Test
	public void insert() {
		log.info("Insert test\n");
		User u = Mockito.mock(User.class);
		userserv.insert(u);
		verify(userdao, times(1)).insert(u);
		
		Role r = Mockito.mock(Role.class);
		userserv.insert(r);
		verify(userdao, times(1)).insert(r);
	}
	
	@Test
	public void role() {
		log.info("Role test\n");
		userserv.roleHQL();	
		verify(userdao, times(1)).roleHQL();
	}
	
	@Test
	public void allempl() {
		log.info("All empl test\n");
		List <User> u = userserv.allEmplHQL();
		for(User user: u) {
			System.out.println(user);
		}
		verify(userdao, times(1)).allEmplHQL();
	}
	
	@Test
	public void update() {
		log.info("Update test\n");
		User u = userserv.useridHQL(21);
		userserv.profileHQL(u);
		userserv.updateHQL(u, "fname", "lname", "email", "username", "password", "repassword");
		verify(userdao, times(1)).updateHQL(u, "fname", "lname", "email", "username", "password", "repassword");
	}
	
	@Test
	public void userid() {
		User u = new User(21, "username", "password", "firstname", "lastname", "email", new Role(1, "EMPLOYEE"));
		assertNotEquals(u, userserv.useridHQL(1));
	}

	@Test
	public void test() {
		assertTrue(true);
	}

}
