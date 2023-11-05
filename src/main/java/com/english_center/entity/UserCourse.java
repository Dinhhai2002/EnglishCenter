package com.english_center.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "user_course")
public class UserCourse extends BaseEntity {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;

	@Column(name = "course_id")
	private int courseId;

	@Column(name = "student_id")
	private int studentId;

	@Column(name = "is_join_class")
	private int isJoinClass;

	private int type;

	@Column(name = "is_expired")
	private int isExpired;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getCourseId() {
		return courseId;
	}

	public void setCourseId(int courseId) {
		this.courseId = courseId;
	}

	public int getStudentId() {
		return studentId;
	}

	public void setStudentId(int studentId) {
		this.studentId = studentId;
	}

	public int getIsJoinClass() {
		return isJoinClass;
	}

	public void setIsJoinClass(int isJoinClass) {
		this.isJoinClass = isJoinClass;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public int getIsExpired() {
		return isExpired;
	}

	public void setIsExpired(int isExpired) {
		this.isExpired = isExpired;
	}

}
