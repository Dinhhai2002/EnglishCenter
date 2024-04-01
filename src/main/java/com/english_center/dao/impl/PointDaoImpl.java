package com.english_center.dao.impl;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import javax.transaction.Transactional;

import org.springframework.stereotype.Repository;

import com.english_center.dao.AbstractDao;
import com.english_center.dao.PointDao;
import com.english_center.entity.Point;

@Repository("PointDao")
@Transactional
public class PointDaoImpl extends AbstractDao<Integer, Point> implements PointDao {

	@Override
	public void create(Point point) throws Exception {
		this.getSession().save(point);

	}

	@Override
	public Point findOne(int id) throws Exception {
		return this.getSession().find(Point.class, id);
	}

	@Override
	public void update(Point point) throws Exception {
		this.getSession().update(point);

	}

	@Override
	public Point findOneByUserId(int userId) throws Exception {
		CriteriaBuilder builder = this.getBuilder();
		CriteriaQuery<Point> query = builder.createQuery(Point.class);
		Root<Point> root = query.from(Point.class);
		query.where(builder.equal(root.get("userId"), userId));

		return this.getSession().createQuery(query).getResultList().stream().findFirst().orElse(null);
	}

}
