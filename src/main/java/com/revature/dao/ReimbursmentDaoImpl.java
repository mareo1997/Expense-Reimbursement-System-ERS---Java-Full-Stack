package com.revature.dao;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import org.apache.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.postgresql.util.PSQLException;

import com.revature.model.ERSReimbursement;
import com.revature.model.ERSStatus;
import com.revature.model.ERSType;
import com.revature.model.Role;
import com.revature.model.User;
import com.revature.util.ConnectionUtil;
import com.revature.util.HibernateUtil;

public class ReimbursmentDaoImpl implements ReimburmentDao {

	private static Logger log = Logger.getLogger(ReimbursmentDaoImpl.class);

	public String sql, call;
	public PreparedStatement ps;
	public ResultSet rs;

	@Override
	public void submit(ERSReimbursement r, ERSStatus s, ERSType t) {
		Session ses = HibernateUtil.getSession(); // capture the session

		Transaction tx = ses.beginTransaction();
		ses.save(r);
		tx.commit(); // commit the transaction by utilizing the methods from the Transaction interface
		
		tx = ses.beginTransaction();  // perform an operation on DB
		
		ses.save(s); // use the save() session method to perform an insert operation
		tx.commit(); // commit the transaction by utilizing the methods from the Transaction interface
		
		tx = ses.beginTransaction();  // perform an operation on DB
		
		ses.save(t); // use the save() session method to perform an insert operation
		tx.commit(); // commit the transaction by utilizing the methods from the Transaction interface

	}
	
	@Override
	public void insert(ERSReimbursement e) {
		//log.info("Attempting to insert user\n");
		Session ses = HibernateUtil.getSession(); // capture the session
		Transaction tx = ses.beginTransaction();  // perform an operation on DB
		
		ses.save(e); // use the save() session method to perform an insert operation
		tx.commit(); // commit the transaction by utilizing the methods from the Transaction interface

	}
	
	@Override
	public void insert(ERSStatus s) {
		//log.info("Attempting to insert user\n");
		Session ses = HibernateUtil.getSession(); // capture the session
		Transaction tx = ses.beginTransaction();  // perform an operation on DB
		
		ses.save(s); // use the save() session method to perform an insert operation
		tx.commit(); // commit the transaction by utilizing the methods from the Transaction interface
	}
	
	@Override
	public void insert(ERSType t) {
		//log.info("Attempting to insert user\n");
		Session ses = HibernateUtil.getSession(); // capture the session
		Transaction tx = ses.beginTransaction();  // perform an operation on DB
		
		ses.save(t); // use the save() session method to perform an insert operation
		tx.commit(); // commit the transaction by utilizing the methods from the Transaction interface
	}
	
	@Override
	public void submit(User u, ERSReimbursement r) { // - An Employee can submit a reimbursement request **DONE**

		ERSReimbursement r2;
		ArrayList<ERSReimbursement> reimburse = new ArrayList<>();
		ArrayList<ERSStatus> status = new ArrayList<>();
		ArrayList<ERSType> type = new ArrayList<>();

		try (Connection conn = ConnectionUtil.getConnectionFromFile("connection.properties")) {

			log.info("Attempting to insert reimbursment\n");

			sql = "INSERT into reimbursment (amount,description,author) values (?,?,?)";
			ps = conn.prepareStatement(sql);
			ps.setDouble(1, r.getAmt());
			ps.setString(2, r.getDescription());
			ps.setInt(3, u.getUserid());
			ps.executeUpdate();

			sql = "select * from reimbursment";
			ps = conn.prepareStatement(sql);
			rs = ps.executeQuery();

			while (rs.next()) {
				reimburse.add(new ERSReimbursement(rs.getInt(1), rs.getInt(2), rs.getString(3), rs.getDouble(4),
						rs.getTimestamp(5)));
			}
			r2 = reimburse.get(reimburse.size() - 1);// Get last account

			log.info("Attempting to insert status and type\n");

			call = "call insert_statustype(?, ?)";
			ps = conn.prepareStatement(call);
			ps.setString(1, r.getType().getType());
			ps.setInt(2, r2.getErsid());
			ps.executeUpdate();

			sql = "SELECT s.statusid, s.status, t.typeid, t.erstype " + "from ersstatus s inner join erstype t "
					+ "on s.reimbursmentid = t.reimbursmentid";
			ps = conn.prepareStatement(sql);
			rs = ps.executeQuery();

			while (rs.next()) {
				status.add(new ERSStatus(rs.getInt(1), rs.getString(2)));
				type.add(new ERSType(rs.getInt(3), rs.getString(4)));
			}
			ERSStatus s = status.get(status.size() - 1);
			ERSType t = type.get(type.size() - 1);

			r2 = new ERSReimbursement(r2.getErsid(), r2.getAuthor(), r2.getDescription(), r2.getAmt(),
					r2.getSubmitted(), s, t);
			System.out.println(r2);

		} catch (PSQLException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public void pending(User u) { // - An Employee can view their pending reimbursement requests **DONE**

		ArrayList<ERSReimbursement> reimburse = new ArrayList<>();
		ERSType type = null;
		ERSStatus status = null;

		try (Connection conn = ConnectionUtil.getConnectionFromFile("connection.properties")) {
			log.info("Attempting to get " + u.getUsername() + " pending list\n");

			sql = "select * from reimbursment r " + "inner join ersstatus s on r.reimbursmentid =s.reimbursmentid "
					+ "inner join erstype t on s.reimbursmentid = t.reimbursmentid "
					+ "where s.status ='PENDING' and r.author =?";
			ps = conn.prepareStatement(sql);
			ps.setInt(1, u.getUserid());
			rs = ps.executeQuery();

			while (rs.next()) {
				type = new ERSType(rs.getInt(12), rs.getString(13));
				status = new ERSStatus(rs.getInt(9), rs.getString(10));
				reimburse.add(new ERSReimbursement(rs.getInt(1), rs.getInt(2), rs.getString(3), rs.getDouble(4),
						rs.getTimestamp(5), rs.getInt(7), rs.getTimestamp(8), status, type));
			}

			if (reimburse.size() > 0) {
				for (ERSReimbursement r : reimburse) {
					System.out.println(r);
				}
			}else {
				System.out.println(u.getUsername()+" has no pending requests\n");
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
	public void resolved(User u) { // - An Employee can view their resolved reimbursement requests **DONE**

		ArrayList<ERSReimbursement> reimburse = new ArrayList<>();
		ERSType type = null;
		ERSStatus status = null;

		try (Connection conn = ConnectionUtil.getConnectionFromFile("connection.properties")) {
			log.info("Attempting to get " + u.getUsername() + " resolved list\n");

			sql = "select * from reimbursment r " + "inner join ersstatus s on r.reimbursmentid =s.reimbursmentid "
					+ "inner join erstype t on s.reimbursmentid = t.reimbursmentid "
					+ "where r.author =? and not s.status ='PENDING'";
			ps = conn.prepareStatement(sql);
			ps.setInt(1, u.getUserid());
			rs = ps.executeQuery();

			while (rs.next()) {
				type = new ERSType(rs.getInt(12), rs.getString(13));
				status = new ERSStatus(rs.getInt(9), rs.getString(10));
				reimburse.add(new ERSReimbursement(rs.getInt(1), rs.getInt(2), rs.getString(3), rs.getDouble(4),
						rs.getTimestamp(5), rs.getInt(7), rs.getTimestamp(8), status, type));
			}

			
			if (reimburse.size() > 0) {
				for (ERSReimbursement r : reimburse) {
					System.out.println(r);
				}
			}else {
				System.out.println(u.getUsername()+" has no resolved requests\n");
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
	public void resolve(int ersid, String status, int resolver) { // - A Manager can approve/deny pending reimbursement
																	// requests **DONE**

		try (Connection conn = ConnectionUtil.getConnectionFromFile("connection.properties")) {

			log.info("Attempting to resolve\n");

			if (status.equalsIgnoreCase("PENDING")) {

			} else {
				call = "CALL update_reim(?,?,?)";
				ps = conn.prepareStatement(call);
				ps.setInt(1, ersid);
				ps.setString(2, status);
				ps.setInt(3, resolver);
				ps.executeUpdate();
			}
		} catch (PSQLException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public void requests(int userid) { // - A Manager can view reimbursement requests from a single Employee **DONE**
		ArrayList<ERSReimbursement> reimburse = new ArrayList<>();
		ERSType type = null;
		ERSStatus status = null;

		try (Connection conn = ConnectionUtil.getConnectionFromFile("connection.properties")) {
			log.info("Attempting to get pending list\n");

			sql = "select * from reimbursment r " + "inner join ersstatus s on r.reimbursmentid =s.reimbursmentid "
					+ "inner join erstype t on s.reimbursmentid = t.reimbursmentid "
					+ "where s.status ='PENDING' and r.author =?";
			ps = conn.prepareStatement(sql);
			ps.setInt(1, userid);
			rs = ps.executeQuery();

			while (rs.next()) {
				type = new ERSType(rs.getInt(12), rs.getString(13));
				status = new ERSStatus(rs.getInt(9), rs.getString(10));
				reimburse.add(new ERSReimbursement(rs.getInt(1), rs.getInt(2), rs.getString(3), rs.getDouble(4),
						rs.getTimestamp(5), rs.getInt(7), rs.getTimestamp(8), status, type));
			}

			for (ERSReimbursement r : reimburse) {
				System.out.println(r);
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
	public void resolvedrequests() { // - A Manager can view all resolved requests from all employees and see which
										// manager resolved it **PROBABLY DONE**

		ArrayList<User> empl = new ArrayList<>();
		Role role;

		try (Connection conn = ConnectionUtil.getConnectionFromFile("connection.properties")) {

			log.info("Attempting to get all resolved requests\n");

			sql = "select * FROM ersuser e " + "inner join roles r on r.userid =e.userid "
					+ "WHERE r.ersroles ='EMPLOYEE'";
			ps = conn.prepareStatement(sql);
			rs = ps.executeQuery();

			while (rs.next()) {
				role = new Role(rs.getInt(7), rs.getString(8));
				empl.add(new User(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(5),
						rs.getString(6), role));
			}

			for (User u : empl) {
				System.out.println(u);
				resolved(u);
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
	public void pendingrequests() { // - A Manager can view all pending requests from all employees **DONE**

		ArrayList<User> empl = new ArrayList<>();
		Role role;

		try (Connection conn = ConnectionUtil.getConnectionFromFile("connection.properties")) {

			log.info("Attempting to get all resolved requests\n");

			sql = "select * FROM ersuser e " + "inner join roles r on r.userid =e.userid "
					+ "WHERE r.ersroles ='EMPLOYEE'";
			ps = conn.prepareStatement(sql);
			rs = ps.executeQuery();

			while (rs.next()) {
				role = new Role(rs.getInt(7), rs.getString(8));
				empl.add(new User(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(5),
						rs.getString(6), role));
			}

			for (User u : empl) {
				System.out.println(u);
				pending(u);
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
