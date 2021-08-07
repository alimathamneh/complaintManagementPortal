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
import com.pwc.complaintManagementPortal.utils.Utils;

@WebServlet("/Operations")
public class Operations extends HttpServlet {
	private static final long serialVersionUID = 1L;

	DB db = new DB();

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		if (request.getParameter("numOfRec") != null) {
			HttpSession session = request.getSession(false);
			session.setAttribute("numOfRec", Integer.parseInt(request.getParameter("numOfRec")));
		}
		try {
			doValidate(request, response);
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	protected void doValidate(HttpServletRequest request, HttpServletResponse response) throws Exception {

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

			HttpSession session = request.getSession(false);

			if (session != null && "1".equals(session.getAttribute("admin"))) {

				int page = 1;
				int recordsPerPage = session.getAttribute("numOfRec") == null ? 4
						: Integer.parseInt(session.getAttribute("numOfRec").toString());

				if (request.getParameter("page") != null)
					page = Integer.parseInt(request.getParameter("page"));

				List<ComplaintBean> list = db.getUserListComplaints((page - 1) * recordsPerPage, recordsPerPage,
						request);

				int noOfRecords = db.getComplaintsCount(request);
				int noOfPages = (int) Math.ceil(noOfRecords * 1.0 / recordsPerPage);

				request.setAttribute("employeeList", list);
				request.setAttribute("noOfPages", noOfPages);
				request.setAttribute("currentPage", page);

				RequestDispatcher rd = request.getRequestDispatcher("MyJsp.jsp");

				rd.forward(request, response);
				return;
			}

			RequestDispatcher rd = request.getRequestDispatcher("portal.jsp");

			rd.forward(request, response);
			return;
		} else {

			request.getSession().invalidate();
			request.setAttribute("login_msg", "Invalid Username or Password!");
			RequestDispatcher rd = request.getRequestDispatcher("/login.jsp");
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

			try {
				doValidate(request, response);
			} catch (Exception e) {
				e.printStackTrace();
			}

			HttpSession session = request.getSession(true);
			session.setAttribute("username", username);
			session.setAttribute("password", password);
			return;
		}

		if (request.getParameter("signup") != null) {

			try {
				password = Utils.generateStorngPasswordHash(password);
			} catch (Exception e) {
				e.printStackTrace();
			}

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

			try {
				doValidate(request, response);
			} catch (Exception e) {
				e.printStackTrace();
			}

		}
		if (request.getParameter("userComp") != null) {

			String userN = request.getSession().getAttribute("username").toString();
			int added = db.addUserComplaint(request.getParameter("result"),
					request.getSession().getAttribute("username").toString(), request);

			if (added == 1)
				request.setAttribute("profile_msg", "The Complaint Added Successfully");
			else
				request.setAttribute("profile_msg", "Somthing Want Wrong, Please try Again Later!");

			UserBean user = new UserBean();
			user.setUsername(userN);

			List<ComplaintBean> complaintList = db.getUserComplaints(user, request);
			request.setAttribute("complaintList", complaintList);

			RequestDispatcher rd = request.getRequestDispatcher("portal.jsp");
			rd.forward(request, response);
			return;
		}

		request.setAttribute("new", "try again");
		RequestDispatcher rd = request.getRequestDispatcher("login.jsp");
		rd.forward(request, response);
	}

}
