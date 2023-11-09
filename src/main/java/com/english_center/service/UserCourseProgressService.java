package com.english_center.service;

import java.util.List;

import com.english_center.entity.UserCourseProgress;
import com.english_center.model.StoreProcedureListResult;

public interface UserCourseProgressService {
	void create(UserCourseProgress userCourseProgress) throws Exception;

	UserCourseProgress findOne(int id) throws Exception;

	void update(UserCourseProgress userCourseProgress) throws Exception;
	
	UserCourseProgress findByLessonsAndUser(int lessonsId, int userId) throws Exception;
	
	List<UserCourseProgress> findByCourseAndUser(int courseId, int userId) throws Exception;
	
	StoreProcedureListResult<UserCourseProgress> spGUserCourseProgress(int userId, int courseId, int chapterId,
			int lessonsId, int isCompleted) throws Exception;
}
