package com.ars.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.ars.model.Student;
import com.ars.model.MainUser;

public class LogonDataIMpl {
	public static Student validate(String name, String pass) {		
		boolean status = false;
		Connection conn = null;
		PreparedStatement pst = null;
		ResultSet rs = null;
		Student user=null;
		String url = "jdbc:mysql://localhost:3306/";
		String dbName = "courseapp";
		String driver = "com.mysql.jdbc.Driver";
		String userName = "root";
		String password = "admin";
		try {
			Class.forName(driver).newInstance();
			conn = DriverManager
					.getConnection(url + dbName, userName, password);

			pst = conn
					.prepareStatement("select * from login_details a , user_details b where a.user_name=? and a.password=? and a.student_id = b.student_id");
			pst.setString(1, name);
			pst.setString(2, pass);

			rs = pst.executeQuery();
			//status = rs.next();
			
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
				user.setStatus(rs.getString("status"));
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
		return user;
	}
}