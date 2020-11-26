package com.revature.services;

import java.util.List;

import com.revature.model.Role;
import com.revature.model.User;

public interface UserServices {

	public void insert(User u);

	public void insert(Role r);

	public void profile(User u);

	public User profileHQL(User u);

	public List<User> allEmplHQL();

	public void allEmpl();

	public User updateHQL(User u, String fname, String lname, String email, String username, String password,
			String repassword);
}
