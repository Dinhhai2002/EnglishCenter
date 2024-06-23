package com.english_center.dao.impl;

import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import javax.transaction.Transactional;

import org.springframework.stereotype.Repository;

import com.english_center.dao.AbstractDao;
import com.english_center.dao.ResultDetailDao;
import com.english_center.entity.ResultDetail;

@Repository("ResultDetailDao")
@Transactional
public class ResultDetailDaoImpl extends AbstractDao<Integer, ResultDetail> implements ResultDetailDao {

	@Override
	public void create(ResultDetail resultDetail) throws Exception {
		this.getSession().save(resultDetail);

	}

	@Override
	public ResultDetail findOne(int id) throws Exception {
		return this.getSession().find(ResultDetail.class, id);
	}

	@Override
	public void update(ResultDetail resultDetail) throws Exception {
		this.getSession().update(resultDetail);

	}

	@Override
	public List<ResultDetail> getListByResultId(int resultId) throws Exception {
		CriteriaBuilder builder = this.getBuilder();
		CriteriaQuery<ResultDetail> query = builder.createQuery(ResultDetail.class);
		Root<ResultDetail> root = query.from(ResultDetail.class);
		query.where(builder.equal(root.get("resultId"), resultId));
		query.orderBy(builder.asc(root.get("sort")));

		return this.getSession().createQuery(query).getResultList();
	}

}
