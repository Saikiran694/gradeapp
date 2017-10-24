package com.ars.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.ars.model.TaskInfo;
import com.ars.model.Course;
import com.ars.model.Student;
import com.ars.model.MainUser;

public class OtherServicesDao {
	boolean status = false;
	Connection conn = null;
	PreparedStatement pst = null;
	Statement stmt = null;
	ResultSet rs = null;
	MainUser user=null;
	String url = "jdbc:mysql://localhost:3306/";
	String dbName = "courseapp";
	String driver = "com.mysql.jdbc.Driver";
	String userName = "root";
	String password = "admin";
	
	public List getUserDetails(String name, String pass) {		
		try {
			Class.forName(driver).newInstance();
			conn = DriverManager
					.getConnection(url + dbName, userName, password);

			pst = conn
					.prepareStatement("select * from login_details where user_id=? and password=?");
			pst.setString(1, name);
			pst.setString(2, pass);

			rs = pst.executeQuery();
			//status = rs.next();
			
			while (rs.next()) {
				user = new MainUser();
	            user.setUserName(rs.getString("user_id"));
	            user.setUserType(rs.getString("user_type"));
	        }

		} catch (Exception e) {
			System.out.println(e);
		} finally {
			if (conn != null) {
				try {
					conn.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
			if (pst != null) {
				try {
					pst.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
			if (rs != null) {
				try {
					rs.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
		return null;
	}
	
	
	
	public List<Course> getEnrolledCourseDetails(int sid) {
		List<Course> list = new ArrayList<Course>();
		
		try {
			
			createConnection();

			String getEnrollmentDetailsQuery = "select * from course_details a, enrollment_details b where b.student_id="+sid+" and a.course_id = b.course_id"; 
			
			pst = conn
					.prepareStatement(getEnrollmentDetailsQuery);
			
			rs = pst.executeQuery();
			
			while (rs.next()) {
				Course course = new Course();
				course.setEnrollId(rs.getInt("enroll_id"));
				course.setCourseName(rs.getString("course_name"));
				course.setCourseCode(rs.getString("course_code"));
				course.setType(rs.getString("type"));
				course.setDeptName(rs.getString("dept_name"));
				course.setAvailable(rs.getString("available"));
				course.setProfessor(rs.getString("professor"));
				course.setProfId(rs.getInt("prof_id"));
				list.add(course);
	        }

		} catch (Exception e) {
			System.out.println(e);
		} finally {
			
			closeConnection(conn,pst,rs);
		}

		
		return list;
	}

	
	
	
	
	private void closeConnection(Connection con, PreparedStatement pst, ResultSet rs) {

		if (conn != null) {
			try {
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		if (pst != null) {
			try {
				pst.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		if (rs != null) {
			try {
				rs.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	
	}

	

	public boolean insertEnrollmentDetails(Course course) {
		try {
			
			conn = createConnection();
			stmt = conn.createStatement();
			String insertQuery = "insert into enrollment_details values (null,'" + course.getStudentId()+
					"','" +course.getCourseId()+
					"','" +course.getStatus()+
					"')";
			int count = stmt.executeUpdate(insertQuery);
			if(count>0) {
				return true;
			}
	
		} catch (Exception e) {
			System.out.println(e);
		} finally {
			try {
				stmt.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
			closeConnection(conn,pst,rs);
		}
		return false;
	}

	public List<TaskInfo> getAssignmentDetails(Student user) {	
		List<TaskInfo> assignmentsList = new ArrayList<TaskInfo>();
		try {
			
			Class.forName(driver).newInstance();
			conn = DriverManager
					.getConnection(url + dbName, userName, password);
			String getAssignmentsQuery = "select * from updates_details a,enrollment_details b ,course_details c where student_id="+user.getStudentId()+" and a.course_id = b.course_id and b.course_id = c.course_id order by deadline desc"; 
		
			pst = conn
					.prepareStatement(getAssignmentsQuery);
			
			rs = pst.executeQuery();
			
			while (rs.next()) {
				TaskInfo assignment = new TaskInfo();
				assignment.setUserId(rs.getInt("user_id"));
				assignment.setUpdateId(rs.getInt("update_id"));
				assignment.setCourseId(rs.getInt("course_id"));
				assignment.setDeadLine(rs.getString("deadline"));
				assignment.setTitle(rs.getString("title"));
				assignment.setDescription(rs.getString("description"));
				assignment.setMarks(rs.getInt("marks"));
				assignment.setCourseName(rs.getString("course_name"));
				assignment.setProfessorName(rs.getString("professor"));
				assignmentsList.add(assignment);
	        }

		} catch (Exception e) {
			System.out.println(e);
		} finally {
			if (conn != null) {
				try {
					conn.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
			if (pst != null) {
				try {
					pst.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
			if (rs != null) {
				try {
					rs.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
		return assignmentsList;
	}


	public List<TaskInfo> getGradeDetails(Student user) {	
		List<TaskInfo> assignmentsList = new ArrayList<TaskInfo>();
		try {
			
			Class.forName(driver).newInstance();
			conn = DriverManager
					.getConnection(url + dbName, userName, password);
			String getAssignmentsQuery = "select * from grade_details a, updates_details b ,course_details c where a.student_id="+user.getStudentId()+" and a.update_id = b.update_id and a.course_id= c.course_id"; 
		
			pst = conn
					.prepareStatement(getAssignmentsQuery);
			
			rs = pst.executeQuery();
			List<Integer> updateIds = new ArrayList<Integer>();
			while (rs.next()) {
				TaskInfo assignment = new TaskInfo();
				assignment.setUserId(rs.getInt("user_id"));
				assignment.setUpdateId(rs.getInt("update_id"));
				assignment.setCourseId(rs.getInt("course_id"));
				assignment.setDeadLine(rs.getString("deadline"));
				assignment.setTitle(rs.getString("title"));
				assignment.setDescription(rs.getString("description"));
				assignment.setMarks(rs.getInt("marks"));
				assignment.setCourseName(rs.getString("course_name"));
				assignment.setProfessorName(rs.getString("professor"));
				assignment.setStudentMarks(rs.getInt("points"));
				if(updateIds.contains(assignment.getUpdateId())){
					
				}else{
				assignmentsList.add(assignment);
				updateIds.add(assignment.getUpdateId());
				}
	        }

		} catch (Exception e) {
			System.out.println(e);
		} finally {
			if (conn != null) {
				try {
					conn.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
			if (pst != null) {
				try {
					pst.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
			if (rs != null) {
				try {
					rs.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
		return assignmentsList;
	}

	
	
	public boolean dropCourseDetails(Course course) {
		try {
				
				conn = createConnection();
				stmt = conn.createStatement();
				String deleteQuery = "delete from enrollment_details where enroll_id = '" +course.getEnrollId()+"'";
			
				int count = stmt.executeUpdate(deleteQuery);
					if(count>0) {
						return true;
					}
		
			} catch (Exception e) {
				System.out.println(e);
			} finally {
				try {
					stmt.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
				closeConnection(conn,pst,rs);
			}
			return false;
		}
	
	
	
	private Connection createConnection() {
		try{
		Class.forName(driver).newInstance();
		conn = DriverManager
				.getConnection(url + dbName, userName, password);
		
		}catch(Exception e) {
			System.out.println(e);
		}
		return conn;
	}

}