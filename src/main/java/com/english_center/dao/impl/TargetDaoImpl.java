package com.english_center.dao.impl;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import javax.transaction.Transactional;

import org.springframework.stereotype.Repository;

import com.english_center.dao.AbstractDao;
import com.english_center.dao.TargetDao;
import com.english_center.entity.Target;

@Repository("TargetDao")
@Transactional
public class TargetDaoImpl extends AbstractDao<Integer, Target> implements TargetDao {

	@Override
	public void create(Target target) throws Exception {
		this.getSession().save(target);
	}

	@Override
	public Target findOne(int id) throws Exception {
		return this.getSession().find(Target.class, id);
	}

	@Override
	public void update(Target target) throws Exception {
		this.getSession().update(target);

	}

	@Override
	public Target findByUserId(int userId) throws Exception {
		CriteriaBuilder builder = this.getBuilder();
		CriteriaQuery<Target> query = builder.createQuery(Target.class);
		Root<Target> root = query.from(Target.class);
		query.where(builder.equal(root.get("userId"), userId));

		return this.getSession().createQuery(query).getResultList().stream().findFirst().orElse(null);
	}

}
