package com.english_center.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = false)
@Data
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

}
