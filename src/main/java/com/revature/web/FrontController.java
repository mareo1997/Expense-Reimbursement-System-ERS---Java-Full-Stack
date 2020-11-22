package com.revature.web;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.revature.util.RequestHelper;

public class FrontController extends HttpServlet {

	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		doPost(req, res);// req.getRequestDispatcher("login.html").forward(req, res);
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		final String URI = req.getRequestURI().replace("/project-1/", "");
		System.out.println("begin " + URI);

		switch (URI) {
		case "login":
			System.out.println("case " + URI);
			RequestHelper.processLogin(req, res);
			break;
		case "logout":
			System.out.println("case " + URI);
			RequestHelper.processLogout(req, res);
			break;
		case "reim":
			System.out.println("case " + URI);
			RequestHelper.processReim(req, res);
			break;
		}

	}
}
