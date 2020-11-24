package com.revature.dao;

import java.util.List;

import com.revature.model.Role;
import com.revature.model.User;

public interface UserDao {

	public void insert(User u);

	public void insert(Role r);

	public void profile(User u);

	public User profileHQL(User u);

	public User updateHQL(User u, String fname, String lname, String email, String username, String password, String repassword);

	public void allEmpl();

	public List<User> allEmplHQL();

}
