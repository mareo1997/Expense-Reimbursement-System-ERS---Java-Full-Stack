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
		final String URI = req.getRequestURI();// .replace("/project-1/", "");
		System.out.println("begin " + URI);

		switch (URI) {
		case "/project-1/allempls": // DONE
			System.out.println("case " + URI);
			RequestHelper.processEmpls(req, res);
			break;
		case "/project-1/pending": // DONE
			System.out.println("case " + URI);
			RequestHelper.processPending(req, res);
			break;
		case "/project-1/requests":
			System.out.println("case " + URI);
			RequestHelper.procesRequests(req, res);
			break;
		case "/project-1/pendingrequests": // DONE
			System.out.println("case " + URI);
			RequestHelper.processPendingRequests(req, res);
			break;
		case "/project-1/resolvedrequests": // DONE
			System.out.println("case " + URI);
			RequestHelper.processResolvedRequests(req, res);
			break;
		case "/project-1/resolved": // DONE
			System.out.println("case " + URI);
			RequestHelper.processResolved(req, res);
			break;
		case "/project-1/profile": // DONE
			System.out.println("case " + URI);
			RequestHelper.processProfile(req, res);
			break;
		case "/project-1/type": // DONE
			System.out.println("case " + URI);
			RequestHelper.processType(req, res);
			break;
		case "/project-1/status": // DONE
			System.out.println("case " + URI);
			RequestHelper.processStatus(req, res);
			break;
		case "/project-1/reimid": // DONE
			System.out.println("case " + URI);
			RequestHelper.processReimID(req, res);
			break;
		default:
			doPost(req, res);// req.getRequestDispatcher("login.html").forward(req, res);
			break;
		}
	}
//Have the post and get methods in form and then servlet can retrive
//ajax

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		final String URI = req.getRequestURI();// .replace("/project-1/", "");
		System.out.println("begin " + URI);

		switch (URI) {
		case "/project-1/login": // DONE
			System.out.println("case " + URI);
			RequestHelper.processLogin(req, res);
			break;
		case "/project-1/logout": // DONE
			System.out.println("case " + URI);
			RequestHelper.processLogout(req, res);
			break;
		case "/project-1/reim": // DONE
			System.out.println("case " + URI);
			RequestHelper.processReim(req, res);
			break;
		case "/project-1/resolve": // DONE
			System.out.println("case " + URI);
			RequestHelper.processResolve(req, res);
			break;
		case "/project-1/update":
			System.out.println("case " + URI);
			RequestHelper.processUpdate(req, res);
			break;
		default:
			System.out.println(URI);
			break;
		}

	}
}
