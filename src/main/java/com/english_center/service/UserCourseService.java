package com.english_center.service;

import com.english_center.common.utils.Pagination;
import com.english_center.entity.UserCourse;
import com.english_center.model.StoreProcedureListResult;

public interface UserCourseService {
	void create(UserCourse userCourse) throws Exception;

	UserCourse findOne(int id) throws Exception;

	void update(UserCourse userCourse) throws Exception;
	
	void spUUserCourse(int userCourseId) throws Exception;
	
	StoreProcedureListResult<UserCourse> spGUserCourse(int courseId, int isJoinClass, int userId, int isExpired,
			int type, Pagination pagination, int isPagination) throws Exception;

	
//	List<UserCourse> findByCourseIdAndUserId(int courseId,int userId) throws Exception;
}
