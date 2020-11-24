package com.revature.util;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.revature.model.User;
import com.revature.services.LoginService;
import com.revature.services.UserServicesImpl;

public class UserRequestHelper {
	private static Logger log = Logger.getLogger(UserRequestHelper.class);
	private static ObjectMapper om = new ObjectMapper().configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);
	public static UserServicesImpl userserv = new UserServicesImpl();

	public static void processLogin(HttpServletRequest req, HttpServletResponse res) throws IOException, ServletException {
		
		String username = req.getParameter("username");
		String password = req.getParameter("password");//System.out.println("username and password " + username + password);

		User login = LoginService.confirm(username, password);

		res.setContentType("application/json");
		PrintWriter ps = res.getWriter();

		if (login != null) {
			log.info(username+" successfully logged in\n");
			HttpSession session = req.getSession();
			session.setAttribute("username", username);
			res.setStatus(200);
			System.out.println(login);
			ps.println(om.writeValueAsString(login));
			req.getRequestDispatcher("Ehome.html").forward(req, res);//res.sendRedirect("http://localhost:8080/project-1/Ehome.html");
		} else {
			log.warn(username+" could not login");
			res.setStatus(HttpServletResponse.SC_BAD_REQUEST);
			ps.write(new ObjectMapper().writeValueAsString("Invalid credentials"));
			req.getRequestDispatcher("index.html").forward(req, res);
		}
	}

	public static void processLogout(HttpServletRequest req, HttpServletResponse res) throws IOException, ServletException {
		HttpSession session = req.getSession(false);

		if (session != null) {
			String username = (String) session.getAttribute("username");
			log.info(username + " logged out\n");
			session.invalidate();
		}
		req.getRequestDispatcher("index.html").forward(req, res);
		res.setStatus(200);
	}

	public static void processProfile(HttpServletRequest req, HttpServletResponse res) throws IOException, ServletException {
		res.setContentType("application/json");
		PrintWriter ps = res.getWriter();

		try {
			HttpSession session = req.getSession(false);

			if (session != null) {
				String username = (String) session.getAttribute("username");
				User u = LoginService.authority(username);
				u = userserv.profileHQL(u);
				if (u != null) {
					log.info("Got profile\n");
					res.setStatus(200);
					System.out.println(u);
					ps.println(om.writeValueAsString(u));
				} else {
					log.warn("Couldn't get profile\n");
					res.setStatus(HttpServletResponse.SC_NOT_FOUND);
					ps.write(new ObjectMapper().writeValueAsString("Does not exist."));
				}
			} else {
				log.warn("Not logged in\n");
				res.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
				ps.write(new ObjectMapper().writeValueAsString("The requested action is not permitted."));
				req.getRequestDispatcher("index.html").forward(req, res);
			}

		} catch (NullPointerException e) {
			log.warn(e);
			res.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
			ps.write(new ObjectMapper().writeValueAsString("The requested action is not permitted."));
			req.getRequestDispatcher("index.html").forward(req, res);
		}
	}

	public static void processUpdate(HttpServletRequest req, HttpServletResponse res) throws IOException, ServletException {
		res.setContentType("application/json");
		PrintWriter ps = res.getWriter();

		try {
			HttpSession session = req.getSession(false);

			if (session != null) {
				String username = (String) session.getAttribute("username");
				User update = LoginService.authority(username);

				if (update != null) {
					String fname = req.getParameter("firstname");
					String lname = req.getParameter("lastname");
					String email = req.getParameter("email");
					String username1 = req.getParameter("username");
					String password = req.getParameter("password");
					String repassword = req.getParameter("repassword");

					update = userserv.updateHQL(update, fname, lname, email, username1, password, repassword);
					
					if (update != null) {
						log.info("Updated "+update.getUsername()+" profile");
						res.setStatus(200);
						System.out.println(update);
						ps.println(om.writeValueAsString(update));
					} else {
						res.setStatus(HttpServletResponse.SC_NOT_FOUND);
						ps.write(new ObjectMapper().writeValueAsString("Does not exist."));
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
			req.getRequestDispatcher("index.html").forward(req, res);
		}
	}

}