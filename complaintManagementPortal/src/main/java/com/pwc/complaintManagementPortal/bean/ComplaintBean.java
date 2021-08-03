package com.pwc.complaintManagementPortal.bean;

public class ComplaintBean {

	private int id;
	private String username, complaint, status, result;

	@Override
	public String toString() {
		return "ComplaintBean [id=" + id + ", username=" + username + ", complaint=" + complaint + ", status=" + status
				+ ", result=" + result + "]";
	}

	public ComplaintBean(int id, String username, String complaint, String status, String result) {
		this.id = id;
		this.username = username;
		this.complaint = complaint;
		this.status = status;
		this.result = result;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getComplaint() {
		return complaint;
	}

	public void setComplaint(String complaint) {
		this.complaint = complaint;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getResult() {
		return result;
	}

	public void setResult(String result) {
		this.result = result;
	}

}
