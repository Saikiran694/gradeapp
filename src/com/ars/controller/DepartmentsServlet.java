package com.ars.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.ars.dao.CoursesDataImpl;
import com.ars.model.TaskInfo;
import com.ars.model.Course;
import com.ars.model.Student;

public class DepartmentsServlet extends HttpServlet{

	private static final long serialVersionUID = 1L;

	public void doPost(HttpServletRequest request, HttpServletResponse response)  
			throws ServletException, IOException {  

		response.setContentType("text/html");  
		PrintWriter out = response.getWriter();  
		String viewType=request.getParameter("viewType");
		HttpSession session = request.getSession(false);
		String finalView="";
		RequestDispatcher rd=null;
		CoursesDataImpl courseDao = new CoursesDataImpl();
		Student user1 =(Student) session.getAttribute("user");
	
		if(viewType!=null && ("postgrades".equalsIgnoreCase(viewType)  || "editdepartment".equalsIgnoreCase(viewType) || "deletedepartment".equalsIgnoreCase(viewType))) {
			
			String searchString =request.getParameter("deptname");
			Course course1 = new Course();
			if(searchString!=null && !searchString.isEmpty()) {
				course1.setCourseName(searchString);
			}
			
			if(user1.getUserType().equalsIgnoreCase("staff")) {
				List<TaskInfo> assignmentsList1 = courseDao.getAssignmentDetails(user1);
				if(assignmentsList1.size()==0) {
					request.setAttribute("errorMsg", "No Assignments to display!");
				}
				request.setAttribute("assignmentsList",assignmentsList1);
				session.setAttribute("assignmentsList",assignmentsList1);
			}else {
			List<Course> departmentsList1 = courseDao.getDepartmentDetails(course1);
			
			if(departmentsList1.size()==0) {
				request.setAttribute("errorMsg", "No Courses to display!");
			}
			request.setAttribute("departmentsList",departmentsList1);
			session.setAttribute("departmentsList",departmentsList1);
			}
			if("editdepartment".equalsIgnoreCase(viewType)) {
			finalView="modifydepartments.jsp";
			}else {
				finalView="deletedepartments.jsp";
			}
			
			if("postgrades".equalsIgnoreCase(viewType)) {
				finalView="postgrades.jsp";
				}
			
		}else if(viewType!=null && ("viewstudents".equalsIgnoreCase(viewType))) {
			List<Student> studentsList1 = new ArrayList<Student>();
			if("staff".equalsIgnoreCase(user1.getUserType())) {
				Course c = new Course();
				c.setCourseId(Integer.valueOf(request.getParameter("selectedstudent")));
				studentsList1 = courseDao.getStudentDetails(c);
				request.setAttribute("courseId",Integer.valueOf(request.getParameter("selectedstudent")));
				request.setAttribute("updateId",Integer.valueOf(request.getParameter("selectedupdate")));
			}else {
			studentsList1 = courseDao.getStudentDetails();
			}
			if(studentsList1.size()==0) {
				request.setAttribute("errorMsg", "No Students to display!");
			}
			request.setAttribute("studentsList",studentsList1);
			session.setAttribute("studentsList",studentsList1);
			
				finalView="viewstudents.jsp";
			
		}else if(viewType!=null && ("viewstudentdetails".equalsIgnoreCase(viewType))) {
			if(user1.getUserType().equalsIgnoreCase("admin")) {
			List<Student> list = (List<Student>) session.getAttribute("studentsList");
			List<Student> newList1 = new ArrayList<Student>();
			String type = request.getParameter("type");
			for(Student item : list) {
				if(item.getStatus().equalsIgnoreCase(type)) {
					newList1.add(item);
				}
			}
			request.setAttribute("studentsList",newList1);
			}else {
				

				List<TaskInfo> gradeList = new ArrayList<TaskInfo>();
				int courseId = Integer.valueOf(request.getParameter("selectedstudent"));
				int updateId = Integer.valueOf(request.getParameter("selectedupdate"));
					
					
					Enumeration<String> parameterNames = request.getParameterNames();

					while (parameterNames.hasMoreElements()) {
						String paramName = parameterNames.nextElement();
						if(paramName.contains("marks_")) {
							String[] parNameArr = paramName.split("_");
							TaskInfo c = new TaskInfo();
							c.setCourseId(courseId);
							c.setUpdateId(updateId);
							c.setStudentId(Integer.valueOf(parNameArr[1]));
							c.setUserId(user1.getStudentId());
							c.setMarks(Integer.valueOf(request.getParameter(paramName)));
							gradeList.add(c);
						}
					}
					try {
					if(!courseDao.insertMarksDetails(gradeList)) {
						request.setAttribute("errorMsg", "Failed to post marks details. Please try again!");
					}else {
						request.setAttribute("errorMsg", "Marks Posted Successfully!");
					}
				}catch(Exception e) {
					request.setAttribute("errorMsg", "Failed to insert course details. Please try again!");
				}
				
				finalView="adddepartments.jsp";
				
				
			}
			finalView="viewstudents.jsp";
		}else if(viewType!=null && ("editdepartmentdetails".equalsIgnoreCase(viewType))) {
			
			if(user1.getUserType().equalsIgnoreCase("staff")) {
			
				List<TaskInfo> list = (List<TaskInfo>) session.getAttribute("assignmentsList");
				int courseId1 =Integer.valueOf(request.getParameter("selecteddepartment"));
				for(TaskInfo item : list) {
					if(item.getUpdateId()==courseId1) {
						request.setAttribute("assignment", item);
						if("editdepartmentdetails".equalsIgnoreCase(viewType)) {
						request.setAttribute("mode", "edit");
						}else {
							request.setAttribute("mode", "delete");
						}
						break;
					}
				}
				
			}else {
			List<Course> list = (List<Course>) session.getAttribute("departmentsList");
			int courseId =Integer.valueOf(request.getParameter("selecteddepartment"));
			for(Course item : list) {
				if(item.getCourseId()==courseId) {
					request.setAttribute("course", item);
					if("editdepartmentdetails".equalsIgnoreCase(viewType)) {
					request.setAttribute("mode", "edit");
					}else {
						request.setAttribute("mode", "delete");
					}
					break;
				}
			}
			}	
			finalView="adddepartments.jsp";
		}else if(viewType!=null && ("editstudent".equalsIgnoreCase(viewType))) {
			List<Student> list = (List<Student>) session.getAttribute("studentsList");
			int studentId =Integer.valueOf(request.getParameter("selectedstudent"));
			for(Student item : list) {
				if(item.getStudentId()==studentId) {
					request.setAttribute("student", item);
					if("editstudent".equalsIgnoreCase(viewType)) {
					request.setAttribute("mode", "edit");
					}else {
						request.setAttribute("mode", "delete");
					}
					break;
				}
			}			
		finalView="editstudentdetails.jsp";
		}else if(viewType!=null && "updatedepartment".equalsIgnoreCase(viewType)) {
			Course c = new Course();
			c.setCourseId(Integer.valueOf(request.getParameter("cid")));
			c.setCourseName(request.getParameter("cname1"));
			c.setCourseCode(request.getParameter("ccode1"));
			c.setAvailable(request.getParameter("avail1"));
			c.setCapacity(Integer.valueOf(request.getParameter("capacity1")));
			c.setProfessor(request.getParameter("prof1"));
			c.setStatus("1");
			c.setType(request.getParameter("dtype"));
			c.setDeptName(request.getParameter("deptname"));
			
			try {
				if(!courseDao.updateDepartmentDetails(c)) {
					request.setAttribute("errorMsg", "Failed to update course details. Please try again!");
				}else {
					request.setAttribute("errorMsg", "course Details Updated Successfully!");
				}
			}catch(Exception e) {
				request.setAttribute("errorMsg", "Failed to update course details. Please try again!");
			}
			finalView="adddepartments.jsp";
		}else if(viewType!=null && "assignmentupdate".equalsIgnoreCase(viewType)) {
			TaskInfo c = new TaskInfo();
			
			c.setCourseId(Integer.valueOf(request.getParameter("course")));
			c.setTitle(request.getParameter("title"));
			c.setMarks(Integer.valueOf(request.getParameter("marks")));
			c.setDeadLine(request.getParameter("deadline"));
			c.setDescription(request.getParameter("message"));
			c.setStatus("1");
			c.setUpdateId(Integer.valueOf(request.getParameter("uid")));
		
			
			try {
				if(!courseDao.updateAssignmentDetails(c)) {
					request.setAttribute("errorMsg", "Failed to update Assignment details. Please try again!");
				}else {
					request.setAttribute("errorMsg", "Assignment Details Updated Successfully!");
				}
			}catch(Exception e) {
				request.setAttribute("errorMsg", "Failed to update course details. Please try again!");
			}
			finalView="adddepartments.jsp";
		}else if(viewType!=null && "updatestudent".equalsIgnoreCase(viewType)) {
			
			Student user = new Student();
			int studentId =Integer.valueOf(request.getParameter("selectedstudent"));
			user.setStudentId(studentId);
			user.setFirstName(request.getParameter("firstname"));
			user.setMiddleName(request.getParameter("middlename"));
			user.setLastName(request.getParameter("lastname"));
			user.setContactNo(request.getParameter("mobileno"));
			user.setEmailId(request.getParameter("emailid"));
			user.setStatus(request.getParameter("status"));
		

			try {
				if(!courseDao.updateStudentDetails(user)) {
					request.setAttribute("errorMsg", "Failed to update student details. Please try again!");
				}else {
					request.setAttribute("errorMsg", "Student Details Updated Successfully!");
				}
			}catch(Exception e) {
				request.setAttribute("errorMsg", "Failed to update student details. Please try again!");
			}
			List<Student> studentsList = courseDao.getStudentDetails();
			
			if(studentsList.size()==0) {
				request.setAttribute("errorMsg", "No Students to display!");
			}
			request.setAttribute("studentsList",studentsList);
			session.setAttribute("studentsList",studentsList);
			
				finalView="viewstudents.jsp";
		}else if(viewType!=null && "deleteDepartmentdetails".equalsIgnoreCase(viewType)) {
			
			if("staff".equalsIgnoreCase(user1.getUserType())) {
				TaskInfo c = new TaskInfo();
				c.setUpdateId(Integer.valueOf(request.getParameter("selecteddepartment")));
						
				try {
					if(!courseDao.deleteAssignmentDetails(c)) {
						request.setAttribute("errorMsg", "Failed to delete Assignment details. Please try again!");
					}else {
						request.setAttribute("errorMsg", "Assignment Details Deleted Successfully!");
					}
				}catch(Exception e) {
					request.setAttribute("errorMsg", "Failed to  delete Assignment  details. Please try again!");
				}
				
			}else {
			Course c = new Course();
			c.setCourseId(Integer.valueOf(request.getParameter("selecteddepartment")));
					
			try {
				if(!courseDao.deleteCourseDetails(c)) {
					request.setAttribute("errorMsg", "Failed to delete Course details. Please try again!");
				}else {
					request.setAttribute("errorMsg", "Course Details Deleted Successfully!");
				}
			}catch(Exception e) {
				request.setAttribute("errorMsg", "Failed to  delete course  details. Please try again!");
			}
			}
			finalView="adddepartments.jsp";
		}else if(viewType!=null && "adddepartment".equalsIgnoreCase(viewType)) {
			
			List<Course> courseList = new ArrayList<Course>();
			String deptName = request.getParameter("deptname");
			String deptType = request.getParameter("dtype");
			for(int i=1;i<=1;i++) {
				Course c = new Course();
				if(request.getParameter("cname"+i)!=null && request.getParameter("cname"+i).trim().length()>0) {
					c.setCourseName(request.getParameter("cname"+i));
					c.setCourseCode(request.getParameter("ccode"+i));
					c.setAvailable(request.getParameter("avail"+i));
					c.setCapacity(Integer.valueOf(request.getParameter("capacity"+i)));
					c.setProfessor(request.getParameter("prof"+i));
					c.setStatus("1");
					c.setType(deptType);
					c.setDeptName(deptName);
					
					courseList.add(c);
				}
			}
				try {
				if(!courseDao.insertDepartmentDetails(courseList)) {
					request.setAttribute("errorMsg", "Failed to insert course details. Please try again!");
				}else {
					request.setAttribute("errorMsg", "New Course Details Added Successfully!");
				}
			}catch(Exception e) {
				request.setAttribute("errorMsg", "Failed to insert course details. Please try again!");
			}
			
			finalView="adddepartments.jsp";
		}else if(viewType!=null && "assignmentsubmit".equalsIgnoreCase(viewType)) {
			
			Student user =(Student) session.getAttribute("user");
				TaskInfo c = new TaskInfo();
					c.setCourseId(Integer.valueOf(request.getParameter("course")));
					c.setTitle(request.getParameter("title"));
					c.setMarks(Integer.valueOf(request.getParameter("marks")));
					c.setDeadLine(request.getParameter("deadline"));
					c.setDescription(request.getParameter("message"));
					c.setStatus("1");
					c.setUserId(user.getStudentId());
					

				try {
				if(!courseDao.insertAssignmentDetails(c)) {
					request.setAttribute("errorMsg", "Failed to post Assignment details. Please try again!");
				}else {
					request.setAttribute("errorMsg", "New Assignment Details Added Successfully!");
				}
			}catch(Exception e) {
				request.setAttribute("errorMsg", "Failed to post assignment details. Please try again!");
			}
			
			finalView="adddepartments.jsp";
		}  
		
		
		 rd=request.getRequestDispatcher(finalView);  
			rd.forward(request,response);
	}  
}  