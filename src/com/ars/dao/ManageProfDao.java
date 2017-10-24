package com.ars.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import com.ars.model.Student;
import com.ars.model.MainUser;

public class ManageProfDao {
	Connection conn = null;
	PreparedStatement pst = null;
	Statement stmt = null;
	ResultSet rs = null;
	
	String url = "jdbc:mysql://localhost:3306/";
	String dbName = "courseapp";
	String driver = "com.mysql.jdbc.Driver";
	String userName = "root";
	String password = "admin";
	public Student getProfileDetails(String name) {		
		
		Student user=null;
		try {
			
			conn = createConnection();
			pst = conn
					.prepareStatement("select * from login_details a , user_details b where a.user_name=? and a.student_id = b.student_id");
			pst.setString(1, name);

			rs = pst.executeQuery();
			
			while (rs.next()) {
				user = new Student();
	            user.setUserName(rs.getString("user_name"));
	            user.setPassword(rs.getString("password"));
	            user.setSecurityQuestion(rs.getString("security_question"));  
				user.setSecurityAnswer(rs.getString("security_answer")); 
				user.setUserType(rs.getString("user_type"));
				user.setFirstName(rs.getString("first_name"));
				user.setMiddleName(rs.getString("middle_name"));
				user.setLastName(rs.getString("last_name"));
				user.setStudentId(rs.getInt("student_id"));
				user.setEmailId(rs.getString("email_id"));
				user.setContactNo(rs.getString("contact_no"));
				user.setDeptName(rs.getString("dept_name"));
	        }

		} catch (Exception e) {
			System.out.println(e);
		} finally {
			
			closeConnection(conn,pst,rs);
		}
		return user;
	}
	
	public boolean insertProfileDetails(Student user) {
try {
			
			conn = createConnection();
			stmt = conn.createStatement();
			String insertQuery = "insert into user_details values (null,'" +user.getFirstName()+
					"','" +user.getMiddleName()+
					"','" +user.getLastName()+
					"','" +user.getEmailId()+
					"','" +user.getContactNo()+
					"','" +user.getStatus()+
					"','" +user.getDeptName()+
					"')";
		
			int count = stmt.executeUpdate(insertQuery);
			if(count>0) {
				pst = conn
						.prepareStatement("select * from user_details where contact_no=? and first_name=?");
				pst.setString(1, user.getContactNo());
				pst.setString(2, user.getFirstName());

				rs = pst.executeQuery();
				int customerId = 0;
				if (rs.next()) {
					customerId = rs.getInt("student_id");
		        }
				String loginQuery="insert into login_details values('" +user.getUserName()+
						"','" +user.getNewPass()+
						"',current_date,'" +user.getSecurityQuestion()+
						"','" +user.getSecurityAnswer()+
						"','" +user.getUserType()+
						"','" +customerId+
						"')";
				
				count = stmt.executeUpdate(loginQuery);
				if(count>0) {
				return true;
				} 
			}
	
		} catch (Exception e) {
			System.out.println(e);
			String deleteQuery="delete from user_details where contact_no='"+ user.getContactNo() +"' and first_name='" +user.getFirstName()+
					"'";
			try {
				stmt.executeUpdate(deleteQuery);
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
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
	
	public void deletProfileDetails(MainUser user) {
		
	}
	
	public boolean updateProfileDetails(Student user) {
try {
			
			conn = createConnection();
			stmt = conn.createStatement();
			String updateQuery = "update user_details set first_name = '" +user.getFirstName()+
					"',middle_name='" +user.getMiddleName()+
					"',last_name= '" +user.getLastName()+
					"',contact_no= '" +user.getContactNo()+
					"',email_id='" +user.getEmailId()+
					"',dept_name = '" +user.getDeptName()+
					"' where student_id='" + user.getStudentId()+"'";
		
			int count = stmt.executeUpdate(updateQuery);
			if(count>0) {
				
				String loginQuery="update login_details set" +
						" password= '" +user.getNewPass()+
						"', security_question = '" +user.getSecurityQuestion()+
						"',security_answer = '" +user.getSecurityAnswer()+
						"' where user_name = '" + user.getOldUserName()+ "'";
				
				count = stmt.executeUpdate(loginQuery);
				if(count>0) {
				return true;
				} 
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
	
	public boolean forgotPassword(MainUser user) {
		
		try {
			
			conn = createConnection();
			pst = conn
					.prepareStatement("select * from login_details where user_name=? and security_question=? and security_answer=?");
			pst.setString(1, user.getUserName());
			pst.setString(2, user.getSecurityQuestion());
			pst.setString(3, user.getSecurityAnswer());

			rs = pst.executeQuery();
			
			return rs.next();
		} catch (Exception e) {
			System.out.println(e);
		} finally {
		
			closeConnection(conn,pst,rs);
		}
		return false;
	}
	
	public boolean updatePassword(MainUser user) {
		try {
			
			conn = createConnection();
			stmt = conn.createStatement();

			int count = stmt.executeUpdate("update login_details set password='"+user.getNewPass()+"' where user_name='"+user.getUserName()+"'");
			if(count>0) {
				return true;
			}
	
			return rs.next();
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