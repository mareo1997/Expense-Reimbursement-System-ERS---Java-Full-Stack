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

public class ManagerRequestHelper {

	private static Logger log = Logger.getLogger(ManagerRequestHelper.class);
	private static ObjectMapper om = new ObjectMapper().configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);
	public static UserServicesImpl userserv = new UserServicesImpl();
	public static ReimbursementServicesImpl reimserv = new ReimbursementServicesImpl();

	public static void processEmpls(HttpServletRequest req, HttpServletResponse res)
			throws IOException, ServletException { // See all empls

		res.setContentType("application/json");
		PrintWriter ps = res.getWriter();

		try {
			HttpSession session = req.getSession(false);
			if (session != null) {
				String username = (String) session.getAttribute("username");
				User u = LoginService.authority(username);
				if (u != null && u.getRole().getRoleid() == 2) {
					List<User> empl = userserv.allEmplHQL();
					if (empl != null) {
						res.setStatus(200);
						for (User user : empl) {
							log.info("Got employees");
							System.out.println(user);
							ps.println(om.writeValueAsString(user));
						}
					} else {
						ps.println(om.writeValueAsString("No employeed"));
					}
				} else {
					res.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
					ps.write(new ObjectMapper().writeValueAsString("The requested action is not permitted."));
					req.getRequestDispatcher("Mhome.html").forward(req, res);
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
			// req.getRequestDispatcher("index.html").forward(req, res);
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

	public static void processResolve(HttpServletRequest req, HttpServletResponse res)
			throws IOException, ServletException { /* Manager resolves a reim */
		Timestamp resolved = new Timestamp(System.currentTimeMillis());
		res.setContentType("application/json");
		PrintWriter ps = res.getWriter();

		try {
			HttpSession session = req.getSession(false);
			if (session != null) {
				String username = (String) session.getAttribute("username");
				User u = LoginService.authority(username);
				if (u != null && u.getRole().getRoleid() == 2) {
					int reimid = Integer.parseInt(req.getParameter("reimid"));
					int statusid = Integer.parseInt(req.getParameter("status"));
					Status status = reimserv.statusHQL(statusid);
					Reimbursement reim = reimserv.findReimHQL(reimid);
					reim = reimserv.resolveHQL(reim, u, status, resolved);
					if (reim != null) {
						log.info("Resolved request");
						res.setStatus(200);
						System.out.println(reim);
						ps.println(om.writeValueAsString(reim));
					} else {
						log.warn("Could not resolve request");
						res.setStatus(HttpServletResponse.SC_BAD_REQUEST);
						ps.write(new ObjectMapper().writeValueAsString("Invalid credentials"));
					}
				} else {
					res.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
					ps.write(new ObjectMapper().writeValueAsString("The requested action is not permitted."));
					req.getRequestDispatcher("Mhome.html").forward(req, res);
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
			// req.getRequestDispatcher("index.html").forward(req, res);
		}
	}

	public static void processRequests(HttpServletRequest req, HttpServletResponse res)
			throws IOException, ServletException { /* Manager views an empl requests */

		res.setContentType("application/json");
		PrintWriter ps = res.getWriter();

		try {
			HttpSession session = req.getSession(false);
			if (session != null) {
				String username = (String) session.getAttribute("username");
				User u = LoginService.authority(username);
				if (u != null && u.getRole().getRoleid() == 2) {
					String username1 = req.getParameter("username");
					User empl = LoginService.authority(username1);
					if (empl != null) {
						List<Reimbursement> reim = reimserv.requestsHQL(empl);
						if (reim != null) {
							log.info("Got requests\n");
							res.setStatus(200);
							for (Reimbursement r : reim) {
								System.out.println(r);
								ps.println(om.writeValueAsString(r));
							}
						} else {
							log.warn("No requests found\n");
							res.setStatus(HttpServletResponse.SC_BAD_REQUEST);
							ps.write(new ObjectMapper().writeValueAsString("Invalid credentials"));
						}
					} else {
						log.warn("Could not find\n");
						res.setStatus(HttpServletResponse.SC_NOT_FOUND);
						ps.write(new ObjectMapper().writeValueAsString("Does not exist."));
					}
				} else {
					log.warn("Not permitted\n");
					res.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
					ps.write(new ObjectMapper().writeValueAsString("The requested action is not permitted."));
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
			// req.getRequestDispatcher("index.html").forward(req, res);
		}
	}

	public static void processPendingRequests(HttpServletRequest req, HttpServletResponse res)
			throws IOException, ServletException { /* Manager views all empls pendings */
		res.setContentType("application/json");
		PrintWriter ps = res.getWriter();

		try {
			HttpSession session = req.getSession(false);
			if (session != null) {
				String username = (String) session.getAttribute("username");
				User u = LoginService.authority(username);
				if (u != null && u.getRole().getRoleid() == 2) {
					List<Reimbursement> reim = reimserv.pendingrequestsHQL();
					if (reim != null) {
						log.info("Got all pending requests\n");
						res.setStatus(200);
						for (Reimbursement r : reim) {
							System.out.println(r);
							ps.println(om.writeValueAsString(r));
						}
					} else {
						log.warn("No requests found\n");
						res.setStatus(HttpServletResponse.SC_BAD_REQUEST);
						ps.write(new ObjectMapper().writeValueAsString("Invalid credentials"));
					}
				} else {
					log.warn("Not permitted\n");
					res.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
					ps.write(new ObjectMapper().writeValueAsString("The requested action is not permitted."));
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
			// req.getRequestDispatcher("index.html").forward(req, res);
		}
	}

	public static void processResolvedRequests(HttpServletRequest req, HttpServletResponse res)
			throws IOException, ServletException {/* Manager views all empls resolved requets */
		res.setContentType("application/json");
		PrintWriter ps = res.getWriter();

		try {
			HttpSession session = req.getSession(false);
			if (session != null) {
				String username = (String) session.getAttribute("username");
				User u = LoginService.authority(username);
				if (u != null && u.getRole().getRoleid() == 2) {
					List<Reimbursement> reim = reimserv.resolvedrequestsHQL();
					if (reim != null) {
						log.info("Got all resolved requests\n");
						res.setStatus(200);
						for (Reimbursement r : reim) {
							System.out.println(r);
							ps.println(om.writeValueAsString(r));
						}
					} else {
						log.warn("No resolved found\n");
						res.setStatus(HttpServletResponse.SC_BAD_REQUEST);
						ps.write(new ObjectMapper().writeValueAsString("Invalid credentials"));
					}
				} else {
					log.warn("Not permitted\n");
					res.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
					ps.write(new ObjectMapper().writeValueAsString("The requested action is not permitted."));
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
			// req.getRequestDispatcher("index.html").forward(req, res);
		}
	}
}
