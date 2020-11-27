package com.revature.services;

import java.util.List;

import com.revature.dao.UserDaoImpl;
import com.revature.model.Role;
import com.revature.model.User;

public class UserServicesImpl implements UserServices {

	UserDaoImpl dao = new UserDaoImpl();

	@Override
	public void insert(User u) {
		dao.insert(u);
	}

	@Override
	public void insert(Role r) {
		dao.insert(r);
	}

	@Override
	public void profile(User u) {
		dao.profile(u);
	}

	@Override
	public User profileHQL(User u) {
		return dao.profileHQL(u);
	}

	@Override
	public User updateHQL(User u, String fname, String lname, String email, String username, String password,
			String repassword) {
		return dao.updateHQL(u, fname, lname, email, username, password, repassword);
	}

	@Override
	public void allEmpl() {
		dao.allEmpl();
	}

	@Override
	public List<User> allEmplHQL() {
		return dao.allEmplHQL();
	}
	
	@Override
	public Role roleHQL() {
		return dao.roleHQL();
	}

}