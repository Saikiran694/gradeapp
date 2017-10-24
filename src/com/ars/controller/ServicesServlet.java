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
import com.ars.dao.OtherServicesDao;
import com.ars.model.TaskInfo;
import com.ars.model.Course;
import com.ars.model.Student;

public class ServicesServlet extends HttpServlet{

	private static final long serialVersionUID = 1L;

	public void doPost(HttpServletRequest request, HttpServletResponse response)  
			throws ServletException, IOException {  

		response.setContentType("text/html");  
		PrintWriter out = response.getWriter();  
		String viewType=request.getParameter("viewType");
		HttpSession session = request.getSession(false);
		String finalView="";
		RequestDispatcher rd=null;
		OtherServicesDao servicesDao = new OtherServicesDao();
		
		if(viewType!=null && "serviceshome".equalsIgnoreCase(viewType)) {
		
		String n=request.getParameter("username");  
		String p=request.getParameter("userpass"); 
		
		Student user = LogonDataIMpl.validate(n, p);
		if(user!=null){  
			finalView="contactus.jsp";
			
		}  
		else{  
			out.print("<p style=\"color:red\">Sorry username or password error</p>");  
			finalView="contactus.jsp"; 
		}  

		out.close();  
		}else if(viewType!=null && "dropcoursedetails".equalsIgnoreCase(viewType)) {
			Course course = new Course();
			course.setEnrollId(Integer.valueOf(request.getParameter("selectedid")));
			try {
				if(!servicesDao.dropCourseDetails(course)) {
					request.setAttribute("errorMsg", "Failed to drop course. Please try again!");
				}else {
					request.setAttribute("errorMsg", "Course dropped Successfully!");
				}
			}catch(Exception e) {
				request.setAttribute("errorMsg", "Failed to drop course  details. Please try again!");
			}
			
			Student user = (Student) session.getAttribute("user");
			List<Course> list = servicesDao.getEnrolledCourseDetails(user.getStudentId());
			if(list.size()==0) {
				request.setAttribute("errorMsg", "No Courses enrolled to display!");
			}
			request.setAttribute("courseList",list);
						
			finalView="managecourses.jsp";
		}else if(viewType!=null && "search".equalsIgnoreCase(viewType)) {
			Course course = new Course();
			
			
			if(request.getParameter("deptname")!=null && request.getParameter("deptname").trim().length()>0) {
				course.setDeptName(request.getParameter("deptname"));
			}
			
			if(request.getParameter("dtype")!=null && request.getParameter("dtype").trim().length()>0) {
				course.setType(request.getParameter("dtype"));
			}
			
			if(request.getParameter("cname1")!=null && request.getParameter("cname1").trim().length()>0) {
				course.setCourseName(request.getParameter("cname1"));
			}
			if(request.getParameter("ccode1")!=null && request.getParameter("ccode1").trim().length()>0) {
				course.setCourseCode(request.getParameter("ccode1"));
			}
			if(request.getParameter("avail1")!=null && request.getParameter("avail1").trim().length()>0) {
				course.setAvailable(request.getParameter("avail1"));
			}
			
			CoursesDataImpl courseDao = new CoursesDataImpl();
			//User user = (User) session.getAttribute("user");
			List<Course> departmentsList = courseDao.getDepartmentDetails(course);
			
			if(departmentsList.size()==0) {
				request.setAttribute("errorMsg", "No Courses to display!");
			}
			request.setAttribute("departmentsList",departmentsList);
			session.setAttribute("departmentsList",departmentsList);
			
			request.setAttribute("course1", course);
			
				finalView="home.jsp";
		}else if(viewType!=null && ("managecourses".equalsIgnoreCase(viewType))) {
			Student user = (Student) session.getAttribute("user");
			List<Course> list = servicesDao.getEnrolledCourseDetails(user.getStudentId());
			if(list.size()==0) {
				request.setAttribute("errorMsg", "No Courses enrolled to display!");
			}
			request.setAttribute("courseList",list);
	
			finalView="managecourses.jsp";
		}else if(viewType!=null && ("viewassignments".equalsIgnoreCase(viewType))) {
			Student user = (Student) session.getAttribute("user");
			List<TaskInfo> list = servicesDao.getAssignmentDetails(user);
			if(list.size()==0) {
				request.setAttribute("errorMsg", "No Assignments to display!");
			}
			request.setAttribute("assignmentsList",list);
	
			finalView="viewassignments.jsp";
		}else if(viewType!=null && ("viewgrades".equalsIgnoreCase(viewType))) {
			Student user = (Student) session.getAttribute("user");
			List<TaskInfo> list = servicesDao.getGradeDetails(user);
			if(list.size()==0) {
				request.setAttribute("errorMsg", "No Courses enrolled to display!");
			}
			request.setAttribute("assignmentsList",list);
	
			finalView="viewgrades.jsp";
		}else if(viewType!=null && ("home".equalsIgnoreCase(viewType))) {
			Course course = new Course();
			CoursesDataImpl courseDao = new CoursesDataImpl();
			List<Course> departmentsList = courseDao.getDepartmentDetails(course);
			
			if(departmentsList.size()==0) {
				request.setAttribute("errorMsg", "No Courses to display!");
			}
			request.setAttribute("departmentsList",departmentsList);
			session.setAttribute("departmentsList",departmentsList);
			finalView="home.jsp";
		}else if(viewType!=null && "enrollcourse".equalsIgnoreCase(viewType)) {
			
			Course course = new Course();
			Student user = (Student) session.getAttribute("user");
			int courseId =Integer.valueOf(request.getParameter("selectedcourse"));
			
			course.setStudentId(user.getStudentId());
			course.setCourseId(courseId);
					
					
			if(!servicesDao.insertEnrollmentDetails(course)) {
				request.setAttribute("errorMsg", "Failed to enroll course. Please try again later!");
			}else {
				request.setAttribute("errorMsg", "Course Enrolled Successfully!");
			}
			CoursesDataImpl courseDao = new CoursesDataImpl();
			List<Course> departmentsList = courseDao.getDepartmentDetails(course);
			
			if(departmentsList.size()==0) {
				request.setAttribute("errorMsg", "No Courses to display!");
			}
			request.setAttribute("departmentsList",departmentsList);
			session.setAttribute("departmentsList",departmentsList);
			
			
			finalView="home.jsp";
		}else if(viewType!=null && "services".equalsIgnoreCase(viewType)) {
			finalView="services.jsp";
		}

			
		 rd=request.getRequestDispatcher(finalView);  
			rd.forward(request,response);
	}  
}  