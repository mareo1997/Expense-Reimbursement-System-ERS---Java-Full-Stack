package com.revature.services;

import java.util.List;

import com.revature.model.Role;
import com.revature.model.User;

public interface UserServices {

	public void insert(User u);

	public void insert(Role r);

	public void profile(User u);

	public User profileHQL(int id);

	public void update();

	public List<User> allEmplHQL();

	public void allEmpl();
}
