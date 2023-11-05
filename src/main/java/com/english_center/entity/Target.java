package com.english_center.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="target")
public class Target extends BaseEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;

	@Column(name = "user_id")
	private int userId;
	
	@Column(name="time_exam ")
	private Date timeExam ;
	
	@Column(name="point_target ")
	private int pointTarget ;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	public Date getTimeExam() {
		return timeExam;
	}

	public void setTimeExam(Date timeExam) {
		this.timeExam = timeExam;
	}

	public int getPointTarget() {
		return pointTarget;
	}

	public void setPointTarget(int pointTarget) {
		this.pointTarget = pointTarget;
	}
	
	
	

}
