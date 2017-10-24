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

import com.ars.dao.CoursesDataImpl;
import com.ars.dao.LogonDataIMpl;
import com.ars.model.Course;
import com.ars.model.Student;

public class LogonServlet extends HttpServlet{

	private static final long serialVersionUID = 1L;

	public void doPost(HttpServletRequest request, HttpServletResponse response)  
			throws ServletException, IOException {  

		response.setContentType("text/html");  
		PrintWriter out = response.getWriter();  
		String viewType=request.getParameter("viewType");
		HttpSession session = request.getSession(false);
		if(viewType!=null && "login".equalsIgnoreCase(viewType)) {
		String n=request.getParameter("username");  
		String p=request.getParameter("userpass"); 
		
		
		if(session!=null)
		request.setAttribute("name", n);
		CoursesDataImpl courseDao = new CoursesDataImpl();

		Student user = LogonDataIMpl.validate(n, p);
		if(user!=null){  
			String userType = user.getUserType();
			session.setAttribute("username", n);
			session.setAttribute("user", user);
			
			// set courses list in generic way
			List<Course> genericCourseList = courseDao.getCourseDetails(user,true);
			if(genericCourseList.size()>0) {
			request.setAttribute("genericCourseList",genericCourseList);
			session.setAttribute("genericCourseList",genericCourseList);
			}
			
			
			if(!userType.isEmpty() && ("student".equals(userType))) {
				
				String searchString =request.getParameter("deptname");
				Course course = new Course();
				if(searchString!=null && !searchString.isEmpty()) {
					course.setDeptName(searchString);
				}
				List<Course> departmentsList = courseDao.getDepartmentDetails(course);
				
				if(departmentsList.size()==0) {
					request.setAttribute("errorMsg", "No Courses to display!");
				}
				request.setAttribute("departmentsList",departmentsList);
				session.setAttribute("departmentsList",departmentsList);
				
			RequestDispatcher rd=request.getRequestDispatcher("home.jsp");  
			rd.forward(request,response);  
			}else if(!userType.isEmpty()) {
				
				List<Course> teachList = courseDao.getCourseDetails(user,false);
				if(teachList.size()>0) {
				request.setAttribute("teachList",teachList);
				session.setAttribute("teachList",teachList);
				}
				RequestDispatcher rd=request.getRequestDispatcher("adddepartments.jsp");  
				rd.forward(request,response);  
				}
		}  
		else{  
			request.setAttribute("errorMsg", "Invalid username or password!");  
			RequestDispatcher rd=request.getRequestDispatcher("login.jsp");  
			rd.forward(request,response);  
		}  

		out.close();  
		}else if(viewType!=null && "logout".equalsIgnoreCase(viewType)) {
			if (session != null){
				session.invalidate();
				RequestDispatcher rd=request.getRequestDispatcher("login.jsp");  
				rd.forward(request,response);
			}
		}
	}  
}  