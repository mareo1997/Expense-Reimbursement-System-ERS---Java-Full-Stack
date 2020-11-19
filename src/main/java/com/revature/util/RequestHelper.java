package com.revature.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.revature.model.LoginTemplate;
import com.revature.services.LoginService;

public class RequestHelper {

	private static Logger log = Logger.getLogger(RequestHelper.class);
	private static ObjectMapper om = new ObjectMapper();

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
		boolean login = LoginService.login(username, password);
		if (login==true) {
			HttpSession session = req.getSession();
			session.setAttribute("username", username);

			PrintWriter pw = res.getWriter();
			res.setContentType("application/json");
			log.info(username + " has successfully logged in.\n");

		} else {
			res.setContentType("application/json");
			res.setStatus(204); // Connection was successful but no user found
		}
	}

}
