package com.ars.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.ars.dao.TicketsDao;
import com.ars.model.Tasks;
import com.ars.model.Student;

public class IssuesServlet extends HttpServlet{

	private static final long serialVersionUID = 1L;

	public void doPost(HttpServletRequest request, HttpServletResponse response)  
			throws ServletException, IOException {  

		response.setContentType("text/html");  
		PrintWriter out = response.getWriter();  
		String viewType=request.getParameter("viewType");
		HttpSession session = request.getSession(false);
		String finalView="";
		RequestDispatcher rd=null;
		TicketsDao enquiryDao = new TicketsDao();
		if(viewType!=null && "enquirysubmit".equalsIgnoreCase(viewType)) {
			Tasks enquiry = new Tasks();
			Student user =(Student) session.getAttribute("user");
			if(user!=null) {
				enquiry.setName(user.getUserName());
				finalView="services.jsp";
			}else {
				enquiry.setName(request.getParameter("custname"));
				finalView="contactus.jsp";
			}
			
			enquiry.setEmailId(request.getParameter("emailid"));
			enquiry.setMessage(request.getParameter("message"));
			enquiry.setStatus("open");
			enquiry.setServiceType(request.getParameter("servicetype"));
			
			
			
			
			try {
				if(!enquiryDao.insertEnquiryDetails(enquiry)) {
					request.setAttribute("errorMsg", "Failed to post enquiry. Please try again!");
				}else {
					request.setAttribute("errorMsg", "Enquiry posted successfully. Please wait for 1-2 business days to recieve response!");
				}
				
			}catch(Exception e) {
				request.setAttribute("errorMsg", "Failed to post enquiry. Please try again!");
			}
			
		}else if(viewType!=null && ("enquiryhome".equalsIgnoreCase(viewType))) {
			
			String searchString =request.getParameter("servicetype");
			Tasks enquiry = new Tasks();
			if(searchString!=null && !searchString.isEmpty()) {
				enquiry.setServiceType(searchString.trim());
			}
			List<Tasks> enquiryList = enquiryDao.getEnquiryDetails(enquiry);
			
			if(enquiryList.size()==0) {
				request.setAttribute("errorMsg", "No enquiries to display!");
			}
			request.setAttribute("enquiryList",enquiryList);
			session.setAttribute("enquiryList",enquiryList);
			
				finalView="viewenquiries.jsp";			
		}else if(viewType!=null && ("editenquirydetails".equalsIgnoreCase(viewType))) {
			List<Tasks> list = (List<Tasks>) session.getAttribute("enquiryList");
			int id =Integer.valueOf(request.getParameter("selectedenquiry"));
			for(Tasks item : list) {
				if(item.getId()==id) {
					request.setAttribute("enquiry", item);
					request.setAttribute("mode", "edit");
					break;
				}
			}			
			finalView="updateenquiries.jsp";
		}else if(viewType!=null && "updateenquiry".equalsIgnoreCase(viewType)) {
			Tasks enquiry = new Tasks();
			if(request.getParameter("id")!=null && request.getParameter("id").trim().length()>0){
			enquiry.setId(Integer.valueOf(request.getParameter("id")));
			}
			enquiry.setName(request.getParameter("custname"));
			enquiry.setEmailId(request.getParameter("emailid"));
			enquiry.setMessage(request.getParameter("message"));
			enquiry.setStatus(request.getParameter("status"));
			enquiry.setServiceType(request.getParameter("servicetype"));
			enquiry.setResponse(request.getParameter("response"));
			
			try {
				if(!enquiryDao.updateEnquiryDetails(enquiry)) {
					request.setAttribute("errorMsg", "Failed to update enquiry details. Please try again!");
				}else {
					request.setAttribute("errorMsg", "Enquiry details updated successfully!");
				}
				
			}catch(Exception e) {
				request.setAttribute("errorMsg", "Failed to update enquiry details. Please try again!");
			}
			finalView="updateenquiries.jsp";
		}
		
		
		 rd=request.getRequestDispatcher(finalView);  
			rd.forward(request,response);
	}  
}  