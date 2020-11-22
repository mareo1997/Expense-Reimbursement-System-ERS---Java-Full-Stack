package com.revature.services;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.revature.model.Role;
import com.revature.model.User;
import com.revature.util.ConnectionUtil;

public class LoginService {

	public static String sql;
	public static PreparedStatement ps;
	public static ResultSet rs;

	public static boolean login(String username, String password) {
		String u = null, p = null;
		try (Connection conn = ConnectionUtil.getConnectionFromFile("connection.properties")) {
			sql = "select username, erspassword from ersuser where username=? and erspassword=?";
			
			ps = conn.prepareStatement(sql);
			ps.setString(1, username);
			ps.setString(2, password);
			rs = ps.executeQuery();
			
			if(rs.next()) {
				u = rs.getString(1);
				p = rs.getString(2);
			}
			if (u != null && p != null) {
				return true;
				//return (username.equalsIgnoreCase("Aretha") && password.equals("awesome"));
			} else {
				return false;
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return false;
	}
	
	public static User confirm(String username, String password) {
		User user = null;
		try (Connection conn = ConnectionUtil.getConnectionFromFile("connection.properties")) {
			sql = "select * from ersuser e inner join roles r on r.userid =e.userid  where username=? and erspassword=?";
			
			ps = conn.prepareStatement(sql);
			ps.setString(1, username);
			ps.setString(2, password);
			rs = ps.executeQuery();
			
			if(rs.next()) {
				Role role = new Role(rs.getInt(7), rs.getString(8));
				user = new User(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(5), rs.getString(6),role);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return user;
	}
}
