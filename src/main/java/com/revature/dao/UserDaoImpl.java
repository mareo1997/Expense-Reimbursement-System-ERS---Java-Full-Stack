package com.revature.dao;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.Transaction;

import com.revature.model.Role;
import com.revature.model.User;
import com.revature.util.ConnectionUtil;
import com.revature.util.HibernateUtil;

/*
 * Session methods:
 * 
 * save() and persist() ---- result in a SQL insert
 * update() and merge() ---- result in a SQL update
 * saveOrUpdate() ---------- result in a SQL insert OR update (depends)
 * get() and load()	-------- result in a SQL select 
 * 
 * 
 * There are 3 different ways to create complex queries in Hibernate:
 * 
 * 1. HQL - Hibernate Query Language
 * 2. Criteria API
 * 3. Native SQL 
 * 
 */
public class UserDaoImpl implements UserDao{

	private static Logger log = Logger.getLogger(UserDaoImpl.class);
	Session ses;
	
	public UserDaoImpl() {
		// TODO Auto-generated constructor stub
	}
	
	@Override
	public void insert(User u) {
		//log.info("Attempting to insert user\n");
		Session ses = HibernateUtil.getSession(); // capture the session
		Transaction tx = ses.beginTransaction();  // perform an operation on DB
		
		ses.save(u); // use the save() session method to perform an insert operation
		tx.commit(); // commit the transaction by utilizing the methods from the Transaction interface
	}
	
	@Override
	public void insert(Role r) {
		//log.info("Attempting to insert user\n");
		Session ses = HibernateUtil.getSession(); // capture the session
		Transaction tx = ses.beginTransaction();  // perform an operation on DB
		
		ses.save(r); // use the save() session method to perform an insert operation
		tx.commit(); // commit the transaction by utilizing the methods from the Transaction interface
	}

	@Override
	public User profile(int id) {
		ses = HibernateUtil.getSession();
		User u = ses.get(User.class, id);
		return u;
	}
	
	@Override
	public List<Role> allEmp() { //- A Manager can view all Employees **DONE**
		ses = HibernateUtil.getSession();		
		// This is an example of HQL --> HQL will check for the class name (of our java models)
		//List<Role> r = ses.createQuery("from Role where role = 'MANAGER' ").list();
		//System.out.println(r);
		List<Role> empl = ses.createQuery("from Role", Role.class).list();
		// HQL is returning all instances of the User class
		// no need for transaction object here We are just querying Data, NOT committing any changes to our database, hence it's not a transaction
		return empl;
	}
	
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
	public void allEmpl() {
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
