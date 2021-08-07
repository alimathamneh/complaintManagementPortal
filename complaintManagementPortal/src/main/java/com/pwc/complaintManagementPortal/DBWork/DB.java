package com.pwc.complaintManagementPortal.DBWork;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.sql.DataSource;
import com.pwc.complaintManagementPortal.bean.ComplaintBean;
import com.pwc.complaintManagementPortal.bean.UserBean;
import com.pwc.complaintManagementPortal.utils.Utils;

public class DB {

	public boolean validate(UserBean bean, HttpServletRequest req) {

		boolean status = false;

		DataSource complaintManagementPortal = (DataSource) req.getServletContext()
				.getAttribute("complaintManagementPortalDataSource");

		try (Connection con = complaintManagementPortal.getConnection();
				PreparedStatement ps = con.prepareStatement("select * from users_login_info where username=?")) {

			ps.setString(1, bean.getUsername());

			try (ResultSet rs = ps.executeQuery()) {
				if (rs.next()) {

					status = Utils.validatePassword(bean.getPassword(), rs.getString("password"));

					if (status) {
						HttpSession session = req.getSession(false);
						session.setAttribute("fname", (rs.getString("fname")));
						session.setAttribute("lname", (rs.getString("lname")));
						session.setAttribute("admin", (rs.getString("admin")));
						req.setAttribute("admin", rs.getString("admin"));
					}

				}
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

		return status;

	}

	public int addUser(UserBean bean, ServletRequest req) {

		int status = 0;

		DataSource complaintManagementPortal = (DataSource) req.getServletContext()
				.getAttribute("complaintManagementPortalDataSource");

		try (Connection con = complaintManagementPortal.getConnection();
				PreparedStatement ps = con.prepareStatement("INSERT INTO users_login_info VALUES (?,?,?,?,0);")) {

			ps.setString(1, bean.getFirstName());
			ps.setString(2, bean.getLastName());
			ps.setString(3, bean.getUsername());
			ps.setString(4, bean.getPassword());

			status = ps.executeUpdate();

		} catch (Exception e) {
			e.printStackTrace();
		}

		return status;

	}

	public List<ComplaintBean> getUserComplaints(UserBean bean, HttpServletRequest req) {

		List<ComplaintBean> complaintList = new ArrayList<ComplaintBean>();

		DataSource complaintManagementPortal = (DataSource) req.getServletContext()
				.getAttribute("complaintManagementPortalDataSource");

		HttpSession session = req.getSession(false);
		String admin = (String) session.getAttribute("admin");

		String sql = "SELECT * FROM user_complaint WHERE username=?;";
		if ("1".equals(admin)) {
			sql = "SELECT * FROM user_complaint;";
		}

		try (Connection con = complaintManagementPortal.getConnection();
				PreparedStatement ps = con.prepareStatement(sql)) {

			if (!"1".equals(admin)) {
				ps.setString(1, bean.getUsername());
			}

			try (ResultSet rs = ps.executeQuery()) {
				while (rs.next()) {

					ComplaintBean complaint = new ComplaintBean(rs.getInt("id"), rs.getString("username"),
							rs.getString("complaint"), rs.getString("status"), rs.getString("result"));

					complaintList.add(complaint);

				}
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

		return complaintList;

	}

	public List<ComplaintBean> getUserListComplaints(int page, int recordsPerPage, HttpServletRequest req) {

		List<ComplaintBean> complaintObjList = new ArrayList<ComplaintBean>();

		DataSource complaintManagementPortal = (DataSource) req.getServletContext()
				.getAttribute("complaintManagementPortalDataSource");

		String sql = "SELECT * FROM user_complaint limit ?,?;";

		try (Connection con = complaintManagementPortal.getConnection();
				PreparedStatement ps = con.prepareStatement(sql)) {

			ps.setInt(1, page);
			ps.setInt(2, recordsPerPage);

			try (ResultSet rs = ps.executeQuery()) {
				while (rs.next()) {

					ComplaintBean complaint = new ComplaintBean(rs.getInt("id"), rs.getString("username"),
							rs.getString("complaint"), rs.getString("status"), rs.getString("result"));

					complaintObjList.add(complaint);

				}
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

		return complaintObjList;

	}

	public int getComplaintsCount(HttpServletRequest req) {

		DataSource complaintManagementPortal = (DataSource) req.getServletContext()
				.getAttribute("complaintManagementPortalDataSource");

		String sql = "SELECT count(*) as count FROM user_complaint;";

		int count = 0;

		try (Connection con = complaintManagementPortal.getConnection();
				PreparedStatement ps = con.prepareStatement(sql)) {

			try (ResultSet rs = ps.executeQuery()) {
				while (rs.next()) {
					count = rs.getInt("count");
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

		return count;

	}

	public int updateComplaint(String result, String complaintId, ServletRequest req) {

		int status = 0;

		DataSource complaintManagementPortal = (DataSource) req.getServletContext()
				.getAttribute("complaintManagementPortalDataSource");

		try (Connection con = complaintManagementPortal.getConnection();
				PreparedStatement ps = con
						.prepareStatement("UPDATE user_complaint SET result=?,status='Done' WHERE id=?")) {

			ps.setString(1, result);
			ps.setString(2, complaintId);

			status = ps.executeUpdate();

		} catch (Exception e) {
			e.printStackTrace();
		}

		return status;

	}

	public int addUserComplaint(String result, String username, ServletRequest req) {

		int status = 0;

		DataSource complaintManagementPortal = (DataSource) req.getServletContext()
				.getAttribute("complaintManagementPortalDataSource");

		try (Connection con = complaintManagementPortal.getConnection();
				PreparedStatement ps = con.prepareStatement(
						"INSERT INTO user_complaint (username, complaint, status, result) VALUES (?, ?, 'Received', 'Pending');")) {

			ps.setString(1, username);
			ps.setString(2, result);

			status = ps.executeUpdate();

		} catch (Exception e) {
			e.printStackTrace();
		}

		return status;

	}
}
