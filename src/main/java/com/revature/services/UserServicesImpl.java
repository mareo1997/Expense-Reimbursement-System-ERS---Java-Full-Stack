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
	public User profile(int id) {
		return dao.profile(id);		
	}

	@Override
	public void update() {
		dao.update();
	}

	@Override
	public void allEmpl() {
		 dao.allEmpl();
	}
	
	@Override
	public List<User> allEmp() {
		return dao.allEmp();
	}



	
}
