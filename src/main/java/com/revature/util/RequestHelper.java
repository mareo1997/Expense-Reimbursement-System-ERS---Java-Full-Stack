package com.revature.util;

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
		System.out.println("username and password " + username + password);

		User login = LoginService.confirm(username, password);
		System.out.println("login " + login);

		res.setContentType("application/json");
		PrintWriter ps = res.getWriter();

		if (login != null) {
			HttpSession session = req.getSession();
			session.setAttribute("username", username);
			res.setStatus(200);
			System.out.println(login);
			ps.println(om.writeValueAsString(login));
			res.sendRedirect("http://localhost:8080/project-1/Ehome.html");
		} else {
			res.setStatus(HttpServletResponse.SC_BAD_REQUEST);
			ps.write(new ObjectMapper().writeValueAsString("Invalid credentials"));
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
		res.setContentType("application/json");

		int userid = Integer.parseInt(req.getParameter("userid"));
		double amount = Double.parseDouble(req.getParameter("amount"));
		String description = req.getParameter("description");
		String t = req.getParameter("type");

		User u = userserv.profileHQL(userid);
		Status status = reimserv.statusHQL(1);
		Type type = reimserv.typeHQL(t);

		Reimbursement reim = new Reimbursement(u, amount, description, timestamp, status, type);

		reimserv.submitHQL(reim);
	}

	public static void processResolve(HttpServletRequest req, HttpServletResponse res) throws IOException {
		Timestamp resolved = new Timestamp(System.currentTimeMillis());
		res.setContentType("application/json");
		PrintWriter ps = res.getWriter();

		int userid = Integer.parseInt(req.getParameter("userid"));
		int reimid = Integer.parseInt(req.getParameter("reimid"));
		int statusid = Integer.parseInt(req.getParameter("status"));

		User u = userserv.profileHQL(userid);
		Status status = reimserv.statusHQL(statusid);
		Reimbursement reim = reimserv.findReimHQL(reimid);

		reim = reimserv.resolveHQL(reim, u, status, resolved);

		if (reim != null) {
			res.setStatus(200);
			System.out.println(reim);
			ps.println(om.writeValueAsString(reim));
		} else {
			res.setStatus(HttpServletResponse.SC_BAD_REQUEST);
			ps.write(new ObjectMapper().writeValueAsString("Invalid credentials"));
		}
	}

	public static void processPending(HttpServletRequest req, HttpServletResponse res) throws IOException {

		res.setContentType("application/json");
		PrintWriter ps = res.getWriter();

		int userid = Integer.parseInt(req.getParameter("userid"));

		User u = userserv.profileHQL(userid);

		List<Reimbursement> reim = reimserv.pendingHQL(u);

		if (reim != null) {
			res.setStatus(200);
			for (Reimbursement r : reim) {
				System.out.println(r);
				ps.println(om.writeValueAsString(r));
			}
		} else {
			res.setStatus(HttpServletResponse.SC_BAD_REQUEST);
			ps.write(new ObjectMapper().writeValueAsString("Invalid credentials"));
		}
	}

	public static void processResolved(HttpServletRequest req, HttpServletResponse res) throws IOException {

		res.setContentType("application/json");
		PrintWriter ps = res.getWriter();

		int userid = Integer.parseInt(req.getParameter("userid"));

		User u = userserv.profileHQL(userid);

		List<Reimbursement> reim = reimserv.resolvedHQL(u);

		if (reim != null) {
			res.setStatus(200);
			for (Reimbursement r : reim) {
				System.out.println(r);
				ps.println(om.writeValueAsString(r));
			}
		} else {
			res.setStatus(HttpServletResponse.SC_BAD_REQUEST);
			ps.write(new ObjectMapper().writeValueAsString("Invalid credentials"));
		}
	}

	public static void processProfile(HttpServletRequest req, HttpServletResponse res) throws IOException {
		res.setContentType("application/json");
		PrintWriter ps = res.getWriter();

		int userid = Integer.parseInt(req.getParameter("userid"));

		User u = userserv.profileHQL(userid);

		if (u != null) {
			res.setStatus(200);
			System.out.println(u);
			ps.println(om.writeValueAsString(u));
		} else {
			res.setStatus(HttpServletResponse.SC_BAD_REQUEST);
			ps.write(new ObjectMapper().writeValueAsString("Invalid credentials"));
		}
	}

	public static void processEmpls(HttpServletRequest req, HttpServletResponse res) throws IOException {
		res.setContentType("application/json");
		PrintWriter ps = res.getWriter();

		List<User> u = userserv.allEmplHQL();

		if (u != null) {
			res.setStatus(200);
			for (User user : u) {
				// if(user.getRole().getRole().equalsIgnoreCase("employee")) {
				System.out.println(user);
				ps.println(om.writeValueAsString(user));
				// }
			}
		} else {
			res.setStatus(HttpServletResponse.SC_BAD_REQUEST);
			ps.write(new ObjectMapper().writeValueAsString("Invalid credentials"));
		}
	}

	public static void processType(HttpServletRequest req, HttpServletResponse res) throws IOException {
		res.setContentType("application/json");
		PrintWriter ps = res.getWriter();

		String t = req.getParameter("type");

		Type type = reimserv.typeHQL(t);

		res.setStatus(200);
		System.out.println(type);
		ps.println(om.writeValueAsString(type));

	}

	public static void processStatus(HttpServletRequest req, HttpServletResponse res) throws IOException {
		res.setContentType("application/json");
		PrintWriter ps = res.getWriter();

		int statusid = Integer.parseInt(req.getParameter("status"));

		Status status = reimserv.statusHQL(statusid);

		res.setStatus(200);
		System.out.println(status);
		ps.println(om.writeValueAsString(status));

	}

	public static void processReimID(HttpServletRequest req, HttpServletResponse res) throws IOException {
		res.setContentType("application/json");
		PrintWriter ps = res.getWriter();

		int Reimbursementid = Integer.parseInt(req.getParameter("reimid"));

		Reimbursement reim = reimserv.findReimHQL(Reimbursementid);

		if (reim != null) {
			res.setStatus(200);
			System.out.println(reim);
			ps.println(om.writeValueAsString(reim));
		} else {
			res.setStatus(HttpServletResponse.SC_BAD_REQUEST);
			ps.write(new ObjectMapper().writeValueAsString("Invalid credentials"));
		}
	}

	public static void procesRequests(HttpServletRequest req, HttpServletResponse res) {
		// TODO Auto-generated method stub

	}

	public static void processPendingRequests(HttpServletRequest req, HttpServletResponse res) {
		// TODO Auto-generated method stub

	}

	public static void processResolvedRequests(HttpServletRequest req, HttpServletResponse res) {
		// TODO Auto-generated method stub

	}

	public static void processUpdate(HttpServletRequest req, HttpServletResponse res) {
		// TODO Auto-generated method stub

	}

}
