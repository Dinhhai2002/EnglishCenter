package com.english_center.dao.impl;

import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import javax.transaction.Transactional;

import org.springframework.stereotype.Repository;

import com.english_center.dao.AbstractDao;
import com.english_center.dao.PromotionDao;
import com.english_center.entity.Promotion;

@Repository("PromotionDao")
@Transactional
public class PromotionDaoImpl extends AbstractDao<Integer, Promotion> implements PromotionDao {

	@Override
	public void create(Promotion promotion) throws Exception {
		this.getSession().save(promotion);

	}

	@Override
	public Promotion findOne(int id) throws Exception {
		return this.getSession().find(Promotion.class, id);
	}

	@Override
	public void update(Promotion promotion) throws Exception {
		this.getSession().update(promotion);

	}

	@Override
	public List<Promotion> findAll() throws Exception {
		CriteriaBuilder builder = this.getBuilder();
		CriteriaQuery<Promotion> query = builder.createQuery(Promotion.class);
		Root<Promotion> root = query.from(Promotion.class);
		query.where(builder.equal(root.get("status"), 1));

		return this.getSession().createQuery(query).getResultList();
	}

}
