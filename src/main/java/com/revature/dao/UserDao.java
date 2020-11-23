package com.revature.dao;

import java.util.List;

import com.revature.model.Role;
import com.revature.model.User;

public interface UserDao {
	
	public void insert(User u);
	public void insert(Role r);
	
	public void profile(User u);
	public User profile(int id);

	public void update();
	public void allEmpl();
	public List<Role> allEmp();

}
