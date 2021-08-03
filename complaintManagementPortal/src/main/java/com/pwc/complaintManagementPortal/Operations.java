package com.pwc.complaintManagementPortal;

import java.io.IOException;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.pwc.complaintManagementPortal.DBWork.DB;
import com.pwc.complaintManagementPortal.bean.ComplaintBean;
import com.pwc.complaintManagementPortal.bean.UserBean;

@WebServlet("/Operations")
public class Operations extends HttpServlet {
	private static final long serialVersionUID = 1L;

	DB db = new DB();

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doValidate(request, response);
	}

	protected void doValidate(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		String username = request.getParameter("username");
		String password = request.getParameter("password");

		if (request.getSession(false) != null && request.getSession(false).getAttribute("username") != null) {
			HttpSession session = request.getSession();
			username = session.getAttribute("username").toString();
			password = session.getAttribute("password").toString();
		}

		UserBean user = new UserBean();
		user.setUsername(username);
		user.setPassword(password);

		boolean status = db.validate(user, request);

		if (status) {
			request.setAttribute("username", username);
			List<ComplaintBean> complaintList = db.getUserComplaints(user, request);

			request.setAttribute("complaintList", complaintList);

			RequestDispatcher rd = request.getRequestDispatcher("portal.jsp");

			rd.forward(request, response);
			return;
		} else {
			RequestDispatcher rd = request.getRequestDispatcher("login.jsp");
			rd.forward(request, response);
			return;
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		String firstName = request.getParameter("first_name");
		String lastName = request.getParameter("last_name");
		String username = request.getParameter("username");
		String password = request.getParameter("password");

		if (request.getParameter("olduser") != null) {
			doValidate(request, response);

			HttpSession session = request.getSession(true);
			session.setAttribute("username", username);
			session.setAttribute("password", password);
			return;
		}

		if (request.getParameter("newuser") != null) {

			UserBean newUser = new UserBean();
			newUser.setFirstName(firstName);
			newUser.setLastName(lastName);
			newUser.setPassword(password);
			newUser.setUsername(username);

			int status = db.addUser(newUser, request);

			if (status == 0) {
				request.setAttribute("username", username);
				RequestDispatcher rd = request.getRequestDispatcher("signup.jsp");

				rd.forward(request, response);
			}

			request.setAttribute("new", firstName);
			RequestDispatcher rd = request.getRequestDispatcher("login.jsp");
			rd.forward(request, response);
			return;
		}
		if (request.getParameter("submitComp") != null) {

			db.updateComplaint(request.getParameter("result"), request.getParameter("comid"), request);
			doValidate(request, response);
			
		}

		request.setAttribute("new", "try again");
		RequestDispatcher rd = request.getRequestDispatcher("login.jsp");
		rd.forward(request, response);
	}

}
