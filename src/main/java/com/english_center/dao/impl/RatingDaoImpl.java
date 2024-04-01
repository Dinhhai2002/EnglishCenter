package com.english_center.dao.impl;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.transaction.Transactional;

import org.springframework.stereotype.Repository;

import com.english_center.dao.AbstractDao;
import com.english_center.dao.RatingDao;
import com.english_center.entity.Rating;

@Repository("RatingDao")
@Transactional
public class RatingDaoImpl extends AbstractDao<Integer, Rating> implements RatingDao {

	@Override
	public void create(Rating rating) throws Exception {
		this.getSession().save(rating);

	}

	@Override
	public Rating findOne(int id) throws Exception {
		return this.getSession().find(Rating.class, id);
	}

	@Override
	public void update(Rating rating) throws Exception {
		this.getSession().update(rating);

	}

	@Override
	public Rating findOneByUserAndPost(int userId, int postId) throws Exception {
		CriteriaBuilder builder = this.getBuilder();
		CriteriaQuery<Rating> query = builder.createQuery(Rating.class);
		Root<Rating> root = query.from(Rating.class);
		Predicate condition1 = builder.equal(root.get("postId"), postId);
		Predicate condition2 = builder.equal(root.get("userId"), userId);
		Predicate combinedCondition = builder.and(condition1, condition2);

		query.where(combinedCondition);

		return this.getSession().createQuery(query).getResultList().stream().findFirst().orElse(null);
	}

}
