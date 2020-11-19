package com.revature.dao;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import org.apache.log4j.Logger;

import com.revature.model.Role;
import com.revature.model.User;
import com.revature.util.ConnectionUtil;


public class UserDaoImpl implements UserDao{

	private static Logger log = Logger.getLogger(UserDaoImpl.class);

	public String sql, call;
	public PreparedStatement ps;
	public ResultSet rs;

	@Override
	public void profile(User u) { //- An Employee can view their information **DONE**

		User user = null;
		Role role;
		
		try (Connection conn = ConnectionUtil.getConnectionFromFile("connection.properties")) {
			log.info("Attempting to access profile\n");
			
			sql = "select * from ersuser u "
				+ "inner join roles r on r.userid=u.userid "
				+ "where u.userid=?";
			ps=conn.prepareStatement(sql);
			ps.setInt(1, u.getUserid());
			rs=ps.executeQuery();
			
			if(rs.next()) {
				role = new Role(rs.getInt(7), rs.getString(8));
				user = new User(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(5), rs.getString(6),role);
			}
			
			if(user!=null) {
			System.out.println(user);
			}else {
				log.warn("Not found\n");
				System.out.println("Not found");
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public void update() {
		// TODO Auto-generated method stub
		
	}

	
	@Override
	public void allEmpl() { //- A Manager can view all Employees **DONE**

		ArrayList<User> empl = new ArrayList<>();
		Role role;
		
		try (Connection conn = ConnectionUtil.getConnectionFromFile("connection.properties")) {
			
			log.info("Attempting to get all employees\n");
			
			sql = "select * FROM ersuser e "
				+ "inner join roles r on r.userid =e.userid "
				+ "WHERE r.ersroles ='EMPLOYEE'";
			ps = conn.prepareStatement(sql);
			rs = ps.executeQuery();
			
			while(rs.next()) {
				role = new Role(rs.getInt(7), rs.getString(8));
				empl.add(new User(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(5), rs.getString(6),role));
			}
			
			for(User u: empl) {
				System.out.println(u);
			}
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
