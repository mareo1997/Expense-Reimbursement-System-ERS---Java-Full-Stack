package com.revature.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Timestamp;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.revature.model.ReimTemplate;
import com.revature.model.Reimbursement;
import com.revature.model.Status;
import com.revature.model.Type;
import com.revature.model.User;
import com.revature.services.LoginService;
import com.revature.services.ReimbursementServicesImpl;
import com.revature.services.UserServicesImpl;

public class RequestHelper {

	private static Logger log = Logger.getLogger(RequestHelper.class);
	private static ObjectMapper om = new ObjectMapper().configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);
	public static UserServicesImpl userserv = new UserServicesImpl();
	public static ReimbursementServicesImpl reimserv = new ReimbursementServicesImpl();

	public static void processLogin(HttpServletRequest req, HttpServletResponse res) throws IOException {
		
		String username = req.getParameter("username");
		String password = req.getParameter("password");
		System.out.println("username and password "+username+password);
		
		User login = LoginService.confirm(username, password);
		System.out.println("login "+login);
		
		res.setContentType("application/json");
		PrintWriter ps = res.getWriter();
		
		if(login!=null) {
			HttpSession session = req.getSession();
			session.setAttribute("username", username);
			//res.setStatus(200);
			System.out.println(login);
			ps.println(om.writeValueAsString(login));		
//			res.sendRedirect("http://localhost:8080/project-1/Ehome.html");
		} else {
			res.setStatus(HttpServletResponse.SC_BAD_REQUEST);
			ps.write(new ObjectMapper().writeValueAsString("Invalid credentials"));
		}

		
		/*BufferedReader reader = req.getReader();
		System.out.println("reader " + reader);
		StringBuilder s = new StringBuilder();

		// we are just transferring our Reader data to our StringBuilder, line by line
		String line = null;

		while ((line = reader.readLine()) != null) {
			System.out.println("line " + line);
			s.append(line);
			System.out.println("s " + s);
			line = reader.readLine();
		}
		String body = s.toString();
		System.out.println("body " + body);

		LoginTemplate attempt = om.readValue(body, LoginTemplate.class);
		String username = attempt.getUsername();
		String password = attempt.getPassword();

		log.info("Username attempted " + username);
		User login = LoginService.confirm(username, password);
		if (login != null) {
			HttpSession session = req.getSession();
			session.setAttribute("username", username);

			PrintWriter pw = res.getWriter();
			res.setContentType("application/json");
			res.setStatus(200);

			pw.println(om.writeValueAsString(login));

			log.info(username + " has successfully logged in.\n");

		} else {
			res.setContentType("application/json");
			res.setStatus(204); // Connection was successful but no user found
		}*/
	}

	public static void processLogout(HttpServletRequest req, HttpServletResponse res) throws IOException {
		HttpSession session = req.getSession(false);

		if (session != null) {
			String username = (String) session.getAttribute("username");
			log.info(username + " logged out\n");
			session.invalidate();
		}
		res.setStatus(200);
	}

	public static void processReim(HttpServletRequest req, HttpServletResponse res) throws IOException {  //Worked but gave back 500
		Timestamp timestamp = new Timestamp(System.currentTimeMillis());
		BufferedReader reader = req.getReader();
		System.out.println("reader " + reader);
		StringBuilder sb = new StringBuilder();

		// we are just transferring our Reader data to our StringBuilder, line by line
		String line = null;

		while ((line = reader.readLine()) != null) {
			System.out.println("line " + line);
			sb.append(line);
			System.out.println("s " + sb);
			line = reader.readLine();
		}
		String body = sb.toString();
		System.out.println("body " + body);

		ReimTemplate attempt = om.readValue(body, ReimTemplate.class);
		System.out.println(attempt);
		int userid = attempt.getUserid();
		double amount = attempt.getAmount();
		String description = attempt.getDescription();
		String type = attempt.getType();

		Reimbursement reim = new Reimbursement();
		Status s = new Status("PENDING");
		Type t = new Type(type);
		//reimserv.submit(reim, s, t);

		PrintWriter pw = res.getWriter();
		res.setContentType("application/json");
		res.setStatus(200);

		pw.println(om.writeValueAsString(reim));
	}

	public static void processPending(HttpServletRequest req, HttpServletResponse res) throws IOException {
		BufferedReader reader = req.getReader();
		System.out.println("reader " + reader);
		StringBuilder s = new StringBuilder();

		// we are just transferring our Reader data to our StringBuilder, line by line
		String line = null;

		while ((line = reader.readLine()) != null) {
			System.out.println("line " + line);
			s.append(line);
			System.out.println("s " + s);
			line = reader.readLine();
		}
		String body = s.toString();
		System.out.println("body " + body);
		
		User u = om.readValue(body, User.class);

		//User u = new User(2, "marwil", "william", "Marcia", "Williamson", "mother@gmail.com");
		List<Reimbursement> reimburse = reimserv.pending(u);
		PrintWriter pw = res.getWriter();

		if (reimburse.size() > 0) {
			res.setContentType("application/json");
			res.setStatus(200);

			for (Reimbursement r : reimburse) {
				pw.write(om.writeValueAsString(r));
			}

		}else {
			res.setStatus(204); // Connection was successful but no user found
			pw.write(om.writeValueAsString(u.getUsername()+" has no pending requests"));
		}

	}

	public static void processResolved(HttpServletRequest req, HttpServletResponse res) throws IOException {
		BufferedReader reader = req.getReader();
		System.out.println("reader " + reader);
		StringBuilder s = new StringBuilder();

		// we are just transferring our Reader data to our StringBuilder, line by line
		String line = null;

		while ((line = reader.readLine()) != null) {
			System.out.println("line " + line);
			s.append(line);
			System.out.println("s " + s);
			line = reader.readLine();
		}
		String body = s.toString();
		System.out.println("body " + body);
		
		User u = om.readValue(body, User.class);

		//User u = new User(2, "marwil", "william", "Marcia", "Williamson", "mother@gmail.com");
		List<Reimbursement> reimburse = reimserv.resolved(u);
		PrintWriter pw = res.getWriter();

		if (reimburse.size() > 0) {
			res.setContentType("application/json");
			res.setStatus(200);

			for (Reimbursement r : reimburse) {
				pw.write(om.writeValueAsString(r));
			}

		}else {
			res.setStatus(204); // Connection was successful but no user found
			pw.write(om.writeValueAsString(u.getUsername()+" has no pending requests"));
		}		
	}

	public static void processProfile(HttpServletRequest req, HttpServletResponse res) throws IOException {
		
		int userid = Integer.parseInt(req.getParameter("userid"));
		
		User u = userserv.profile(userid);
				
		res.setContentType("application/json");
		PrintWriter ps = res.getWriter();
		
		if(u!=null) {
			res.setStatus(200);
			System.out.println(u);
			ps.println(om.writeValueAsString(u));		
//			res.sendRedirect("http://localhost:8080/project-1/Ehome.html");
		} else {
			res.setStatus(HttpServletResponse.SC_BAD_REQUEST);
			ps.write(new ObjectMapper().writeValueAsString("Invalid credentials"));
		}
		
		/*BufferedReader reader = req.getReader();
		System.out.println("reader " + reader);
		StringBuilder s = new StringBuilder();

		// we are just transferring our Reader data to our StringBuilder, line by line
		String line = null;

		while ((line = reader.readLine()) != null) {
			System.out.println("line " + line);
			s.append(line);
			System.out.println("s " + s);
			line = reader.readLine();
		}
		String body = s.toString();
		System.out.println("body " + body);		
		
		User u = om.readValue(body, User.class);

		u = userserv.profile(u.getUserid());
		
		if(u!=null) {
			PrintWriter pw = res.getWriter();
			res.setContentType("application/json");
			res.setStatus(200);
			System.out.println(u);
			pw.println(om.writeValueAsString(u));
		}*/
	}

}
