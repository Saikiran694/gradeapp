package com.ars.model;

import java.util.ArrayList;
import java.util.List;

public class Student {

	String firstName;
	
	String middleName;
	
	String lastName;
	
	String oldUserName;
	
	int studentId;
	
	String dob;
	
	String emailId;
	
	String contactNo;
	
	String status;
	
	int deptId;
	
	String tbTest;
	
	String mmr;
	
	String description;
	
	String healthScr;
	
	String holdType;
	
	String mailed;
	
	String deptName;
	
	String userName;
	
	String password;
	
	String oldPass;
	
	String newPass;
	
	String securityQuestion;
	
	String securityAnswer;
	
	String userType;
	
	
	
	public String getUserType() {
		return userType;
	}

	public void setUserType(String userType) {
		this.userType = userType;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getOldPass() {
		return oldPass;
	}

	public void setOldPass(String oldPass) {
		this.oldPass = oldPass;
	}

	public String getNewPass() {
		return newPass;
	}

	public void setNewPass(String newPass) {
		this.newPass = newPass;
	}

	public String getSecurityQuestion() {
		return securityQuestion;
	}

	public void setSecurityQuestion(String securityQuestion) {
		this.securityQuestion = securityQuestion;
	}

	public String getSecurityAnswer() {
		return securityAnswer;
	}

	public void setSecurityAnswer(String securityAnswer) {
		this.securityAnswer = securityAnswer;
	}

	public String getDeptName() {
		return deptName;
	}

	public void setDeptName(String deptName) {
		this.deptName = deptName;
	}

	public String getMailed() {
		return mailed;
	}

	public void setMailed(String mailed) {
		this.mailed = mailed;
	}

	List<Course> courseList = new ArrayList<Course>();


	
	
	public String getHoldType() {
		return holdType;
	}

	public void setHoldType(String holdType) {
		this.holdType = holdType;
	}

	public List<Course> getCourseList() {
		return courseList;
	}

	public void setCourseList(List<Course> courseList) {
		this.courseList = courseList;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getMiddleName() {
		return middleName;
	}

	public void setMiddleName(String middleName) {
		this.middleName = middleName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public int getStudentId() {
		return studentId;
	}

	public void setStudentId(int studentId) {
		this.studentId = studentId;
	}

	public String getDob() {
		return dob;
	}

	public void setDob(String dob) {
		this.dob = dob;
	}

	public String getEmailId() {
		return emailId;
	}

	public void setEmailId(String emailId) {
		this.emailId = emailId;
	}

	public String getContactNo() {
		return contactNo;
	}

	public void setContactNo(String contactNo) {
		this.contactNo = contactNo;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public int getDeptId() {
		return deptId;
	}

	public void setDeptId(int deptId) {
		this.deptId = deptId;
	}

	public String getTbTest() {
		return tbTest;
	}

	public void setTbTest(String tbTest) {
		this.tbTest = tbTest;
	}

	public String getMmr() {
		return mmr;
	}

	public void setMmr(String mmr) {
		this.mmr = mmr;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getHealthScr() {
		return healthScr;
	}

	public void setHealthScr(String healthScr) {
		this.healthScr = healthScr;
	}

	public String getOldUserName() {
		return oldUserName;
	}

	public void setOldUserName(String oldUserName) {
		this.oldUserName = oldUserName;
	}
	
	
}
