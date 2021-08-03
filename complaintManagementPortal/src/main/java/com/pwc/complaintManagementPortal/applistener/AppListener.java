package com.pwc.complaintManagementPortal.applistener;

import javax.annotation.Resource;
import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.sql.DataSource;

public class AppListener implements ServletContextListener {

	@Resource(name = "jdbc/complaintmanagementportal")
	DataSource complaintManagementPortalDataSource;

	public void contextDestroyed(ServletContextEvent context) {

		context.getServletContext().removeAttribute("complaintManagementPortalDataSource");
	}

	public void contextInitialized(ServletContextEvent sce) {
		try {
			ServletContext context = sce.getServletContext();
			context.setAttribute("complaintManagementPortalDataSource", complaintManagementPortalDataSource);

		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}