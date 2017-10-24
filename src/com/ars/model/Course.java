package com.ars.model;

public class Course {

	int courseId;
	
	String courseName;
	
	String courseCode;
	
	String description;
	
	String status;
	
	int deptId;
	
	int profId=0;
	
	
	
	
	public int getProfId() {
		return profId;
	}

	public void setProfId(int profId) {
		this.profId = profId;
	}

	String type;
	
	int capacity;
	
	int remaining;
	
	String available;
	
	String deptName;
	
	int studentId;
	
	int enrollId;
	
	

	public int getStudentId() {
		return studentId;
	}

	public void setStudentId(int studentId) {
		this.studentId = studentId;
	}

	public int getEnrollId() {
		return enrollId;
	}

	public void setEnrollId(int enrollId) {
		this.enrollId = enrollId;
	}

	public int getRemaining() {
		return remaining;
	}

	public void setRemaining(int remaining) {
		this.remaining = remaining;
	}

	int totalCourses;
	
	
	String professor;
	
	
	public String getProfessor() {
		return professor;
	}

	public void setProfessor(String professor) {
		this.professor = professor;
	}

	public int getTotalCourses() {
		return totalCourses;
	}

	public void setTotalCourses(int totalCourses) {
		this.totalCourses = totalCourses;
	}

	public String getAvailable() {
		return available;
	}

	public void setAvailable(String available) {
		this.available = available;
	}

	public String getDeptName() {
		return deptName;
	}

	public void setDeptName(String deptName) {
		this.deptName = deptName;
	}

	public int getCourseId() {
		return courseId;
	}

	public void setCourseId(int courseId) {
		this.courseId = courseId;
	}

	public String getCourseName() {
		return courseName;
	}

	public void setCourseName(String courseName) {
		this.courseName = courseName;
	}

	public String getCourseCode() {
		return courseCode;
	}

	public void setCourseCode(String courseCode) {
		this.courseCode = courseCode;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
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

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public int getCapacity() {
		return capacity;
	}

	public void setCapacity(int capacity) {
		this.capacity = capacity;
	}
	
}
