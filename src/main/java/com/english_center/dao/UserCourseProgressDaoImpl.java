package com.english_center.dao;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.transaction.Transactional;

import org.springframework.stereotype.Repository;

import com.english_center.entity.UserCourseProgress;

@Repository("UserCourseProgressDao")
@Transactional
public class UserCourseProgressDaoImpl extends AbstractDao<Integer, UserCourseProgress>
		implements UserCourseProgressDao {

	@Override
	public void create(UserCourseProgress userCourseProgress) throws Exception {
		this.getSession().save(userCourseProgress);

	}

	@Override
	public UserCourseProgress findOne(int id) throws Exception {
		return this.getSession().find(UserCourseProgress.class, id);
	}

	@Override
	public void update(UserCourseProgress userCourseProgress) throws Exception {
		this.getSession().update(userCourseProgress);

	}

	@Override
	public UserCourseProgress findByLessonsAndUser(int lessonsId, int userId) throws Exception {
		CriteriaBuilder builder = this.getBuilder();
		CriteriaQuery<UserCourseProgress> query = builder.createQuery(UserCourseProgress.class);
		Root<UserCourseProgress> root = query.from(UserCourseProgress.class);
		Predicate condition1 = builder.equal(root.get("lessonsId"), lessonsId);
		Predicate condition2 = builder.equal(root.get("userId"), userId);
		Predicate combinedCondition = builder.and(condition1, condition2);

		query.where(combinedCondition);

		return this.getSession().createQuery(query).getResultList().stream().findFirst().orElse(null);
	}

}
