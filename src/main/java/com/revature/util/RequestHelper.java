package com.revature.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Timestamp;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.revature.model.ERSReimbursement;
import com.revature.model.ERSStatus;
import com.revature.model.ERSType;
import com.revature.model.LoginTemplate;
import com.revature.model.ReimTemplate;
import com.revature.model.User;
import com.revature.services.LoginService;
import com.revature.services.ReimbursementServicesImpl;
import com.revature.services.UserServicesImpl;

public class RequestHelper {

	private static Logger log = Logger.getLogger(RequestHelper.class);
	private static ObjectMapper om = new ObjectMapper();
	public static UserServicesImpl userserv = new UserServicesImpl();
	public static ReimbursementServicesImpl reimserv = new ReimbursementServicesImpl();

	public static void processLogin(HttpServletRequest req, HttpServletResponse res) throws IOException {
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
		}
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

	public static void processReim(HttpServletRequest req, HttpServletResponse res) throws IOException {
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

		ERSReimbursement reim = new ERSReimbursement(amount, timestamp, description, userserv.profile(userid));
		ERSStatus s = new ERSStatus("PENDING", reim);
		ERSType t = new ERSType(type, reim);
		reimserv.submit(reim, s, t);

		PrintWriter pw = res.getWriter();
		res.setContentType("application/json");
		res.setStatus(200);

		pw.println(om.writeValueAsString(reim));
	}

}
