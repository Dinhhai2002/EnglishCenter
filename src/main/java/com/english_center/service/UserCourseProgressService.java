package com.english_center.service;

import com.english_center.entity.UserCourseProgress;

public interface UserCourseProgressService {
	void create(UserCourseProgress userCourseProgress) throws Exception;

	UserCourseProgress findOne(int id) throws Exception;

	void update(UserCourseProgress userCourseProgress) throws Exception;
	
	UserCourseProgress findByLessonsAndUser(int lessonsId, int userId) throws Exception;
}
