package com.english_center.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.english_center.common.utils.Pagination;
import com.english_center.dao.UserCourseDao;
import com.english_center.entity.UserCourse;
import com.english_center.model.StoreProcedureListResult;
import com.english_center.service.UserCourseService;

@Service("UserCourseService")
@Transactional(rollbackFor = Error.class)
public class UserCourseServiceImpl implements UserCourseService {
	@Autowired
	UserCourseDao userCourseDao;

	@Override
	public void create(UserCourse userCourse) throws Exception {
		userCourseDao.create(userCourse);
	}

	@Override
	public UserCourse findOne(int id) throws Exception {
		return userCourseDao.findOne(id);
	}

	@Override
	public void update(UserCourse userCourse) throws Exception {
		userCourseDao.update(userCourse);
	}

	@Override
	public StoreProcedureListResult<UserCourse> spGUserCourse(int courseId, int isJoinClass, int userId, int isExpired,
			int type, Pagination pagination, int isPagination) throws Exception {
		return userCourseDao.spGUserCourse(courseId, isJoinClass, userId, isExpired, type, pagination, isPagination);
	}

	@Override
	public void spUUserCourse(int userCourseId) throws Exception {
		userCourseDao.spUUserCourse(userCourseId);
	}

}
