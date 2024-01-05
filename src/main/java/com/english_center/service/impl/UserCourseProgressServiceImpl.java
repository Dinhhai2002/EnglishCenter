package com.english_center.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.english_center.dao.UserCourseProgressDao;
import com.english_center.entity.UserCourseProgress;
import com.english_center.model.StoreProcedureListResult;
import com.english_center.service.UserCourseProgressService;

@Service("UserCourseProgressService")
@Transactional(rollbackFor = Error.class)
public class UserCourseProgressServiceImpl implements UserCourseProgressService {

	@Autowired
	UserCourseProgressDao userCourseProgressDao;

	@Override
	public void create(UserCourseProgress userCourseProgress) throws Exception {
		userCourseProgressDao.create(userCourseProgress);

	}

	@Override
	public UserCourseProgress findOne(int id) throws Exception {
		return userCourseProgressDao.findOne(id);
	}

	@Override
	public void update(UserCourseProgress userCourseProgress) throws Exception {
		userCourseProgressDao.update(userCourseProgress);

	}

	@Override
	public UserCourseProgress findByLessonsAndUser(int lessonsId, int userId) throws Exception {
		return userCourseProgressDao.findByLessonsAndUser(lessonsId, userId);
	}

	@Override
	public List<UserCourseProgress> findByCourseAndUser(int courseId, int userId) throws Exception {
		return userCourseProgressDao.findByCourseAndUser(courseId, userId);
	}

	@Override
	public StoreProcedureListResult<UserCourseProgress> spGUserCourseProgress(int userId, int courseId, int chapterId,
			int lessonsId, int isCompleted) throws Exception {
		return userCourseProgressDao.spGUserCourseProgress(userId, courseId, chapterId, lessonsId, isCompleted);
	}

}
