package com.revature.util;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Timestamp;
import java.util.List;

import javax.servlet.ServletException;
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

public class EmplRequestHelper { //Applied log, exceptions
 
	private static Logger log = Logger.getLogger(EmplRequestHelper.class);
	private static ObjectMapper om = new ObjectMapper().configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);
	public static UserServicesImpl userserv = new UserServicesImpl();
	public static ReimbursementServicesImpl reimserv = new ReimbursementServicesImpl();

	public static void processReim(HttpServletRequest req, HttpServletResponse res) throws IOException, ServletException { //SUBMIT REIM
		Timestamp timestamp = new Timestamp(System.currentTimeMillis());
		res.setContentType("application/json");
		PrintWriter ps = res.getWriter();

		try {
			HttpSession session = req.getSession(false);
			if (session != null) {
				String username = (String) session.getAttribute("username");
				User u = LoginService.authority(username);
				if(u!=null && u.getRole().getRoleid()==1) {
					double amount = Double.parseDouble(req.getParameter("amount"));
					String description = req.getParameter("description");
					String t = req.getParameter("type");
					
					Status status = reimserv.statusHQL(1);
					Type type = reimserv.typeHQL(t);
					Reimbursement reim = new Reimbursement(u, amount, description, timestamp, status, type);

					reim = reimserv.submitHQL(reim);
					if(reim!=null) {
						log.info("Submitted form");
						res.setStatus(200);
						System.out.println(reim);
						ps.println(om.writeValueAsString(reim));
					}else {
						res.setStatus(HttpServletResponse.SC_NOT_IMPLEMENTED);
					}
				}else {
					res.setStatus(HttpServletResponse.SC_NOT_FOUND);
					ps.write(new ObjectMapper().writeValueAsString("Does not exist."));
				}
			}else {
				log.warn("Not logged in\n");
				res.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
				ps.write(new ObjectMapper().writeValueAsString("The requested action is not permitted."));
				req.getRequestDispatcher("index.html").forward(req, res);			
			}
		} catch (NullPointerException e) {	
			log.warn(e);
			res.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
			ps.write(new ObjectMapper().writeValueAsString("The requested action is not permitted."));
			//req.getRequestDispatcher("index.html").forward(req, res);
		}
	}

	public static void processResolved(HttpServletRequest req, HttpServletResponse res) throws IOException, ServletException { //Empl gets his resolved reim

		res.setContentType("application/json");
		PrintWriter ps = res.getWriter();

		try {
			HttpSession session = req.getSession(false);
			if (session != null) {
				String username = (String) session.getAttribute("username");
				User u = LoginService.authority(username);
				if(u!=null && u.getRole().getRoleid()==1) {
					List<Reimbursement> reim = reimserv.resolvedHQL(u);
					if (reim != null) {
						log.info("Got resolved reims");
						res.setStatus(200);
						for (Reimbursement r : reim) {
							System.out.println(r);
							ps.println(om.writeValueAsString(r));
						}
					} else {
						ps.write(new ObjectMapper().writeValueAsString("No resolved reims\n"));
					}
				}else {
					res.setStatus(HttpServletResponse.SC_NOT_FOUND);
					ps.write(new ObjectMapper().writeValueAsString("Does not exist."));
				}
			}else {
			log.warn("Not logged in\n");
			res.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
			ps.write(new ObjectMapper().writeValueAsString("The requested action is not permitted."));
			req.getRequestDispatcher("index.html").forward(req, res);			
			}
		} catch (NullPointerException e) {	
			log.warn(e);
			res.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
			ps.write(new ObjectMapper().writeValueAsString("The requested action is not permitted."));
			//req.getRequestDispatcher("index.html").forward(req, res);
		}
	}

	public static void processPending(HttpServletRequest req, HttpServletResponse res) throws IOException, ServletException { //Empl gets his pending reim

		res.setContentType("application/json");
		PrintWriter ps = res.getWriter();

		try {
			HttpSession session = req.getSession(false);
			if (session != null) {
				String username = (String) session.getAttribute("username");
				User u = LoginService.authority(username);
				if(u!=null && u.getRole().getRoleid()==1) {
					List<Reimbursement> reim = reimserv.pendingHQL(u);
					if (reim != null) {
						log.info("Got pending reims");
						res.setStatus(200);
						for (Reimbursement r : reim) {
							System.out.println(r);
							ps.println(om.writeValueAsString(r));
						}
					} else {
						ps.write(new ObjectMapper().writeValueAsString("No pending reims\n"));
					}
				}else {
					res.setStatus(HttpServletResponse.SC_NOT_FOUND);
					ps.write(new ObjectMapper().writeValueAsString("Does not exist."));
				}
			}else {
			log.warn("Not logged in\n");
			res.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
			ps.write(new ObjectMapper().writeValueAsString("The requested action is not permitted."));
			req.getRequestDispatcher("index.html").forward(req, res);			
			}
		} catch (NullPointerException e) {	
			log.warn(e);
			res.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
			ps.write(new ObjectMapper().writeValueAsString("The requested action is not permitted."));
			//req.getRequestDispatcher("index.html").forward(req, res);
		}
	}
}
