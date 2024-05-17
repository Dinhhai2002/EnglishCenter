package com.english_center.dao.impl;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import javax.transaction.Transactional;

import org.springframework.stereotype.Repository;

import com.english_center.common.utils.Pagination;
import com.english_center.dao.AbstractDao;
import com.english_center.dao.BannerDao;
import com.english_center.entity.Banner;
import com.english_center.model.StoreProcedureListResult;

@Repository("BannerDao")
@Transactional
public class BannerDaoImpl extends AbstractDao<Integer, Banner> implements BannerDao {

	@Override
	public void create(Banner banner) throws Exception {
		this.getSession().save(banner);

	}

	@Override
	public Banner findOne(int id) throws Exception {
		return this.getSession().find(Banner.class, id);
	}

	@Override
	public void update(Banner banner) throws Exception {
		this.getSession().update(banner);

	}

	@Override
	public StoreProcedureListResult<Banner> getAll(int status, Pagination pagination) throws Exception {
		CriteriaBuilder builder = this.getBuilder();
		CriteriaQuery<Banner> query = builder.createQuery(Banner.class);
//		CriteriaQuery<Banner> countQuery = builder.createQuery(Banner.class);
		Root<Banner> root = query.from(Banner.class);
		int totalRecord = this.getSession().createQuery(query).getResultList().size();
		if (status >= 0) {
			query.where(builder.equal(root.get("status"), status));
		}
		return new StoreProcedureListResult<>(this.getSession().createQuery(query)
				.setFirstResult(pagination.getOffset()).setMaxResults(pagination.getLimit()).getResultList(),
				totalRecord);
	}

}
